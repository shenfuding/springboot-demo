package com.shen.config.bean.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "oauth2.security")
public class Oauth2ClientsConfig {

    List<Oauth2Client> clients;

}
