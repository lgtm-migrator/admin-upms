package com.hb0730.admin.upms.security.service.client;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

/**
 * redis存储
 *
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
public class RedisOauth2AuthorizedClientServiceImpl implements OAuth2AuthorizedClientService {
    private static final String CACHE_CLIENT_AUTHORIZED = "spring:oauth:client:authorized";
    private final RedisConnectionFactory connectionFactory;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration == null) {
            return null;
        }
        try (RedisConnection connection = getConnection()) {
            String key = getKey(clientRegistrationId);
            byte[] bytes = connection.hGet(key.getBytes(StandardCharsets.UTF_8), principalName.getBytes(StandardCharsets.UTF_8));
            if (null == bytes || bytes.length == 0) {
                return null;
            }
            return SerializationUtils.deserialize(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        String registrationId = authorizedClient.getClientRegistration().getRegistrationId();

        try (RedisConnection connection = getConnection()) {
            String key = getKey(registrationId);
            byte[] serialize = SerializationUtils.serialize(authorizedClient);
            connection.hSet(
                    key.getBytes(StandardCharsets.UTF_8),
                    principal.getName().getBytes(StandardCharsets.UTF_8),
                    serialize
            );
        }
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (null == registration) {
            return;
        }
        try (RedisConnection connection = getConnection()) {
            String key = getKey(clientRegistrationId);
            connection.hDel(key.getBytes(StandardCharsets.UTF_8), principalName.getBytes(StandardCharsets.UTF_8));
        }
    }


    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    private String getKey(String clientRegistrationId) {
        return CACHE_CLIENT_AUTHORIZED + ":" + clientRegistrationId;
    }

}
