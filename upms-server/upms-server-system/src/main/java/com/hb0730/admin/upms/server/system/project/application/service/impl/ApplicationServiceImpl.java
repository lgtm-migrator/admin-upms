package com.hb0730.admin.upms.server.system.project.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.admin.upms.server.system.domain.service.impl.SuperBaseServiceImpl;
import com.hb0730.admin.upms.server.system.domain.utils.QueryWrapperUtils;
import com.hb0730.admin.upms.server.system.exceptions.ServerException;
import com.hb0730.admin.upms.server.system.project.application.mapper.IApplicationMapper;
import com.hb0730.admin.upms.server.system.project.application.model.dto.ApplicationDTO;
import com.hb0730.admin.upms.server.system.project.application.model.entity.Application;
import com.hb0730.admin.upms.server.system.project.application.model.params.ApplicationParams;
import com.hb0730.admin.upms.server.system.project.application.service.IApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 应用 服务类
 *
 * @author bing_huang
 */
@Service
public class ApplicationServiceImpl extends SuperBaseServiceImpl<String, ApplicationParams, ApplicationDTO, Application, IApplicationMapper> implements IApplicationService {
    @Override
    public boolean save(Application entity) {
        verify(entity, false);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Application entity) {
        verify(entity, true);
        return super.updateById(entity);
    }

    @Override
    public QueryWrapper<Application> query(@NonNull ApplicationParams params) {
        QueryWrapper<Application> query = QueryWrapperUtils.getQuery(params);
        if (StringUtils.isBlank(params.getAppid())) {
            query.eq(Application.APP_ID, params.getAppid());
        }
        if (StringUtils.isBlank(params.getName())) {
            query.like(Application.NAME, params.getName());
        }
        return query;
    }

    private void verify(Application entity, boolean isUpdate) {
        if (null == entity) {
            throw new ServerException("实体为空");
        }
        String appid = entity.getAppid();
        if (StringUtils.isBlank(appid)) {
            throw new ServerException("appid为空");
        }
        String name = entity.getName();
        if (StringUtils.isBlank(name)) {
            throw new ServerException("%s 名称为空", appid);
        }
        LambdaQueryWrapper<Application> queryWrapper = Wrappers.lambdaQuery(Application.class).eq(Application::getAppid, appid);
        int count = super.count(queryWrapper);
        if (isUpdate && count > 1) {
            throw new ServerException("%s 重复", appid);
        } else if (!isUpdate && count > 0) {
            throw new ServerException("%s 重复", appid);
        }
    }
}
