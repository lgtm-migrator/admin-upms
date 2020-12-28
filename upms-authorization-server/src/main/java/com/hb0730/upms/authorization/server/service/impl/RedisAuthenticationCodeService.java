package com.hb0730.upms.authorization.server.service.impl;

import com.hb0730.admin.upms.comons.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 授权码保存到Redis，以确保认证服务器集群的一致性
 *
 * @author bing_huang
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RedisAuthenticationCodeService extends RandomValueAuthorizationCodeServices {
    private static final String AUTH_CODE_KEY = "spring:oauth:auth_code";
    private final RedisService redisService;

    @Override
    protected OAuth2Authentication remove(String code) {
        String hget = (String) redisService.hget(AUTH_CODE_KEY, code);
        if (StringUtils.isBlank(hget)) {
            return null;
        }
        OAuth2Authentication authentication = SerializationUtils.deserialize(hget.getBytes());
        if (null != authentication) {
            redisService.hdel(AUTH_CODE_KEY, code);
        }
        return authentication;
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        long seconds = TimeUnit.MINUTES.toSeconds(10);
        redisService.hset(AUTH_CODE_KEY, code, SerializationUtils.serialize(authentication), seconds);
        log.info("保存authentication code: {}至redis", code);
    }

}
