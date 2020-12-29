package com.hb0730.admin.upms.server.system.project.application.service.impl;


import com.hb0730.admin.upms.server.system.SpringBaseTest;
import com.hb0730.admin.upms.server.system.project.application.model.dto.ApplicationDTO;
import com.hb0730.admin.upms.server.system.project.application.service.IApplicationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationServiceImplTest extends SpringBaseTest {
    @Autowired
    IApplicationService service;

    @Test
    public void saveTest() {
        ApplicationDTO application = new ApplicationDTO();
        application.setAppid("test1");
        application.setName("测试应用");
        service.save(application);
    }
}
