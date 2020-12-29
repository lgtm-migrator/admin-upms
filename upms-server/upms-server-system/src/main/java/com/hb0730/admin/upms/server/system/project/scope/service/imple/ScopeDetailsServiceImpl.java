package com.hb0730.admin.upms.server.system.project.scope.service.imple;

import com.hb0730.admin.upms.server.system.domain.service.impl.SuperBaseServiceImpl;
import com.hb0730.admin.upms.server.system.project.scope.mapper.IScopeDetailsMapper;
import com.hb0730.admin.upms.server.system.project.scope.model.dto.ScopeDetailsDTO;
import com.hb0730.admin.upms.server.system.project.scope.model.entity.ScopeDetails;
import com.hb0730.admin.upms.server.system.project.scope.model.params.ScopeDetailsParams;
import com.hb0730.admin.upms.server.system.project.scope.service.IScopeDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 */
@Service
public class ScopeDetailsServiceImpl extends SuperBaseServiceImpl<String, ScopeDetailsParams, ScopeDetailsDTO, ScopeDetails, IScopeDetailsMapper> implements IScopeDetailsService {
}
