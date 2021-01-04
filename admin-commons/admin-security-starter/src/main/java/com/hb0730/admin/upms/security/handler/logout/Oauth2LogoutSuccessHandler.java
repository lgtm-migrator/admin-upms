package com.hb0730.admin.upms.security.handler.logout;

import com.hb0730.admin.upms.commons.entity.constant.Oauth2Constant;
import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import lombok.Getter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @author bing_huang
 */
public class Oauth2LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
    @Getter
    private String postLogoutRedirectUri;
    private RestOperations restOperations;
    private Converter<String, RequestEntity<?>> requestEntityConverter;

    public Oauth2LogoutSuccessHandler(UpmsSecurityStarterProperties properties) {
        Assert.notNull(properties, "upmsSecurityStarterProperties cannot be null");
        this.restOperations = new RestTemplate();
        URI endpoint = this.endSessionEndpoint(properties);
        this.requestEntityConverter = this.defaultRequestEntityConverter(endpoint);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String token = idToken(request);
        RequestEntity<?> requestEntity = this.requestEntityConverter.convert(token);
        if (requestEntity == null) {
            return super.determineTargetUrl(request, response);
        } else {
            makeRequest(requestEntity);
            return null == getPostLogoutRedirectUri() ? super.determineTargetUrl(request, response) : getPostLogoutRedirectUri();
        }

    }

    protected URI endSessionEndpoint(UpmsSecurityStarterProperties properties) {
        if (properties != null) {
            String endpoint = properties.getLogoutEndpoint();
            if (endpoint != null) {
                return URI.create(endpoint);
            }
        }
        return null;
    }

    protected String idToken(Authentication authentication) {
        return ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
    }

    protected String idToken(HttpServletRequest request) {
        return this.bearerTokenResolver.resolve(request);
    }


    private Converter<String, RequestEntity<?>> defaultRequestEntityConverter(URI introspectionUri) {
        return (token) -> {
            HttpHeaders headers = requestHeaders(token);
            MultiValueMap<String, String> body = requestBody(token);
            return new RequestEntity<>(body, headers, HttpMethod.POST, introspectionUri);
        };
    }

    private HttpHeaders requestHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, Oauth2Constant.OAUTH2_TOKEN_TYPE_BEARER + token);
        return headers;
    }

    private MultiValueMap<String, String> requestBody(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("access_token", token);
        return body;
    }

    private ResponseEntity<String> makeRequest(RequestEntity<?> requestEntity) {
        try {
            return this.restOperations.exchange(requestEntity, String.class);
        } catch (Exception ex) {
            throw new OAuth2IntrospectionException(ex.getMessage(), ex);
        }
    }

    public Oauth2LogoutSuccessHandler setBearerTokenResolver(BearerTokenResolver bearerTokenResolver) {
        Assert.notNull(bearerTokenResolver, "bearerTokenResolver cannot be null");
        this.bearerTokenResolver = bearerTokenResolver;
        return this;
    }

    public Oauth2LogoutSuccessHandler setRequestEntityConverter(Converter<String, RequestEntity<?>> requestEntityConverter) {
        Assert.notNull(requestEntityConverter, "requestEntityConverter cannot be null");
        this.requestEntityConverter = requestEntityConverter;
        return this;
    }

    public Oauth2LogoutSuccessHandler setPostLogoutRedirectUri(String postLogoutRedirectUri) {
        Assert.hasText(postLogoutRedirectUri, "postLogoutRedirectUri cannot be null");
        this.postLogoutRedirectUri = postLogoutRedirectUri;
        return this;
    }

    public Oauth2LogoutSuccessHandler setRestOperations(RestOperations restOperations) {
        Assert.hasText(postLogoutRedirectUri, "restOperations cannot be null");
        this.restOperations = restOperations;
        return this;
    }
}
