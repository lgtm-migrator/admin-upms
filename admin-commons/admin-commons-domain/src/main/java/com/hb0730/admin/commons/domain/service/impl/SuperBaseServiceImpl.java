package com.hb0730.admin.commons.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import com.hb0730.admin.commons.domain.model.entity.BaseDomain;
import com.hb0730.admin.commons.domain.model.query.BaseParams;
import com.hb0730.admin.commons.domain.service.ISuperBaseService;
import com.hb0730.admin.commons.domain.utils.QueryWrapperUtils;
import com.hb0730.commons.spring.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/*** 基础service 实现基本的curd<br>
 * 所有的service应当继承此类,以便Controller基类的调用{@link com.hb0730.admin.commons.domain.controller.SuperBaseController}
 * @author bing_huang
 */
public class SuperBaseServiceImpl<
        ID extends Serializable,
        PARAMS extends BaseParams,
        DTO extends BaseDTO,
        ENTITY extends BaseDomain,
        MAPPER extends BaseMapper<ENTITY>
        >
        extends
        BaseMybatisPlusServiceImpl<MAPPER, ENTITY> implements ISuperBaseService<ID, PARAMS, DTO, ENTITY> {

    @Override
    public QueryWrapper<ENTITY> query(@NonNull PARAMS params) {
        Assert.notNull(params, "参数不为空");
        return QueryWrapperUtils.getQuery(params);
    }

    @Override
    public <T> T getThis() {
        return null;
    }

    @Override
    public Page<DTO> page(@NonNull PARAMS params) {
        Assert.notNull(params, "参数不为空");
        QueryWrapper<ENTITY> query = query(params);
        Page<ENTITY> page = QueryWrapperUtils.getPage(params);
        page = page(page, query);
        return QueryWrapperUtils.pageToBean(page, getDtoClass());
    }

    @Override
    public List<DTO> list(@NonNull PARAMS params) {
        Assert.notNull(params, "参数不为空");
        QueryWrapper<ENTITY> query = query(params);
        List<ENTITY> entities = list(query);
        return BeanUtils.transformFromInBatch(entities, getDtoClass());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(@NonNull DTO dto) {
        Assert.notNull(dto, "参数不为空");
        Class<ENTITY> entityClass = getEntityClass();
        ENTITY entity = BeanUtils.transformFrom(dto, entityClass);
        return save(entity);
    }

    @Override
    public boolean updateById(@NonNull DTO dto) {
        Assert.notNull(dto, "参数不为空");
        Class<ENTITY> entityClass = getEntityClass();
        ENTITY entity = BeanUtils.transformFrom(dto, entityClass);
        return updateById(entity);
    }

    @Override
    public boolean updateById(@NonNull ID id, @NonNull DTO dto) {
        Assert.notNull(id, "id不为空");
        Assert.notNull(dto, "修改参数不为空");
        ENTITY entity = super.getById(id);
        BeanUtils.updateProperties(dto, entity);
        return updateById(entity);
    }

    @SuppressWarnings({"unchecked"})
    protected Class<DTO> getDtoClass() {
        return (Class<DTO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Class<ENTITY> getEntityClass() {
        return (Class<ENTITY>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[3];
    }

    @Override
    @SuppressWarnings({"unchecked"})
    protected Class<ENTITY> currentModelClass() {
        return (Class<ENTITY>) ReflectionKit.getSuperClassGenericType(getClass(), 3);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    protected Class<ENTITY> currentMapperClass() {
        return (Class<ENTITY>) ReflectionKit.getSuperClassGenericType(getClass(), 4);
    }
}
