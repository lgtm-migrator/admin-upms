package com.hb0730.upms.authorization.server.service.impl;

import com.hb0730.admin.upms.commons.enums.ActionEnum;
import com.hb0730.admin.upms.comons.redis.service.RedisService;
import com.hb0730.commons.json.exceptions.JsonException;
import com.hb0730.commons.json.utils.Jsons;
import com.hb0730.commons.lang.StringUtils;
import com.hb0730.commons.lang.collection.CollectionUtils;
import com.hb0730.upms.authorization.server.event.ClientEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * redis 存储的客户端信息
 *
 * @author bing_huang
 */
@Service
@Slf4j
public class RedisClientDetailsServiceImpl extends JdbcClientDetailsService implements ApplicationListener<ClientEvent> {
    /**
     * 缓存 client的 redis key，这里是 hash结构存储
     */
    private static final String CACHE_CLIENT_KEY = "upm:oauth:client_details";
    private final RedisService redisService;

    public RedisClientDetailsServiceImpl(@Qualifier("dataSource") DataSource dataSource, RedisService redisService) {
        super(dataSource);
        this.redisService = redisService;
    }

    @SneakyThrows
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;
        String value = (String) redisService.hget(CACHE_CLIENT_KEY, clientId);
        if (StringUtils.isBlank(value)) {
            clientDetails = cacheAndGetClient(clientId);
        } else {
            clientDetails = Jsons.JSONS.jsonToObject(value, BaseClientDetails.class);
        }
        return clientDetails;
    }

    /**
     * 缓存 client并返回 client
     *
     * @param clientId clientId
     */
    public ClientDetails cacheAndGetClient(String clientId) throws JsonException {
        ClientDetails clientDetails = null;
        clientDetails = super.loadClientByClientId(clientId);
        if (clientDetails != null) {
            BaseClientDetails baseClientDetails = (BaseClientDetails) clientDetails;
            Set<String> autoApproveScopes = baseClientDetails.getAutoApproveScopes();
            if (CollectionUtils.isNotEmpty(autoApproveScopes)) {
                baseClientDetails.setAutoApproveScopes(
                        autoApproveScopes.stream().map(this::convert).collect(Collectors.toSet())
                );
            }
            redisService.hset(CACHE_CLIENT_KEY, clientId, Jsons.JSONS.objectToJson(baseClientDetails));
        }
        return clientDetails;
    }

    /**
     * 删除 redis缓存
     *
     * @param clientId clientId
     */
    public void removeRedisCache(String clientId) {
        redisService.hdel(CACHE_CLIENT_KEY, clientId);
    }

    /**
     * 将 oauth_client_details全表刷入 redis
     */
    public void loadAllClientToCache() {
        if (redisService.hasKey(CACHE_CLIENT_KEY)) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");

        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.forEach(client -> {
            try {
                redisService.hset(CACHE_CLIENT_KEY, client.getClientId(), Jsons.JSONS.objectToJson(client));
            } catch (JsonException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onApplicationEvent(@Nullable ClientEvent clientEvent) {
        assert clientEvent != null;
        ActionEnum action = clientEvent.getAction();
        String clientIds = clientEvent.getClientId();
        String[] clientIdArray = org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(clientIds, ",");
        switch (action) {
            case ADD_NEW:
            case UPDATE:
                for (String clientId : clientIdArray) {
                    loadClientByClientId(clientId);
                }
                break;
            case DELETE:
                for (String clientId : clientIdArray) {
                    removeRedisCache(clientId);
                }
                break;
            default:
        }
    }

    private String convert(String value) {
        final String logicTrue = "1";
        return logicTrue.equals(value) ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
