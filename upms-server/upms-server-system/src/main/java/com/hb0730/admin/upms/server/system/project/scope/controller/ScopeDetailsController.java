package com.hb0730.admin.upms.server.system.project.scope.controller;

import com.hb0730.admin.upms.server.system.domain.controller.SuperBaseController;
import com.hb0730.admin.upms.server.system.project.scope.model.dto.ScopeDetailsDTO;
import com.hb0730.admin.upms.server.system.project.scope.model.entity.ScopeDetails;
import com.hb0730.admin.upms.server.system.project.scope.model.params.ScopeDetailsParams;
import com.hb0730.admin.upms.server.system.project.scope.service.IScopeDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bing_huang
 */
@RestController
@RequestMapping("/api/v1/scope")
public class ScopeDetailsController extends SuperBaseController<String, ScopeDetailsDTO, ScopeDetailsParams, ScopeDetails> {
    public ScopeDetailsController(IScopeDetailsService service) {
        super(service);
    }
}
