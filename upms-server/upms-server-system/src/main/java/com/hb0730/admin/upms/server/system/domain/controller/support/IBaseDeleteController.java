package com.hb0730.admin.upms.server.system.domain.controller.support;

import com.hb0730.admin.upms.server.system.domain.model.entity.BaseDomain;
import com.hb0730.admin.upms.server.system.domain.service.ISuperBaseService;
import com.hb0730.admin.upms.commons.entity.ResponseResult;
import com.hb0730.admin.upms.commons.entity.constant.ResponseMessageConstant;
import com.hb0730.commons.lang.collection.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * 删除
 *
 * @param <ID>     ID类型
 * @param <ENTITY> 实体类型
 * @author bing_huang
 */
public interface IBaseDeleteController<ID extends Serializable, ENTITY extends BaseDomain> extends IBaseController<ENTITY> {

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @GetMapping("/delete/{id}")
    @SuppressWarnings({"rawtypes"})
    default ResponseResult deleteById(@PathVariable("id") ID id) {
        ISuperBaseService service = getBaseService();
        ResponseResult responseResult = ResponseResult.newInstance();
        if (null != service) {
            boolean remove = service.removeById(id);
            return responseResult.data(remove);
        }
        return responseResult.message(ResponseMessageConstant.PARAMS_REQUIRED_IS_NULL);
    }

    /**
     * 批量删除
     *
     * @param ids id集合
     * @return 是否成功
     */
    @PostMapping("/delete")
    @SuppressWarnings({"rawtypes"})
    default ResponseResult deleteByIds(@RequestBody List<ID> ids) {
        ISuperBaseService service = getBaseService();
        ResponseResult responseResult = ResponseResult.newInstance();
        if (null != service && CollectionUtils.isNotEmpty(ids)) {
            boolean remove = service.removeByIds(ids);
            return responseResult.data(remove);
        }
        return responseResult.message(ResponseMessageConstant.PARAMS_REQUIRED_IS_NULL);
    }
}
