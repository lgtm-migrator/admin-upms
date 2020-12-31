package com.hb0730.admin.upms.commons.security.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author bing_huang
 */
@ConfigurationProperties(prefix = "upms.security")
@Data
public class UpmsSecurityProperties implements Serializable {
    /**
     * 是否开启安全配置
     */
    private Boolean enable;
}
