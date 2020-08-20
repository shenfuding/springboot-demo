package com.shen.config.bean.test;

import lombok.Data;

/**
 * Oauth2客户端
 */
@Data
public class Oauth2Client {
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 令牌有效期
     */
    private Integer accessTokenValiditySeconds;
    /**
     * 刷新令牌的有效期
     */
    private Integer refreshTokenValiditySeconds;
}
