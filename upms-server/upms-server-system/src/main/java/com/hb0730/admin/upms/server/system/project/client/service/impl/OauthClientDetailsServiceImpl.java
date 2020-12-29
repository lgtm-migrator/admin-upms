package com.hb0730.admin.upms.server.system.project.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.admin.upms.server.system.domain.service.impl.SuperBaseServiceImpl;
import com.hb0730.admin.upms.server.system.exceptions.ServerException;
import com.hb0730.admin.upms.server.system.project.client.mapper.IOauthClientDetailsMapper;
import com.hb0730.admin.upms.server.system.project.client.model.dto.OauthClientDetailsDTO;
import com.hb0730.admin.upms.server.system.project.client.model.entity.OauthClientDetails;
import com.hb0730.admin.upms.server.system.project.client.model.params.OauthClientDetailsParams;
import com.hb0730.admin.upms.server.system.project.client.service.IOauthClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
public class OauthClientDetailsServiceImpl extends SuperBaseServiceImpl<String, OauthClientDetailsParams, OauthClientDetailsDTO, OauthClientDetails, IOauthClientDetailsMapper> implements IOauthClientDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean save(OauthClientDetails entity) {
        verify(entity, false);
        entity.setClientSecret(passwordEncoder.encode(entity.getClientSecret()));
        return super.save(entity);
    }

    @Override
    public boolean updateById(@NonNull OauthClientDetailsDTO dto) {
        OauthClientDetails details = getById(dto.getClientId());
        dto.setClientSecret(details.getClientSecret());
        return super.updateById(dto);
    }

    @Override
    public boolean updateById(@NonNull String id, @NonNull OauthClientDetailsDTO dto) {
        Assert.notNull(id, "id不为空");
        Assert.notNull(dto, "修改参数不为空");
        OauthClientDetails details = super.getById(id);
        dto.setClientSecret(details.getClientSecret());
        return super.updateById(id, dto);
    }

    @Override
    public boolean updateById(OauthClientDetails entity) {
        verify(entity, true);
        return super.updateById(entity);
    }

    private void verify(OauthClientDetails entity, boolean isUpdate) {
        if (null == entity) {
            throw new ServerException("实体为空");
        }
        String clientId = entity.getClientId();
        if (StringUtils.isBlank(clientId)) {
            throw new ServerException("clientId为空");
        }
        String secret = entity.getClientSecret();
        if (StringUtils.isBlank(secret)) {
            throw new ServerException("%s 密钥为空", clientId);
        }
        String grantTypes = entity.getAuthorizedGrantTypes();
        if (StringUtils.isBlank(grantTypes)) {
            throw new ServerException("%s 授权方式为空", clientId);
        }
        String scope = entity.getScope();
        if (StringUtils.isBlank(scope)) {
            throw new ServerException("%s 授权访问为空", clientId);
        }
        Integer accessToken = entity.getAccessTokenValidity();
        if (null == accessToken) {
            throw new ServerException("%s access_token有效时间为空", clientId);
        }
        LambdaQueryWrapper<OauthClientDetails> queryWrapper = Wrappers
                .lambdaQuery(OauthClientDetails.class)
                .eq(OauthClientDetails::getClientId, clientId);
        int count = super.count(queryWrapper);
        if (isUpdate && count > 1) {
            throw new ServerException("%s 重复", clientId);
        } else if (!isUpdate && count > 0) {
            throw new ServerException("%s 重复", clientId);
        }
    }
}
