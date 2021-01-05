package com.hb0730.admin.upms.server.system.configuration;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author bing_huang
 */
@Configuration
public class CorsConfiguration {


    /**
     * 创建跨域filter
     *
     * @return {@link org.springframework.web.filter.CorsFilter}
     */
    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource corsConfiguration = new UrlBasedCorsConfigurationSource();
        corsConfiguration.registerCorsConfiguration("/**", build());
        return new org.springframework.web.filter.CorsFilter(corsConfiguration);
    }

    private org.springframework.web.cors.CorsConfiguration build() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        // 是否允许请求带有验证信息
        configuration.setAllowCredentials(true);
        // 允许访问的客户端域名 2.4.0
        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedOrigin("*");
        // 允许服务端访问的客户端请求头
        configuration.addAllowedHeader("*");
        // 允许访问的方法名,GET POST等
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("content-disposition");
        configuration.setMaxAge(3600L);
        return configuration;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }
}
