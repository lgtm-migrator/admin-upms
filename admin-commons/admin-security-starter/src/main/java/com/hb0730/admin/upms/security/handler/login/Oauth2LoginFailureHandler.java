package com.hb0730.admin.upms.security.handler.login;

import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import com.hb0730.commons.lang.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败，重新跳转
 *
 * @author bing_huang
 * @see org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter#generateLoginPageHtml(HttpServletRequest, boolean, boolean)
 * @see OAuth2LoginConfigurer#getLoginLinks()
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final UpmsSecurityStarterProperties properties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("登录失败>>>>");
        String redirectUri = properties.getLogoutRedirectUri();
        if (StringUtils.isNotBlank(redirectUri)) {
            response.sendRedirect(redirectUri);
            return;
        }
        super.onAuthenticationFailure(request, response, exception);
    }

}
