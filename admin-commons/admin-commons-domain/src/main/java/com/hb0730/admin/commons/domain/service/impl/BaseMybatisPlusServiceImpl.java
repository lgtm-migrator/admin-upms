package com.hb0730.admin.commons.domain.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 扩展mybatisPlus的{@link ServiceImpl},解决扩展问题
 * 基类service(填充删除修改) <br>
 * mybatis Plus 无法填充 非entity {@link com.baomidou.mybatisplus.core.MybatisParameterHandler#process(Object)} <br>
 *
 * @author bing_huang
 */
public class BaseMybatisPlusServiceImpl<MAPPER extends BaseMapper<ENTITY>, ENTITY> extends ServiceImpl<MAPPER, ENTITY> {

    @Override
    @SuppressWarnings({"unchecked"})
    protected Class<ENTITY> currentMapperClass() {
        return (Class<ENTITY>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    protected Class<ENTITY> currentModelClass() {
        return (Class<ENTITY>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }
}
