package com.hb0730.admin.upms.security;

import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author bing_huang
 */
@Configuration
@ComponentScan(value = {"com.hb0730.admin.upms.security.**", "com.hb0730.admin.upms.commons.security.**"})
@EnableConfigurationProperties(value = UpmsSecurityStarterProperties.class)
@ConditionalOnProperty(value = "umps.security.enable", havingValue = "true", matchIfMissing = true)
public class UpmsSecurityAutoConfiguration {
    public UpmsSecurityAutoConfiguration(UpmsSecurityStarterProperties properties) {
        String registrationId = properties.getClientId();
        Assert.hasText(registrationId, "umps.security.registration-id must be not null");
        String[] appid = properties.getAppid();
        Assert.notEmpty(appid, "umps.security.appid must be not null");
    }
}
