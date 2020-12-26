package com.hb0730.upms.authorization.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb0730.upms.authorization.server.entity.OauthClientDetails;

/**
 * @author bing_huang
 */
public interface IOauthClientDetailsService extends IService<OauthClientDetails> {
    /**
     * 根据主键查询
     *
     * @param clientId clientId
     * @return OauthClientDetails
     */
    OauthClientDetails findById(String clientId);

    /**
     * 新增
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void createOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 修改
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void updateOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 删除
     *
     * @param clientIds clientIds
     */
    void deleteOauthClientDetails(String clientIds);
}
