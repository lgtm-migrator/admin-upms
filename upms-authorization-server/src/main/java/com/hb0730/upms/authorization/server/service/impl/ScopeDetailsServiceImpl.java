package com.hb0730.upms.authorization.server.service.impl;

import com.hb0730.upms.authorization.server.entity.ScopeDetails;
import com.hb0730.upms.authorization.server.service.IScopeDetailsService;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限范围
 *
 * @author bing_huang
 */
@Service
public class ScopeDetailsServiceImpl implements IScopeDetailsService {
    @Override
    public List<ScopeDetails> findScopeByIds(Collection<String> ids) {
        List<ScopeDetails> details = scopeDetails();
        return details.stream().filter(detail -> ids.contains(detail.getScopeId())).collect(Collectors.toList());
    }

    public static List<ScopeDetails> scopeDetails() {
        List<ScopeDetails> details = Lists.newArrayList();
        ScopeDetails scopeDetails = new ScopeDetails();
        scopeDetails.setScopeId("read");
        scopeDetails.setName("读");
        details.add(scopeDetails);
        scopeDetails = new ScopeDetails();
        scopeDetails.setScopeId("write");
        scopeDetails.setName("写");
        details.add(scopeDetails);
        scopeDetails = new ScopeDetails();
        scopeDetails.setScopeId("user");
        scopeDetails.setName("用户信息");
        details.add(scopeDetails);
        return details;
    }
}
