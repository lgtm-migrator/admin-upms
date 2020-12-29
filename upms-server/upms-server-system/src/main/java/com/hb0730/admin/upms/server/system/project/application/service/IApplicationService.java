package com.hb0730.admin.upms.server.system.project.application.service;

import com.hb0730.admin.upms.server.system.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.server.system.project.application.model.dto.ApplicationDTO;
import com.hb0730.admin.upms.server.system.project.application.model.entity.Application;
import com.hb0730.admin.upms.server.system.project.application.model.params.ApplicationParams;

/**
 * 应用 服务类
 *
 * @author bing_huang
 */
public interface IApplicationService extends ISuperBaseService<String, ApplicationParams, ApplicationDTO, Application> {
}
