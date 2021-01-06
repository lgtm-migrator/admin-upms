package com.hb0730.admin.upms.security.handler.refresh;

import com.hb0730.admin.upms.commons.entity.constant.Oauth2Constant;
import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author bing_huang
 */
public class Oauth2RefreshHandler {
    @Setter
    private RestOperations restOperations;
    @Setter
    private Converter<UpmsSecurityStarterProperties, RequestEntity<?>> requestEntityConverter;
    @Getter
    private final UpmsSecurityStarterProperties properties;

    public Oauth2RefreshHandler(UpmsSecurityStarterProperties properties) {
        Assert.notNull(properties, "upmsSecurityStarterProperties cannot be null");
        this.properties = properties;
        this.restOperations = new RestTemplate();
    }

    public ResponseEntity<String> request(String refreshToken) {
        Assert.hasText(refreshToken, "refresh_token cannot be null");
        if (this.requestEntityConverter == null) {
            URI endpoint = requestEndpoint(properties);
            this.requestEntityConverter = this.defaultRequestEntityConverter(refreshToken, endpoint);
        }
        RequestEntity<?> requestEntity = this.requestEntityConverter.convert(properties);
        return makeRequest(requestEntity);

    }

    private ResponseEntity<String> makeRequest(RequestEntity<?> requestEntity) {
        try {
            return this.restOperations.exchange(requestEntity, String.class);
        } catch (Exception ex) {
            throw new OAuth2IntrospectionException(ex.getMessage(), ex);
        }
    }

    protected URI requestEndpoint(UpmsSecurityStarterProperties properties) {
        if (properties != null) {
            String endpoint = properties.getLoginOauth2Endpoint();
            if (endpoint != null) {
                return URI.create(endpoint);
            }
        }
        return null;
    }


    private Converter<UpmsSecurityStarterProperties, RequestEntity<?>> defaultRequestEntityConverter(String refreshToken, URI introspectionUri) {
        return (properties) -> {
            HttpHeaders headers = requestHeaders(properties);
            MultiValueMap<String, String> body = requestBody(refreshToken);
            return new RequestEntity<>(body, headers, HttpMethod.POST, introspectionUri);
        };
    }


    private HttpHeaders requestHeaders(UpmsSecurityStarterProperties properties) {
        HttpHeaders headers = new HttpHeaders();
        String basic = Base64Utils.encodeToString((properties.getClientId() + ":" + properties.getClientSecret()).getBytes(StandardCharsets.UTF_8));
        headers.set(HttpHeaders.AUTHORIZATION, Oauth2Constant.OAUTH2_TOKEN_TYPE_BASIC + basic);
        return headers;
    }

    private MultiValueMap<String, String> requestBody(String refreshToken) {
        Assert.hasText(refreshToken, "refresh_token cannot be null");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);
        return body;
    }
}
