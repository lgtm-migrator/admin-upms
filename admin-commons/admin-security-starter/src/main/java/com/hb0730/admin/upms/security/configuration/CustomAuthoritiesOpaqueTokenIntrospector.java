package com.hb0730.admin.upms.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 解决调用权限问题
 *
 * @author bing_huang
 */
@Component
public class CustomAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final OpaqueTokenIntrospector delegate;

    public CustomAuthoritiesOpaqueTokenIntrospector(
            @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}") String introspectionUri,
            @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}") String clientId,
            @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}") String clientSecret) {
        delegate = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
        String name = Objects.requireNonNull(principal.getAttribute("user_name")).toString();
        return new DefaultOAuth2AuthenticatedPrincipal(name, principal.getAttributes(), extractAuthorities(principal));
    }

    private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
        List<String> scopes = principal.getAttribute(OAuth2IntrospectionClaimNames.SCOPE);
        assert scopes != null;
        List<GrantedAuthority> authorityList = scopes.stream()
                .map(scope -> "SCOPE_" + scope)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        List<String> authorities = principal.getAttribute("authorities");
        assert authorities != null;
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        authorityList.addAll(grantedAuthorities);
        return authorityList;
    }
}
