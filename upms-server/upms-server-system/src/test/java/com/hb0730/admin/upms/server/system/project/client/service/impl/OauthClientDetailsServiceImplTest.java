package com.hb0730.admin.upms.server.system.project.client.service.impl;

import com.hb0730.admin.upms.server.system.SpringBaseTest;
import com.hb0730.admin.upms.server.system.project.client.model.dto.OauthClientDetailsDTO;
import com.hb0730.admin.upms.server.system.project.client.service.IOauthClientDetailsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OauthClientDetailsServiceImplTest extends SpringBaseTest {
    @Autowired
    private IOauthClientDetailsService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveTest() {
        OauthClientDetailsDTO clientDetails = new OauthClientDetailsDTO();
        clientDetails.setClientId("upms-server");
        clientDetails.setClientSecret("upms123456");
        clientDetails.setAuthorizedGrantTypes("password,refresh_token,authorization_code");
        clientDetails.setWebServerRedirectUri("https://localhost:9000/oauth/callback");
        clientDetails.setAuthorities("ROLE_ALL");
        clientDetails.setScope("read,write");
        clientDetails.setAccessTokenValidity(Integer.MAX_VALUE);
        clientDetails.setRefreshTokenValidity(Integer.MAX_VALUE);
        service.save(clientDetails);
    }

    @Test
    public void updateByIdTest() {
        OauthClientDetailsDTO clientDetails = new OauthClientDetailsDTO();
        clientDetails.setClientId("upms-server");
        clientDetails.setClientSecret("upms123456");
        clientDetails.setAuthorizedGrantTypes("password,refresh_token,authorization_code");
        clientDetails.setWebServerRedirectUri("http://localhost:9000/oauth/callback");
        clientDetails.setAuthorities("ROLE_ALL");
        clientDetails.setScope("read,write");
        clientDetails.setAccessTokenValidity(Integer.MAX_VALUE);
        clientDetails.setRefreshTokenValidity(Integer.MAX_VALUE);
        service.updateById(clientDetails);
    }

    @Test
    public void password() {
        String encode = passwordEncoder.encode("upms123456");
        System.out.println(encode);
    }
}
