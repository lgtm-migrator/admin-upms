package com.hb0730.upms.authorization.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisRememberMeTokenRepositoryImpl implements PersistentTokenRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private static final Integer TOKEN_VALID_DAYS = 30;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        log.debug("token create seriesId: [{}]", token.getSeries());
        String key = generateKey(token.getSeries());
        HashMap<String, String> map = new HashMap<>(3);
        map.put("username", token.getUsername());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", String.valueOf(token.getDate().getTime()));
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = generateKey(series);
        HashMap<String, String> map = new HashMap<>(2);
        map.put("tokenValue", tokenValue);
        map.put("date", String.valueOf(lastUsed.getTime()));
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = generateKey(seriesId);
        List<Object> hashKeys = new ArrayList<>();
        hashKeys.add("username");
        hashKeys.add("tokenValue");
        hashKeys.add("date");
        List<Object> hashValues = stringRedisTemplate.opsForHash().multiGet(key, hashKeys);
        try {

            String username = hashValues.get(0).toString();
            String tokenValue = hashValues.get(1).toString();
            String date = hashValues.get(2).toString();
            if (null == username || null == tokenValue || null == date) {
                return null;
            }
            long timestamp = Long.parseLong(date);
            Date time = new Date(timestamp);
            return new PersistentRememberMeToken(username, seriesId, tokenValue, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        log.debug("token remove username: [{}]", username);
        byte[] hashKey = ((StringRedisSerializer) stringRedisTemplate.getHashKeySerializer()).serialize("username");
        RedisConnection redisConnection = stringRedisTemplate.getConnectionFactory().getConnection();
        try (Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().match(generateKey("*")).count(1024).build())) {
            while (cursor.hasNext()) {
                byte[] key = cursor.next();
                byte[] hashValue = redisConnection.hGet(key, hashKey);
                String storeName = (String) stringRedisTemplate.getHashValueSerializer().deserialize(hashValue);
                if (username.equals(storeName)) {
                    redisConnection.expire(key, 0L);
                    return;
                }
            }
        } catch (IOException ex) {
            log.warn("token remove exception", ex);
        }
    }

    /**
     * 生成key
     *
     * @param series
     * @return
     */
    private String generateKey(String series) {
        return "spring:security:rememberMe:token:" + series;
    }
}
