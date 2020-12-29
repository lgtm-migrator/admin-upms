package com.hb0730.admin.upms.server.system.project.application.model.params;

import com.hb0730.admin.upms.server.system.domain.model.query.BaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 应用信息
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class ApplicationParams extends BaseParams {
    private static final long serialVersionUID = 279764117824815822L;
    /**
     * appid
     */
    private String appid;
    /**
     * name
     */
    private String name;
}
