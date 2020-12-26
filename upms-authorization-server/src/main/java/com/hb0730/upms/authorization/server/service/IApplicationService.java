package com.hb0730.upms.authorization.server.service;

import com.hb0730.upms.authorization.server.entity.Application;
import org.springframework.lang.Nullable;

/**
 * 应用
 *
 * @author bing_huang
 */
public interface IApplicationService {

    /**
     * 根据id查询详情
     *
     * @param appid appid
     * @return 详情页
     */
    @Nullable
    Application findApplicationById(String appid);
}
