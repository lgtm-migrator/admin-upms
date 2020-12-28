package com.hb0730.admin.upms.comons.security.starter.configuration;

import com.hb0730.admin.upms.comons.security.starter.handler.AuthExceptionEntryPoint;
import com.hb0730.admin.upms.comons.security.starter.handler.SecurityAccessDeniedHandler;
import com.hb0730.admin.upms.comons.security.starter.properties.UpmsSecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @author bing_huang
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(value = UpmsSecurityProperties.class)
@ConditionalOnProperty(value = "umps.security.enable", havingValue = "true", matchIfMissing = true)
public class SecurityAutoConfigure extends GlobalMethodSecurityConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public AuthExceptionEntryPoint authenticationEntryPoint() {
        return new AuthExceptionEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public SecurityAccessDeniedHandler accessDeniedHandler() {
        return new SecurityAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
