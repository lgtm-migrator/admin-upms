package com.hb0730.admin.commons.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import com.hb0730.admin.commons.domain.model.entity.BaseDomain;
import com.hb0730.admin.commons.domain.model.query.BaseParams;
import com.hb0730.admin.commons.domain.service.support.ISuperQueryService;
import com.hb0730.admin.commons.domain.service.support.ISuperSaveService;
import com.hb0730.admin.commons.domain.service.support.ISuperUpdateService;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * 此类为总类，包含了{@link IService}和 <code>support</code>下所有方法，此外还新增了
 * <ul>
 *     <li>
 *         {@link #query(BaseParams)} 查询拼接
 *     </li>
 *     <li>
 *         {@link #getThis()} 获取当前实现类
 *     </li>
 * </ul>
 *
 * @param <ID>     id类型
 * @param <PARAMS> 请求参数类型
 * @param <DTO>    dto类型
 * @param <ENTITY> 实体类型
 */
public interface ISuperBaseService<ID extends Serializable,
        PARAMS extends BaseParams,
        DTO extends BaseDTO,
        ENTITY extends BaseDomain>
        extends
        IService<ENTITY>,
        ISuperQueryService<PARAMS, DTO>,
        ISuperSaveService<DTO>,
        ISuperUpdateService<ID, DTO> {

    /**
     * 构造QueryWrapper
     *
     * @param params 请求参数
     * @return QueryWrapper
     */
    QueryWrapper<ENTITY> query(@NonNull PARAMS params);

    /**
     * 获取当前类
     *
     * @param <T> 实现类类型
     * @return 实现类
     */
    <T> T getThis();
}
