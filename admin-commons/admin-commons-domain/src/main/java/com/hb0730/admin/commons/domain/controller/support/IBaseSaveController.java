package com.hb0730.admin.commons.domain.controller.support;

import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import com.hb0730.admin.commons.domain.model.entity.BaseDomain;
import com.hb0730.admin.commons.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.commons.entity.ResponseResult;
import com.hb0730.admin.upms.commons.entity.constant.ResponseMessageConstant;
import com.hb0730.commons.spring.ValidatorUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 基础新增
 *
 * @param <ENTITY> 实体类型
 * @param <DTO>    DTO类型
 * @author bing_huang
 */
public interface IBaseSaveController<DTO extends BaseDTO, ENTITY extends BaseDomain> extends IBaseController<ENTITY> {

    /**
     * 保存
     *
     * @param dto dto
     * @return 是否成功
     */
    @PostMapping("/save")
    @SuppressWarnings({"rawtypes", "unchecked"})
    default ResponseResult save(@Validated @RequestBody DTO dto) {
        ValidatorUtils.validate(dto);
        ISuperBaseService service = getBaseService();
        ResponseResult responseResult = ResponseResult.newInstance();
        if (null != service) {
            boolean save = service.save(dto);
            return responseResult.data(save);
        }
        return responseResult.message(ResponseMessageConstant.PARAMS_REQUIRED_IS_NULL);
    }
}
