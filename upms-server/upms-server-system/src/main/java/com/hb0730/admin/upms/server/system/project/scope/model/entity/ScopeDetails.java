package com.hb0730.admin.upms.server.system.project.scope.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hb0730.admin.upms.server.system.domain.model.entity.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限范围
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oauth_scope_details")
public class ScopeDetails extends BaseDomain {
    private static final long serialVersionUID = 3137277581719708626L;
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

    public static final String SCOPE_ID = "scope_id";
    public static final String NAME = "name";
}
