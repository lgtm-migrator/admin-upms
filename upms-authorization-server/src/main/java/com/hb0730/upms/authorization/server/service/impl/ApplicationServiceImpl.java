package com.hb0730.upms.authorization.server.service.impl;

import com.hb0730.upms.authorization.server.entity.Application;
import com.hb0730.upms.authorization.server.service.IApplicationService;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 */
@Service
public class ApplicationServiceImpl implements IApplicationService {
    @Override
    public Application findApplicationById(String appid) {
        Application application = new Application();
        if ("test1".equals(appid)) {
            application.setAppid(appid);
            application.setName("测试应用");
            return application;
        }
        return null;
    }
}
