package com.hb0730.admin.upms.server.system.project.scope.model.params;

import com.hb0730.admin.upms.server.system.domain.model.query.BaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 权限范围
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class ScopeDetailsParams extends BaseParams {
    /**
     * scopeid
     */
    private String scopeId;
    /**
     * name
     */
    private String name;
}
