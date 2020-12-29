package com.hb0730.admin.commons.domain.service.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import com.hb0730.admin.commons.domain.model.query.BaseParams;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 扩展service查询，主要新增
 * <ul>
 *     <li>
 *          分页查询  {@link #page(BaseParams)}
 *     </li>
 *     <li>
 *          列表查询 {@link #list(BaseParams)}
 *     </li>
 * </ul>
 *
 * @param <DTO>    DTO类型
 * @param <PARAMS> 过滤参数类型
 * @author bing_huang
 */
public interface ISuperQueryService<PARAMS extends BaseParams, DTO extends BaseDTO> {
    /**
     * 分页
     *
     * @param params 过滤参数
     * @return 分页列表
     */
    Page<DTO> page(@NonNull PARAMS params);

    /**
     * 列表查询
     *
     * @param params 过滤参数
     * @return 列表
     */
    List<DTO> list(@NonNull PARAMS params);
}
