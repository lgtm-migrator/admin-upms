package com.hb0730.admin.upms.server.system.project.application.controller;

import com.hb0730.admin.upms.server.system.domain.controller.SuperBaseController;
import com.hb0730.admin.upms.server.system.project.application.model.dto.ApplicationDTO;
import com.hb0730.admin.upms.server.system.project.application.model.entity.Application;
import com.hb0730.admin.upms.server.system.project.application.model.params.ApplicationParams;
import com.hb0730.admin.upms.server.system.project.application.service.IApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用
 *
 * @author bing_huang
 */
@RestController
@RequestMapping("/api/v1//application")
public class ApplicationController extends SuperBaseController<String, ApplicationDTO, ApplicationParams, Application> {

    public ApplicationController(IApplicationService service) {
        super(service);
    }
}
