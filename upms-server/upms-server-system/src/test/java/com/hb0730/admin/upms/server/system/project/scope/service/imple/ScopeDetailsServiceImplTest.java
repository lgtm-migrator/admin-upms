package com.hb0730.admin.upms.server.system.project.scope.service.imple;

import com.hb0730.admin.upms.server.system.SpringBaseTest;
import com.hb0730.admin.upms.server.system.project.scope.model.dto.ScopeDetailsDTO;
import com.hb0730.admin.upms.server.system.project.scope.service.IScopeDetailsService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScopeDetailsServiceImplTest extends SpringBaseTest {
    @Autowired
    private IScopeDetailsService service;

    @Test
    public void saveTest() {
        List<ScopeDetailsDTO> details = Lists.newArrayList();
        ScopeDetailsDTO scopeDetails = new ScopeDetailsDTO();
        scopeDetails.setScopeId("read");
        scopeDetails.setName("读");
        details.add(scopeDetails);
        scopeDetails = new ScopeDetailsDTO();
        scopeDetails.setScopeId("write");
        scopeDetails.setName("写");
        details.add(scopeDetails);
        scopeDetails = new ScopeDetailsDTO();
        scopeDetails.setScopeId("user");
        scopeDetails.setName("用户信息");
        details.add(scopeDetails);
        for (ScopeDetailsDTO dto : details) {
            service.save(dto);
        }

    }
}
