package com.hb0730.admin.upms.security.endpoint;

import com.hb0730.admin.upms.security.properties.UpmsSecurityStarterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当前客户端 信息
 *
 * @author bing_huang
 */
@RequiredArgsConstructor
@RestController
public class ClientEndpoint {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UpmsSecurityStarterProperties properties;

    @RequestMapping("/oauth/current/client")
    @ResponseBody
    public ClientRegistration getClientInfo() {
        return clientRegistrationRepository.findByRegistrationId(properties.getClientId());
    }
}
