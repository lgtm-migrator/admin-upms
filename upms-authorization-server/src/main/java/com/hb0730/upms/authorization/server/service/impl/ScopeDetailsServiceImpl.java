package com.hb0730.upms.authorization.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.upms.authorization.server.entity.ScopeDetails;
import com.hb0730.upms.authorization.server.mapper.IScopeDetailsMapper;
import com.hb0730.upms.authorization.server.service.IScopeDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 权限范围
 *
 * @author bing_huang
 */
@Service
public class ScopeDetailsServiceImpl extends ServiceImpl<IScopeDetailsMapper, ScopeDetails> implements IScopeDetailsService {
    @Override
    public List<ScopeDetails> findScopeByIds(Collection<String> ids) {
        return listByIds(ids);
    }
}
