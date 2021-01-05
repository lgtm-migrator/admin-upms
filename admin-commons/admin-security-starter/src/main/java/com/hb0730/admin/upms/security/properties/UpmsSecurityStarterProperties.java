package com.hb0730.admin.upms.security.properties;

import com.hb0730.admin.upms.commons.security.starter.properties.UpmsSecurityProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * security 配置
 *
 * @author bing_huang
 */
@ConfigurationProperties(prefix = "upms.security")
@Data
@EqualsAndHashCode(callSuper = true)
public class UpmsSecurityStarterProperties extends UpmsSecurityProperties {
    /**
     * provider
     */
    private String clientRegistrationId;
    /**
     * 客户端
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 当前应用
     */
    private String[] appid;
    /**
     * 登录端点
     */
    private String loginEndpoint;
    /**
     * 注销端点
     */
    private String logoutEndpoint;

    /**
     * 注销后重定向
     */
    private String logoutRedirectUri;
}
