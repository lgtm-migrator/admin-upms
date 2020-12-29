package com.hb0730.admin.upms.server.system.project.application.model.dto;

import com.hb0730.admin.upms.server.system.domain.model.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 应用信息
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class ApplicationDTO extends BaseDTO {
    private static final long serialVersionUID = 279764117824815822L;
    /**
     * appid
     */
    @NotBlank(message = "appid不为空")
    private String appid;
    /**
     * name
     */
    @NotBlank(message = "app name不为空")
    private String name;
}
