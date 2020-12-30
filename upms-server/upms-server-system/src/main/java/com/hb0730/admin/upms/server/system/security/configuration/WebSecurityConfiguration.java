package com.hb0730.admin.upms.server.system.security.configuration;

import com.hb0730.admin.upms.server.system.security.handler.Oauth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author bing_huang
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAuthoritiesOpaqueTokenIntrospector opaqueTokenIntrospector;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .csrf().disable()
                .cors()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        // BearerTokenAuthenticationFilter
        // 无法设置authenticationDetailsSource
        // issues https://github.com/spring-projects/spring-security/issues/8840
        http.oauth2ResourceServer()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .opaqueToken().introspector(opaqueTokenIntrospector)
        ;
        http
                .oauth2Login()
                .userInfoEndpoint()
                .and()
                .successHandler(oauth2LoginSuccessHandler)
        ;
        http.oauth2Client();
    }
}
