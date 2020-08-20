package com.shen.httpresult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用响应结构
 */
@Data
@Slf4j
public class HttpResult<T> {

    /**
     * 成功/失败/系统错误 状态码
     */
    private String code;

    /**
     * 业务类状态码，返回给前端，方便前后端沟通业务类错误问题
     */
    private String bizCode;

    /**
     * 消息描述
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private String timestamp;

    private HttpResult(T data) {
        // ignore
    }

    private HttpResult(HttpResultCodeEnum codeEnum, T data) {
        this.code = codeEnum.code();
        this.msg = codeEnum.msg();
        this.timestamp = TimestampUtil.getTimestamp();
        this.data = data;
    }

    /**
     * 业务类异常调用
     * @param codeEnum
     */
    private HttpResult(HttpResultCodeEnum codeEnum) {
        this.code = HttpResultCodeEnum.BIZ_ERROR.code();
        this.bizCode = codeEnum.code();
        this.msg = codeEnum.msg();
        this.timestamp = TimestampUtil.getTimestamp();
        this.data = data;
    }

    private HttpResult(String msg) {
        this.code = HttpResultCodeEnum.BIZ_ERROR.code();
        this.bizCode = HttpResultCodeEnum.VALIDATE_ERROR.code();
        this.msg = msg;
        this.timestamp = TimestampUtil.getTimestamp();
        this.data = data;
    }

    /**
     * 操作成功，无需数据返回
     *
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> success() {
        return new HttpResult(HttpResultCodeEnum.SUCCESS, null);
    }

    /**
     * 操作成功，携带data数据返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> success(T data) {
        return new HttpResult(HttpResultCodeEnum.SUCCESS, data);
    }

    /**
     * 系统发生错误时调用
     * 切勿显式调用，由GlobalExceptionHandler统一处理
     * @return
     */
    public static <T> HttpResult<T> systemError() {
        return new HttpResult(HttpResultCodeEnum.SYSTEM_ERROR, null);
    }

    /**
     * 业务校验错误提示
     * 切换显示调用，由GlobalExceptionHandler统一处理
     * @param codeEnum
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> bizError(HttpResultCodeEnum codeEnum) {
        return new HttpResult(codeEnum);
    }

    /**
     * 参数校验异常
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> validateError(String msg) {
        return new HttpResult(msg);
    }

}