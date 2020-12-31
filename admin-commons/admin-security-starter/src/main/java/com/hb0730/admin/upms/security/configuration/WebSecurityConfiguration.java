package com.hb0730.admin.upms.security.configuration;

import com.hb0730.admin.upms.security.handler.Oauth2LoginSuccessHandler;
import com.hb0730.admin.upms.security.handler.logout.Oauth2LogoutSuccessHandler;
import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import com.hb0730.admin.upms.security.service.client.RedisOauth2AuthorizedClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
    private final RedisOauth2AuthorizedClientServiceImpl redisOauth2AuthorizedClientService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UpmsSecurityStarterProperties upmsSecurityStarterProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .csrf().disable()
                .cors()
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .invalidateHttpSession(true)
                .deleteCookies(upmsSecurityStarterProperties.getRegistrationId(),"authorization-server")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
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
                .authorizationEndpoint()
                .and()
                .tokenEndpoint()
                .and()
                .redirectionEndpoint()
                .and()
                .successHandler(oauth2LoginSuccessHandler)
        ;
        http
                .oauth2Client()
                .authorizedClientService(redisOauth2AuthorizedClientService)
        ;
    }


    public LogoutSuccessHandler logoutSuccessHandler() {
        Oauth2LogoutSuccessHandler logoutSuccessHandler = new Oauth2LogoutSuccessHandler(clientRegistrationRepository, upmsSecurityStarterProperties);
        logoutSuccessHandler.setPostLogoutRedirectUri("/logout");
        return logoutSuccessHandler;
    }


}
