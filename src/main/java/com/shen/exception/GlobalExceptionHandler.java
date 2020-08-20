package com.shen.exception;

import com.shen.httpresult.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Order(1)
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    /**
     * 全局Exception
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public HttpResult handlerException(Exception e) {
        log.error("!!!!!!!!!!!!!!!!发生错误!!!!!!!!!!!!!!!!", e);
        return HttpResult.systemError();
    }

    /**
     * 参数校验错误，@Valid
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResult handlerException(MethodArgumentNotValidException e) {
        String msg = null;
        BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        if(bindingResult.hasErrors()) {
            List<FieldError> errorList = bindingResult.getFieldErrors();
            if(errorList != null && !errorList.isEmpty()) {
                log.error("!!!!!!!!!方法【{}】参数【{}】校验不通过: {}!!!!!!!!!",
                        errorList.get(0).getObjectName(),
                        errorList.get(0).getField(),
                        errorList.get(0).getDefaultMessage());
                msg = errorList.get(0).getDefaultMessage();
            }
        }

        return HttpResult.validateError(msg);
    }

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BizException.class)
    public HttpResult handlerException(BizException e) {
        log.error("!!!!!!!!!!!!!!!!业务校验错误!!!!!!!!!!!!!!!!", e);
        return HttpResult.bizError(e.getCodeEnum());
    }

    /**
     * 自定义用户不存在异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public HttpResult handlerException(UserNotFoundException e) {
        log.error("!!!!!!!!!!!!!!!!用户不存在!!!!!!!!!!!!!!!!", e);
        return HttpResult.bizError(e.getCodeEnum());
    }
}
