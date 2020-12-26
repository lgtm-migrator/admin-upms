package com.hb0730.upms.authorization.server.service.impl;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author bing_huang
 */
@Service
public class RedisClientDetailsServiceImpl extends JdbcClientDetailsService {

    public RedisClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }
}
