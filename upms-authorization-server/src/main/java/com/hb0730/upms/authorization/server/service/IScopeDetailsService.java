package com.hb0730.upms.authorization.server.service;

import com.hb0730.upms.authorization.server.entity.ScopeDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author bing_huang
 */
public interface IScopeDetailsService {
    /**
     * 根据id获取详情
     *
     * @param ids ids
     * @return 权限范围
     */
    List<ScopeDetails> findScopeByIds(Collection<String> ids);
}
