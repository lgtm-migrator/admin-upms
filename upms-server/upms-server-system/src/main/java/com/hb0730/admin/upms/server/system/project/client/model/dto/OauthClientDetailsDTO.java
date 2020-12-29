package com.hb0730.admin.upms.server.system.project.client.model.dto;

import com.hb0730.admin.upms.server.system.domain.model.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 客户端详情
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OauthClientDetailsDTO extends BaseDTO {
    private static final long serialVersionUID = -380264374802235816L;
    /**
     * client
     */
    @NotBlank(message = "ClientId必填项")
    @Size(max = 255, message = "clientId最大值为255")
    private String clientId;

    /**
     * 所属资源id
     */
    @Size(max = 255, message = "resourcesIds最大值为255")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @NotBlank(message = "clientSecret必填项")
    @Size(max = 255, message = "clientSecret最大值为255")
    private String clientSecret;

    /**
     * 权限范围
     */
    @NotBlank(message = "scope为必填项")
    @Size(max = 255, message = "scope最大值为255")
    private String scope;

    /**
     * 客户端支持的grant_typ
     */
    @NotBlank(message = "grant_typ授权方式必填项")
    @Size(max = 255, message = "授权方式最大值为255")
    private String authorizedGrantTypes;

    /**
     * 重定向
     */
    @Size(max = 255, message = "重定向的最大值为255")
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值
     */
    @Size(max = 255, message = "客户端权限信息最大值为255")
    private String authorities;

    /**
     * 设定客户端的access_token的有效时间值(单位:秒)
     */
    @NotNull(message = "access_token必填项")
    private Integer accessTokenValidity;

    /**
     * 设定客户端的refresh_token的有效时间值(单位:秒),可选
     */
    private Integer refreshTokenValidity;

    /**
     * 是否自动授权
     */
    private String autoapprove;

    /**
     * 这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:
     */
    private String additionalInformation;
}
