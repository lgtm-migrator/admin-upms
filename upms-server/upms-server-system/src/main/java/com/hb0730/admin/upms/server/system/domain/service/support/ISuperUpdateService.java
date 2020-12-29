package com.hb0730.admin.upms.server.system.domain.service.support;

import com.hb0730.admin.upms.server.system.domain.model.dto.BaseDTO;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * 扩展对修改的方法
 *
 * <ul>
 *      <li>
 *          1. 通过DTO修改 {@link #updateById(BaseDTO)}
 *      </li>
 *      <li>
 *          2. 通过id修改 {@link #updateById(Serializable, BaseDTO)}
 *      </li>
 * </ul>
 *
 * @author bing_huang
 */
public interface ISuperUpdateService<ID extends Serializable, DTO extends BaseDTO> {


    /**
     * 根据id修改
     *
     * @param dto 显示层对象类型
     * @return 是否成功
     */
    boolean updateById(@NonNull DTO dto);

    /**
     * 根据id修改，该修改会先查询更新值后进行修改
     *
     * @param id  id
     * @param dto 修改参数
     * @return 是否成功
     */
    boolean updateById(@NonNull ID id, @NonNull DTO dto);
}
