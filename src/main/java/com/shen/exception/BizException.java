package com.shen.exception;

import com.shen.httpresult.HttpResultCodeEnum;
import lombok.Data;

/**
 * 业务类异常
 */
@Data
public class BizException extends RuntimeException {

    private HttpResultCodeEnum codeEnum;

    private BizException() {
    }

    public BizException(HttpResultCodeEnum codeEnum) {
        this.codeEnum =  codeEnum;
    }

}
