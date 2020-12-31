package com.hb0730.admin.upms.security.endpoint;

import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 令牌相关信息
 *
 * @author bing_huang
 */
@Controller
@RequiredArgsConstructor
public class TokenEndpoint {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UpmsSecurityStarterProperties properties;

    @RequestMapping("/oauth/current/token")
    @ResponseBody
    public OAuth2AuthorizedClient getToken(Authentication authentication) {
        return authorizedClientService.loadAuthorizedClient(properties.getRegistrationId(), authentication.getName());
    }
}
