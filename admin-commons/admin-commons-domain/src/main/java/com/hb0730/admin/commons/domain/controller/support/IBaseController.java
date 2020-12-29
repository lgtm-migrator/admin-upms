package com.hb0730.admin.commons.domain.controller.support;

import com.hb0730.admin.commons.domain.model.entity.BaseDomain;
import com.hb0730.admin.commons.domain.service.ISuperBaseService;

/**
 * 基础的Controller
 * <ul>
 *     <li>
 *         获取当前实体Class {@link #getEntityClass()}
 *     </li>
 *     <li>
 *         获取当前Service {@link #getBaseService()}
 *     </li>
 * </ul>
 *
 * @param <ENTITY> 实体类
 * @author bing_huang
 */
@SuppressWarnings({"rawtypes"})
public interface IBaseController<ENTITY extends BaseDomain> {
    /**
     * 获取当前实体类型
     *
     * @return 实体Class
     */
    Class<ENTITY> getEntityClass();

    /**
     * 获取当前Service
     *
     * @return 当前Service {@link ISuperBaseService}
     */
    ISuperBaseService getBaseService();
}
