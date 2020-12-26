package com.hb0730.admin.upms.comons.security.starter.handler;

import com.hb0730.admin.upms.commons.entity.ResponseResult;
import com.hb0730.admin.upms.commons.utils.UpmsUtils;
import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bing_huang
 */
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @SneakyThrows
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ResponseResult result = ResponseResult.newInstance().message("没有权限访问该资源");
        UpmsUtils.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, result);
    }
}
