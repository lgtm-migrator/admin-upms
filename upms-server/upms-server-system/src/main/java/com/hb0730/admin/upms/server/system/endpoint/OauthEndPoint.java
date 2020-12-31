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
