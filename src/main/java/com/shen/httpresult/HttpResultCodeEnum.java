package com.shen.httpresult;

/**
 * 状态码定义集合
 */
public enum HttpResultCodeEnum implements ErrorCode {

    SUCCESS("200", "操作成功"),
    BIZ_ERROR("0", "业务校验错误"),
    SYSTEM_ERROR("-1", "系统发生错误"), // 由系统自己捕获异常时使用


    // TODO 按模块增加状态码
    VALIDATE_ERROR("VALIDATE_ERROR", "参数校验不通过"),
    USER_NOT_FOUND("USER_NOT_FOUND", "用户不存在"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),
    ;

    private String code;
    private String msg;

    HttpResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

}
