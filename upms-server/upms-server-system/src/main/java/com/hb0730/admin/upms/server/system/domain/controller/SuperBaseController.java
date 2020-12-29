package com.hb0730.admin.upms.server.system.domain.controller;

import com.hb0730.admin.upms.server.system.domain.controller.support.IBaseDeleteController;
import com.hb0730.admin.upms.server.system.domain.controller.support.IBaseQueryController;
import com.hb0730.admin.upms.server.system.domain.controller.support.IBaseSaveController;
import com.hb0730.admin.upms.server.system.domain.controller.support.IBaseUpdateController;
import com.hb0730.admin.upms.server.system.domain.model.dto.BaseDTO;
import com.hb0730.admin.upms.server.system.domain.model.entity.BaseDomain;
import com.hb0730.admin.upms.server.system.domain.model.query.BaseParams;
import com.hb0730.admin.upms.server.system.domain.service.ISuperBaseService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * 简单的实现了BaseController，<br>
 * 为了获取注入 Service 和 实体类型,实现基本的curd与数据校验已经对应的权限
 *
 * @param <ENTITY> 实体类型
 * @param <ID>     ID类型
 * @param <DTO>    DTO类型
 * @param <PARAMS> 过滤参数类型
 * @author bing_huang
 */
public class SuperBaseController<ID extends Serializable, DTO extends BaseDTO, PARAMS extends BaseParams, ENTITY extends BaseDomain> implements
        IBaseQueryController<ID, DTO, PARAMS, ENTITY>,
        IBaseSaveController<DTO, ENTITY>,
        IBaseUpdateController<ID, DTO, ENTITY>,
        IBaseDeleteController<ID, ENTITY> {
    private ISuperBaseService<ID, PARAMS, DTO, ENTITY> service;
    protected Class<ENTITY> entityClass = null;

    public SuperBaseController(ISuperBaseService<ID, PARAMS, DTO, ENTITY> service) {
        this.service = service;
    }

    public SuperBaseController() {
    }

    @Override
    public ISuperBaseService<ID, PARAMS, DTO, ENTITY> getBaseService() {
        return service;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Class<ENTITY> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[3];
        }
        return this.entityClass;
    }

}
