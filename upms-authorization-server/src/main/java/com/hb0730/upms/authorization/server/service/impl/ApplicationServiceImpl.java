package com.hb0730.upms.authorization.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.upms.authorization.server.entity.Application;
import com.hb0730.upms.authorization.server.mapper.IApplicationMapper;
import com.hb0730.upms.authorization.server.service.IApplicationService;
import org.springframework.stereotype.Service;

/**
 * 应用信息(项目)
 *
 * @author bing_huang
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<IApplicationMapper, Application> implements IApplicationService {
    @Override
    public Application findApplicationById(String appid) {
        return super.getById(appid);
    }
}
