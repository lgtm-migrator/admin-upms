package com.hb0730.admin.upms.server.system.project.client.service;

import com.hb0730.admin.upms.server.system.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.server.system.project.client.model.dto.OauthClientDetailsDTO;
import com.hb0730.admin.upms.server.system.project.client.model.entity.OauthClientDetails;
import com.hb0730.admin.upms.server.system.project.client.model.params.OauthClientDetailsParams;

/**
 * @author bing_huang
 */
public interface IOauthClientDetailsService extends ISuperBaseService<String, OauthClientDetailsParams, OauthClientDetailsDTO, OauthClientDetails> {
}
