package com.hb0730.upms.authorization.server.service;

import com.hb0730.upms.authorization.server.entity.OauthClientDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(value = SpringJUnit4ClassRunner.class)
public class IOauthClientDetailsServiceTest {
    @Autowired
    private IOauthClientDetailsService oauthClientDetailsServiceImpl;

    @Test
    public void createOauthClientDetails() {
        OauthClientDetails details = new OauthClientDetails();
        details.setClientId("test1");
        details.setClientSecret("secret");
        details.setAuthorizedGrantTypes("password,refresh_token,authorization_code");
        details.setWebServerRedirectUri("http://blod.hb0730.com");
        details.setAuthorities("ROLE_ALL");
        details.setScope("read,write");
        details.setAccessTokenValidity(Integer.MAX_VALUE);
        details.setRefreshTokenValidity(Integer.MAX_VALUE);
        oauthClientDetailsServiceImpl.createOauthClientDetails(details);
    }

    @Test
    public void createOauthClientDetails2() {
        OauthClientDetails details = new OauthClientDetails();
        details.setClientId("test1");
        details.setClientSecret("secret");
        details.setAuthorizedGrantTypes("password,refresh_token,authorization_code");
        details.setWebServerRedirectUri("http://blod.hb0730.com");
        details.setAuthorities("ROLE_ALL");
        details.setScope("read,write");
        details.setAutoapprove(Byte.valueOf("true"));
        details.setAccessTokenValidity(Integer.MAX_VALUE);
        details.setRefreshTokenValidity(Integer.MAX_VALUE);
        oauthClientDetailsServiceImpl.createOauthClientDetails(details);
    }

    @Test
    public void updateOauthClientDetails() {
        OauthClientDetails details = new OauthClientDetails();
        details.setClientId("test1");
        details.setClientSecret("secret");
        details.setAuthorizedGrantTypes("password,refresh_token,authorization_code");
        details.setWebServerRedirectUri("http://blog.hb0730.com");
        details.setAuthorities("ROLE_ALL");
        details.setScope("read,write");
        details.setAccessTokenValidity(Integer.MAX_VALUE);
        details.setRefreshTokenValidity(Integer.MAX_VALUE);
        oauthClientDetailsServiceImpl.updateOauthClientDetails(details);
    }

}
