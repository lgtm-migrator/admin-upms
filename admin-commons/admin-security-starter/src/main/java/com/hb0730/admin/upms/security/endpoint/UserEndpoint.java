package com.hb0730.admin.upms.security.endpoint;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * user Info 端点
 *
 * @author bing_huang
 */
@Controller
@RequestMapping
public class UserEndpoint {
    /**
     * 用户端点
     */
    @RequestMapping("/oauth/userinfo")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        // 为何转为map
        // 当security5 Client请求过来时流程
        // Oauth2LoginAuthenticationFilter#attemptAuthentication 184
        // OAuth2LoginAuthenticationProvider#authenticate 110
        // DefaultOAuth2Service#loadUser
        // DefaultOAuth2User
        BearerTokenAuthentication authentication = (BearerTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>();
        map.put("authorities", authentication.getAuthorities());
        map.put("details", authentication.getDetails());
        map.put("authenticated", authentication.isAuthenticated());
        map.put("principal", authentication.getPrincipal());
        map.put("credentials", authentication.getCredentials());
        map.put("token", authentication.getToken());
        map.put("tokenAttributes", authentication.getTokenAttributes());
        map.put("name", authentication.getTokenAttributes().get("user_name"));
        return map;
    }

    /**
     * 获取当前用户
     */
    @RequestMapping("/oauth/current/user")
    @ResponseBody
    public Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}