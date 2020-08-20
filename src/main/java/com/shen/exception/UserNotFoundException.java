package com.shen.exception;

import com.shen.httpresult.HttpResultCodeEnum;
import lombok.Data;

/**
 * 用户不存在异常
 */
@Data
public class UserNotFoundException extends RuntimeException {

    private HttpResultCodeEnum codeEnum;

    public UserNotFoundException() {
        this.codeEnum =  HttpResultCodeEnum.USER_NOT_FOUND;
    }

}
