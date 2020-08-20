package com.shen.demo;

import com.shen.config.bean.test.EmailConfig;
import com.shen.config.bean.test.Oauth2ClientsConfig;
import com.shen.httpresult.HttpResult;
import com.shen.request.QueryUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class HelloController {

    @Value("${app.env}")
    private String env;

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private Oauth2ClientsConfig oauth2ClientsConfig;

    @PostMapping("/hello")
    public HttpResult hello() {
        log.info("-->controller方法执行");
        log.info("-->读取当前app.env：{}", env);
        log.info("-->读取emailConfig: {}",emailConfig);
        log.info("-->读取oauth2ClientsConfig:{}", oauth2ClientsConfig);
        return HttpResult.success();
    }

    @PostMapping("/query")
    public HttpResult query(@Valid @RequestBody QueryUserRequest request) {
        return HttpResult.success();
    }

}
