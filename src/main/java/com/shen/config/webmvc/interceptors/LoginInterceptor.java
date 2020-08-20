package com.shen.config.webmvc.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在controller方法处理前调用
     * @param request
     * @param response
     * @param handler
     * @return 返回false则不会调用controller方法
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        log.info("--preHandle执行");
//        boolean notLogin = true;
//        if(notLogin) {
//            HttpResultUtil.response(response, HttpResultCodeEnum.USER_NOT_LOGIN);
//            return false;
//        }
        return true;
    }

    /**
     * Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("--postHandle执行");
    }

    /**
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("--afterCompletion执行");
    }
}
