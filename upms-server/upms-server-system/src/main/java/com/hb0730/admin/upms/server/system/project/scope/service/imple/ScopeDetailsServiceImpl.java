package com.hb0730.admin.upms.server.system.project.scope.service.imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.admin.upms.server.system.domain.service.impl.SuperBaseServiceImpl;
import com.hb0730.admin.upms.server.system.domain.utils.QueryWrapperUtils;
import com.hb0730.admin.upms.server.system.exceptions.ServerException;
import com.hb0730.admin.upms.server.system.project.scope.mapper.IScopeDetailsMapper;
import com.hb0730.admin.upms.server.system.project.scope.model.dto.ScopeDetailsDTO;
import com.hb0730.admin.upms.server.system.project.scope.model.entity.ScopeDetails;
import com.hb0730.admin.upms.server.system.project.scope.model.params.ScopeDetailsParams;
import com.hb0730.admin.upms.server.system.project.scope.service.IScopeDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 */
@Service
public class ScopeDetailsServiceImpl extends SuperBaseServiceImpl<String, ScopeDetailsParams, ScopeDetailsDTO, ScopeDetails, IScopeDetailsMapper> implements IScopeDetailsService {
    @Override
    public boolean save(ScopeDetails entity) {
        verify(entity, false);
        return super.save(entity);
    }

    @Override
    public boolean updateById(ScopeDetails entity) {
        verify(entity, true);
        return super.updateById(entity);
    }

    @Override
    public QueryWrapper<ScopeDetails> query(@NonNull ScopeDetailsParams params) {
        QueryWrapper<ScopeDetails> query = QueryWrapperUtils.getQuery(params);
        if (StringUtils.isBlank(params.getScopeId())) {
            query.eq(ScopeDetails.SCOPE_ID, params.getScopeId());
        }
        if (StringUtils.isBlank(params.getName())) {
            query.like(ScopeDetails.NAME, params.getName());
        }
        return query;
    }

    private void verify(ScopeDetails entity, boolean isUpdate) {
        if (null == entity) {
            throw new ServerException("实体为空");
        }
        String scopeid = entity.getScopeId();
        if (StringUtils.isBlank(scopeid)) {
            throw new ServerException("scopeId为空");
        }
        String name = entity.getName();
        if (StringUtils.isBlank(name)) {
            throw new ServerException("%s 名称为空", scopeid);
        }
        LambdaQueryWrapper<ScopeDetails> queryWrapper = Wrappers
                .lambdaQuery(ScopeDetails.class)
                .eq(ScopeDetails::getScopeId, scopeid);
        int count = super.count(queryWrapper);
        if (isUpdate && count > 1) {
            throw new ServerException("%s 重复", scopeid);
        } else if (!isUpdate && count > 0) {
            throw new ServerException("%s 重复", scopeid);
        }
    }
}
