package com.hb0730.admin.upms.security.properties;

import com.hb0730.admin.upms.commons.security.starter.properties.UpmsSecurityProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
     * 客户端
     */
    private String registrationId;

    /**
     * 当前应用
     */
    private String[] appid;
}
