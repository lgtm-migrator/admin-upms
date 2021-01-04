package com.hb0730.admin.upms.security.endpoint;

import com.hb0730.admin.upms.commons.entity.constant.Oauth2Constant;
import com.hb0730.admin.upms.commons.utils.UpmsUtils;
import com.hb0730.admin.upms.security.handler.login.Oauth2LoginHandler;
import com.hb0730.admin.upms.security.handler.refresh.Oauth2RefreshHandler;
import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    private final Oauth2RefreshHandler oauth2RefreshHandler;

    public TokenEndpoint(OAuth2AuthorizedClientService authorizedClientService,
                         UpmsSecurityStarterProperties properties) {
        this.authorizedClientService = authorizedClientService;
        this.properties = properties;
        this.oauth2LoginHandler = new Oauth2LoginHandler(properties);
        this.oauth2RefreshHandler = new Oauth2RefreshHandler(properties);
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
    @PostMapping(value = "/oauth/refresh", headers = {Oauth2Constant.OAUTH2_TOKEN_HEADER_AUTHORIZATION}, params = Oauth2Constant.OAUTH2_PARAMS_REFRESH)
    @ResponseBody
    public String refreshToken(@RequestParam(Oauth2Constant.OAUTH2_PARAMS_REFRESH) String refreshToken) {
        ResponseEntity<String> responseEntity = this.oauth2RefreshHandler.request(refreshToken);
        return responseEntity.getBody();
    }
}
