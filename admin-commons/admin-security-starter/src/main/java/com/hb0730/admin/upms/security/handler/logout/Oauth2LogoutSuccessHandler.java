package com.hb0730.admin.upms.security.handler.logout;

import com.hb0730.admin.upms.commons.entity.constant.SystemConstant;
import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @author bing_huang
 */
public class Oauth2LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Getter
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UpmsSecurityStarterProperties properties;
    private BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
    @Getter
    private String postLogoutRedirectUri;
    @Setter
    private RestOperations restOperations;
    @Setter
    private Converter<String, RequestEntity<?>> requestEntityConverter;

    public Oauth2LogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository, UpmsSecurityStarterProperties properties) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        Assert.notNull(properties, "upmsSecurityStarterProperties cannot be null");
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.properties = properties;
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

            ResponseEntity<String> responseEntity = makeRequest(requestEntity);
            return properties.getLogoutRedirectUri();
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

    protected String endpointUri(URI endSessionEndpoint, String idToken, URI postLogoutRedirectUri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(endSessionEndpoint);
        builder.queryParam("access_token", idToken);
        if (postLogoutRedirectUri != null) {
            builder.queryParam("post_logout_redirect_uri", postLogoutRedirectUri);
        }
        // @formatter:off
        return builder.encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
        // @formatter:on
    }

    protected URI postLogoutRedirectUri(HttpServletRequest request) {
        if (this.postLogoutRedirectUri == null) {
            return null;
        }
        // @formatter:off
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .build();
        return UriComponentsBuilder.fromUriString(this.postLogoutRedirectUri)
                .buildAndExpand(Collections.singletonMap("baseUrl", uriComponents.toUriString()))
                .toUri();
        // @formatter:on
    }

    public void setPostLogoutRedirectUri(String postLogoutRedirectUri) {
        Assert.hasText(postLogoutRedirectUri, "postLogoutRedirectUri cannot be null");
        this.postLogoutRedirectUri = postLogoutRedirectUri;
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
        headers.set(HttpHeaders.AUTHORIZATION, SystemConstant.OAUTH2_TOKEN_TYPE + token);
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

    public void setBearerTokenResolver(BearerTokenResolver bearerTokenResolver) {
        this.bearerTokenResolver = bearerTokenResolver;
    }
}
