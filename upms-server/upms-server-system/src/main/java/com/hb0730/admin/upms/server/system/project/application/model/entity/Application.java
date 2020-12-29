package com.hb0730.admin.upms.server.system.project.application.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hb0730.admin.upms.server.system.domain.model.entity.BaseDomain;
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
@TableName("sys_oauth_application")
public class Application extends BaseDomain {
    private static final long serialVersionUID = 279764117824815822L;
    /**
     * appid
     */
    @TableId(value = "app_id")
    private String appid;
    /**
     * name
     */
    @TableField(value = "name")
    private String name;

    public static final String APP_ID = "app_id";
    public static final String NAME = "name";
}
