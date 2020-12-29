package com.hb0730.upms.authorization.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 权限范围
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode
@TableName("sys_oauth_scope_details")
public class ScopeDetails implements Serializable {
    private static final long serialVersionUID = 2669137009572642842L;
    /**
     * scopeid
     */
    @TableId("scope_id")
    private String scopeId;
    /**
     * name
     */
    @TableField("name")
    private String name;
}
