package com.hb0730.admin.upms.commons.utils;

import com.hb0730.commons.json.exceptions.JsonException;
import com.hb0730.commons.json.utils.Jsons;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * upms 工具类
 *
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

    /**
     * 设置响应
     *
     * @param response    HttpServletResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param value       响应内容
     * @throws IOException   IOException
     * @throws JsonException 未找到
     */
    public static void makeResponse(HttpServletResponse response, String contentType,
                                    int status, Object value) throws IOException, JsonException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(Jsons.JSONS.objectToJson(value).getBytes());
    }

    /**
     * 设置成功响应
     *
     * @param response HttpServletResponse
     * @param value    响应内容
     * @throws IOException IOException
     */
    public static void makeSuccessResponse(HttpServletResponse response, Object value) throws IOException, JsonException {
        makeResponse(response, MediaType.APPLICATION_JSON_VALUE, HttpServletResponse.SC_OK, value);
    }

    /**
     * 设置失败响应
     *
     * @param response HttpServletResponse
     * @param value    响应内容
     * @throws IOException IOException
     */
    public static void makeFailureResponse(HttpServletResponse response, Object value) throws IOException, JsonException {
        makeResponse(response, MediaType.APPLICATION_JSON_VALUE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, value);
    }

    /**
     * 设置JSON类型响应
     *
     * @param response HttpServletResponse
     * @param status   http状态码
     * @param value    响应内容
     * @throws IOException IOException
     */
    public static void makeJsonResponse(HttpServletResponse response, int status, Object value) throws IOException, JsonException {
        makeResponse(response, MediaType.APPLICATION_JSON_VALUE, status, value);
    }

}
