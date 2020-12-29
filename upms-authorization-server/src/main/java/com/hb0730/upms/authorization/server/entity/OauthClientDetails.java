package com.hb0730.upms.authorization.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户端详情
 *
 * @author bing_huang
 */
@Data
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = -248594946089871021L;
    /**
     * client
     */
    @TableId(value = "client_id")
    private String clientId;

    /**
     * 所属资源id
     */
    @TableField("resource_ids")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 权限范围
     */
    @TableField("scope")
    private String scope;

    /**
     * 客户端支持的grant_typ
     */
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 重定向
     */
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值
     */
    @TableField("authorities")
    private String authorities;

    /**
     * 设定客户端的access_token的有效时间值(单位:秒)
     */
    @TableField("access_token_validity")
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
