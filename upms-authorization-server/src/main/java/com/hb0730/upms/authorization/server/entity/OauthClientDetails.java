package com.hb0730.upms.authorization.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 客户端详情
 *
 * @author bing_huang
 */
@Data
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 421783821058285802L;

    /**
     * client
     */
    @TableId(value = "client_id")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String clientId;

    /**
     * 所属资源id
     */
    @TableField("resource_ids")
    @Size(max = 255, message = "{noMoreThan}")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @TableField("client_secret")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String clientSecret;

    /**
     * 权限范围
     */
    @TableField("scope")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String scope;

    /**
     * 客户端支持的grant_typ
     */
    @TableField("authorized_grant_types")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String authorizedGrantTypes;

    /**
     * 重定向
     */
    @TableField("web_server_redirect_uri")
    @Size(max = 255, message = "{noMoreThan}")
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值
     */
    @TableField("authorities")
    @Size(max = 255, message = "{noMoreThan}")
    private String authorities;

    /**
     * 设定客户端的access_token的有效时间值(单位:秒)
     */
    @TableField("access_token_validity")
    @NotNull(message = "{required}")
    private Integer accessTokenValidity;

    /**
     * 设定客户端的refresh_token的有效时间值(单位:秒),可选
     */
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 是否自动授权
     */
    @TableField("autoapprove")
    private Byte autoapprove;

    /**
     * 这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:
     */
    @TableField("additional_information")
    private String additionalInformation;

}
