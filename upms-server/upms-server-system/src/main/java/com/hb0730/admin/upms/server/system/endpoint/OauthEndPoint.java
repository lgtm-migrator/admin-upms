package com.hb0730.admin.upms.server.system.endpoint;

import com.hb0730.admin.upms.commons.entity.constant.Oauth2Constant;
import com.hb0730.admin.upms.security.endpoint.TokenEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bing_huang
 */
@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class OauthEndPoint {
    private final TokenEndpoint tokenEndpoint;

    @RequestMapping("/call/back")
    public void redirect(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("跳转");
        OAuth2AuthorizedClient token = tokenEndpoint.getToken(authentication);
        response.setHeader(Oauth2Constant.OAUTH2_TOKEN_HEADER_AUTHORIZATION, Oauth2Constant.OAUTH2_TOKEN_TYPE_BEARER + token.getAccessToken().getTokenValue());
        Cookie cookie = new Cookie(Oauth2Constant.OAUTH2_TOKEN_HEADER_AUTHORIZATION, token.getAccessToken().getTokenValue());
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);

        String newUrl = "http://localhost:8080/";
        String html = "<script type='text/javascript'>location.href='" + newUrl + "';</script>";
        response.getWriter().print(html);
    }

    @RequestMapping("/upms/login?goto=/call/back")
    public String login() {
        return "redirect:/";
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
