package com.hb0730.admin.upms.commons.security.starter.handler;

import com.hb0730.admin.upms.commons.entity.ResponseResult;
import com.hb0730.admin.upms.commons.utils.UpmsUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bing_huang
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthExceptionEntryPoint.class);

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String message = "访问令牌不合法";
        LOGGER.error("客户端访问{}请求失败: {}", requestUri, message, exception);
        UpmsUtils.makeJsonResponse(response, status, ResponseResult.newInstance().message(message));
    }
}
