package com.hb0730.admin.commons.domain.service.support;

import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import org.springframework.lang.NonNull;

/**
 * 主要扩展保存，新增
 * <ul>
 *     <li>
 *      {@link #save(BaseDTO)} 直接通过dto方式保存
 *     </li>
 * </ul>
 *
 * @param <DTO> DTO类型
 * @author bing_huang
 */
public interface ISuperSaveService<DTO extends BaseDTO> {
    /**
     * 保存
     *
     * @param dto 信息
     * @return 是否成功
     */
    boolean save(@NonNull DTO dto);
}
