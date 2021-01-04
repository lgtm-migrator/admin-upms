package com.hb0730.admin.upms.commons.entity.constant;

/**
 * oauth2 常量
 *
 * @author bing_huang
 */
public interface Oauth2Constant {
    /**
     * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
     */
    String OAUTH2_TOKEN_TYPE_BEARER = "bearer ";
    /**
     * basic登录
     */
    String OAUTH2_TOKEN_TYPE_BASIC = "Basic ";

    /**
     * OAUTH2 令牌 header
     */
    String OAUTH2_TOKEN_HEADER_AUTHORIZATION = "authorization";

    String OAUTH2_PARAMS_REFRESH="refresh_token";
}
