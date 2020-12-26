package com.hb0730.upms.authorization.server.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bing_huang
 */
@Data
public class Application implements Serializable {
    private String appid;
    private String name;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
