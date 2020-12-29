package com.hb0730.admin.commons.domain.controller.support;

import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import com.hb0730.admin.commons.domain.model.entity.BaseDomain;
import com.hb0730.admin.commons.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.commons.entity.ResponseResult;
import com.hb0730.admin.upms.commons.entity.constant.ResponseMessageConstant;
import com.hb0730.commons.spring.ValidatorUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * 修改
 *
 * @param <DTO>    DTO类型
 * @param <ENTITY> 实体类型
 * @param <ID>     ID类型
 * @author bing_huang
 */
public interface IBaseUpdateController<ID extends Serializable, DTO extends BaseDTO, ENTITY extends BaseDomain> extends IBaseController<ENTITY> {

    /**
     * 根据id修改
     *
     * @param id  id
     * @param dto 修改参数
     * @return 是否成功
     */
    @PostMapping("/update/{id}")
    @SuppressWarnings({"rawtypes", "unchecked"})
    default ResponseResult updateById(@PathVariable("id") ID id, @RequestBody @Validated DTO dto) {
        ValidatorUtils.validate(dto);
        ISuperBaseService service = getBaseService();
        ResponseResult responseResult = ResponseResult.newInstance();
        if (null != service) {
            boolean update = service.updateById(id, dto);
            return responseResult.data(update);
        }
        return responseResult.message(ResponseMessageConstant.PARAMS_REQUIRED_IS_NULL);
    }
}
