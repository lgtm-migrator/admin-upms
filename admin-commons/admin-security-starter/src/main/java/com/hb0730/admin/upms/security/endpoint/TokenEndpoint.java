package com.hb0730.admin.upms.security.endpoint;

import com.hb0730.admin.upms.security.handler.login.Oauth2LoginHandler;
import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 令牌相关信息
 *
 * @author bing_huang
 */
@Controller
public class TokenEndpoint {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UpmsSecurityStarterProperties properties;
    private final Oauth2LoginHandler oauth2LoginHandler;

    public TokenEndpoint(OAuth2AuthorizedClientService authorizedClientService,
                         UpmsSecurityStarterProperties properties) {
        this.authorizedClientService = authorizedClientService;
        this.properties = properties;
        this.oauth2LoginHandler = new Oauth2LoginHandler(properties);
    }

    @RequestMapping("/oauth/current/token")
    @ResponseBody
    public OAuth2AuthorizedClient getToken(Authentication authentication) {
        return authorizedClientService.loadAuthorizedClient(properties.getClientId(), authentication.getName());
    }

    @PostMapping(value = "/oauth/login")
    @ResponseBody
    public String login(String username, String password) {
        ResponseEntity<String> entity = this.oauth2LoginHandler.request(username, password);
        return entity.getBody();
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌 token
     * @return 以及刷新后的认证信息
     */
    public OAuth2AuthorizedClient refreshToken(@RequestParam("refresh_token") String refreshToken) {

        return null;
    }
}
