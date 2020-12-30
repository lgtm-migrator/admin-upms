package com.hb0730.admin.upms.server.system.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bing_huang
 */
@Controller
@RequestMapping("/oauth")
@Slf4j
public class OauthEndPoint {

    @RequestMapping("/userinfo")
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

    @RequestMapping("/current/user")
    @ResponseBody
    public Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping("/call/back")
    @ResponseBody
    public Authentication redirect(HttpServletRequest request, HttpServletResponse response) {
        log.info("跳转");
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping("/test/permission")
    @PreAuthorize("hasAnyAuthority('test:permission')")
    @ResponseBody
    public String testPermission() {
        return "权限测试成功";
    }

    @RequestMapping("/test/permission2")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseBody
    public String testPermission2() {
        return "权限测试成功";
    }
}
