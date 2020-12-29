package com.hb0730.admin.upms.server.system.project.scope.service;

import com.hb0730.admin.upms.server.system.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.server.system.project.scope.model.dto.ScopeDetailsDTO;
import com.hb0730.admin.upms.server.system.project.scope.model.entity.ScopeDetails;
import com.hb0730.admin.upms.server.system.project.scope.model.params.ScopeDetailsParams;

/**
 * 权限范围 服务类
 *
 * @author bing_huang
 */
public interface IScopeDetailsService extends ISuperBaseService<String, ScopeDetailsParams, ScopeDetailsDTO, ScopeDetails> {
}
