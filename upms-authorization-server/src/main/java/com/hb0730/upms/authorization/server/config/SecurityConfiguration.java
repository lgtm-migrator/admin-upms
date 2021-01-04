package com.hb0730.upms.authorization.server.config;

import com.hb0730.admin.upms.commons.entity.constant.EndpointConstant;
import com.hb0730.upms.authorization.server.handler.WebLoginFailureHandler;
import com.hb0730.upms.authorization.server.handler.WebLoginSuccessHandler;
import com.hb0730.upms.authorization.server.handler.WebLogoutSuccessHandler;
import com.hb0730.upms.authorization.server.service.impl.RedisRememberMeTokenRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.*;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final WebLogoutSuccessHandler webLogoutSuccessHandler;

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
        web.ignoring().antMatchers("/asset/**", "*.css", "*.js");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers(EndpointConstant.OAUTH_ALL, EndpointConstant.LOGIN)
                .and()
                .authorizeRequests()
                .antMatchers(
                        EndpointConstant.LOGIN,
                        EndpointConstant.CUS_LOGOUT,
                        EndpointConstant.LOGOUT).permitAll()
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
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID", "SESSION", "remember-me")
                .logoutSuccessHandler(webLogoutSuccessHandler)
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .userDetailsService(userDetailService)
                .tokenRepository(redisRememberMeTokenRepository)
                .and()
                .cors()
                .and()
                .csrf().disable()
        ;

        http.requestCache().requestCache(cache(http));
    }

    private RequestCache cache(HttpSecurity httpRequest) {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setRequestMatcher(createDefaultSavedRequestMatcher(httpRequest));
        return requestCache;
    }

    private RequestMatcher createDefaultSavedRequestMatcher(HttpSecurity http) {
        RequestMatcher notFavIcon = new NegatedRequestMatcher(new AntPathRequestMatcher("/**/favicon.*"));
        RequestMatcher notXRequestedWith = new NegatedRequestMatcher(
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
        List<RequestMatcher> matchers = new ArrayList<>();
        RequestMatcher getRequests = new AntPathRequestMatcher("/**", "GET");
        matchers.add(0, getRequests);
        matchers.add(notFavIcon);
        matchers.add(notMatchingMediaType(http, MediaType.APPLICATION_JSON));
        matchers.add(notXRequestedWith);
        matchers.add(notMatchingMediaType(http, MediaType.MULTIPART_FORM_DATA));
        matchers.add(notMatchingMediaType(http, MediaType.TEXT_EVENT_STREAM));

        return new AndRequestMatcher(matchers);
    }

    private RequestMatcher notMatchingMediaType(HttpSecurity http, MediaType mediaType) {
        ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher mediaRequest = new MediaTypeRequestMatcher(contentNegotiationStrategy, mediaType);
        mediaRequest.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        return new NegatedRequestMatcher(mediaRequest);
    }
}
