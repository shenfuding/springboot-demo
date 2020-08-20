package com.shen.config.webmvc;

import com.shen.config.webmvc.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器实现
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截的URL集合
        List<String> patterns = new ArrayList<>();
        patterns.add("/**");

        // 排除的URL集合
        List<String> excludePatterns = new ArrayList<>();
        excludePatterns.add("/query");

        // 注册拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns(patterns).excludePathPatterns(excludePatterns);
    }
}
