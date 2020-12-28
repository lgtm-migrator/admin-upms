package com.hb0730.upms.authorization.server.config;

import com.hb0730.admin.upms.commons.entity.constant.EndpointConstant;
import com.hb0730.upms.authorization.server.handler.WebLoginFailureHandler;
import com.hb0730.upms.authorization.server.handler.WebLoginSuccessHandler;
import com.hb0730.upms.authorization.server.service.impl.RedisRememberMeTokenRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurity配置
 *
 * @author bing_huang
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailService;
    private final WebLoginSuccessHandler webLoginSuccessHandler;
    private final WebLoginFailureHandler webLoginFailureHandler;
    private final PasswordEncoder passwordEncoder;
    private final RedisRememberMeTokenRepositoryImpl redisRememberMeTokenRepository;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/asset/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers(EndpointConstant.OAUTH_ALL, EndpointConstant.LOGIN)
                .and()
                .authorizeRequests()
                .antMatchers(EndpointConstant.LOGIN).permitAll()
                .antMatchers(EndpointConstant.OAUTH_ALL).authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(EndpointConstant.LOGIN)
                .loginProcessingUrl(EndpointConstant.LOGIN)
                .successHandler(webLoginSuccessHandler)
                .failureHandler(webLoginFailureHandler)
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .userDetailsService(userDetailService)
                .tokenRepository(redisRememberMeTokenRepository)
                .and()
                .cors()
        ;
    }

}
