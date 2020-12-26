package com.hb0730.upms.authorization.server.commons;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bing_huang
 */
public class UpmsUtils {
    /**
     * 判断是否为 ajax请求
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }
}
