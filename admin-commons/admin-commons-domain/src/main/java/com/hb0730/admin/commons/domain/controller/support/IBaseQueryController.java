package com.hb0730.admin.commons.domain.controller.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb0730.admin.commons.domain.model.dto.BaseDTO;
import com.hb0730.admin.commons.domain.model.entity.BaseDomain;
import com.hb0730.admin.commons.domain.model.query.BaseParams;
import com.hb0730.admin.commons.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.commons.entity.ResponseResult;
import com.hb0730.admin.upms.commons.entity.constant.ResponseMessageConstant;
import com.hb0730.commons.spring.ValidatorUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基础查询
 * <ul>
 *     <li>
 *       列表查询   {@link #list(BaseParams)}
 *     </li>
 *     <li>
 *        分页查询 {@link #page(BaseParams)}
 *     </li>
 *     <li>
 *         获取DTOClass {@link #getDtoClass()}
 *     </li>
 * </ul>
 *
 * @param <DTO>    DTO类型
 * @param <PARAMS> 过滤参数类型
 * @param <ENTITY> 实体类型
 * @param <ID>     ID类型
 * @author bing_huang
 */
public interface IBaseQueryController<ID extends Serializable,
        DTO extends BaseDTO,
        PARAMS extends BaseParams,
        ENTITY extends BaseDomain> extends IBaseController<ENTITY> {

    /**
     * 列表查询
     *
     * @param params 过滤参数
     * @return 列表
     */
    @SuppressWarnings({"unchecked"})
    @PostMapping("/list")
    default ResponseResult list(@Validated @RequestBody PARAMS params) {
        ValidatorUtils.validate(params);
        ISuperBaseService<ID, PARAMS, DTO, ENTITY> service = getBaseService();
        ResponseResult responseResult = ResponseResult.newInstance();
        if (null != service) {
            List<DTO> list = service.list(params);
            return responseResult.data(list);
        }
        return responseResult.message(ResponseMessageConstant.PARAMS_REQUIRED_IS_NULL);
    }

    /**
     * 分页查询
     *
     * @param params 过滤参数
     * @return 分页列表
     */
    @PostMapping("/list/page")
    @SuppressWarnings({"unchecked"})
    default ResponseResult page(@Validated @RequestBody PARAMS params) {
        ValidatorUtils.validate(params);
        ISuperBaseService<ID, PARAMS, DTO, ENTITY> service = getBaseService();
        ResponseResult responseResult = ResponseResult.newInstance();
        if (null != service) {
            Page<DTO> page = service.page(params);
            return responseResult.data(page);
        }
        return responseResult.message(ResponseMessageConstant.PARAMS_REQUIRED_IS_NULL);
    }

    /**
     * 获取dto具体的类型
     *
     * @return {@link Class}类型的DTO
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    default Class<DTO> getDtoClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
