package com.shen.config.bean.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
@Data
@ToString
public class EmailConfig {

    private String from;
    private String title;
    private String content;

}
