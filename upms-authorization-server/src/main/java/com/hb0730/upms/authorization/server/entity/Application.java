package com.hb0730.upms.authorization.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bing_huang
 */
@Data
@TableName("sys_oauth_application")
public class Application implements Serializable {
    private static final long serialVersionUID = -2092675914831375846L;
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
}
