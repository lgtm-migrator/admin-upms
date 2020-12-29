package com.hb0730.admin.upms.server.system.project.client.model.params;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hb0730.admin.upms.server.system.domain.model.query.BaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oauth_client_details")
public class OauthClientDetailsParams extends BaseParams {
}
