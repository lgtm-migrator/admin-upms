package com.hb0730.upms.authorization.server.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限范围
 *
 * @author bing_huang
 */
@Data
public class ScopeDetails implements Serializable {
    private String scopeId;
    private String name;
}
