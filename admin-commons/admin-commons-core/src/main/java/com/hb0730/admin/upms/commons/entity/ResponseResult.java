package com.hb0730.admin.upms.commons.entity;

import java.util.HashMap;

/**
 * 响应
 *
 * @author bing_huang
 */
public class ResponseResult extends HashMap<String, Object> {

    private static final long serialVersionUID = -8133301478679283367L;

    public static ResponseResult newInstance() {
        return new ResponseResult();
    }

    public ResponseResult message(String message) {
        this.put("message", message);
        return this;
    }

    public ResponseResult data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public ResponseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
