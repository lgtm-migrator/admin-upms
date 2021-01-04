package com.hb0730.upms.authorization.server.web;

import com.hb0730.admin.upms.commons.entity.constant.Oauth2Constant;
import com.hb0730.admin.upms.commons.entity.constant.SystemConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author bing_huang
 */
@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final ConsumerTokenServices consumerTokenServices;

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "欢迎访问";
    }

    /**
     * 获取当前用户
     *
     * @param principal 已认证的用户
     * @return 当前用户
     */
    @ResponseBody
    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登出
     *
     * @param request request
     * @param token   token
     * @return 是否成功
     */
    @ResponseBody
    @PostMapping("/signout")
    public String signout(HttpServletRequest request, @RequestHeader("Authorization") String token) {
        token = StringUtils.replace(token, Oauth2Constant.OAUTH2_TOKEN_TYPE_BEARER, "");
        consumerTokenServices.revokeToken(token);
        return "登出成功";
    }
}
