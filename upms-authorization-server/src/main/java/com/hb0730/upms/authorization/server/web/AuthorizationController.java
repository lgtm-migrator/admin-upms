package com.hb0730.upms.authorization.server.web;

import com.hb0730.upms.authorization.server.entity.Application;
import com.hb0730.upms.authorization.server.entity.ScopeDetails;
import com.hb0730.upms.authorization.server.service.IApplicationService;
import com.hb0730.upms.authorization.server.service.IScopeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权码页面
 *
 * @author bing_huang
 */
@Controller
@SessionAttributes("authorizationRequest")
@RequiredArgsConstructor
public class AuthorizationController {
    // @see WhitelabelApprovalEndpoint
    // @see AuthorizationEndpoint

    private final IApplicationService applicationService;
    private final IScopeDetailsService scopeDetailsService;

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();
        Application application = applicationService.findApplicationById(clientId);
        Set<String> scope = authorizationRequest.getScope();
        List<ScopeDetails> scopeDetails = scopeDetailsService.findScopeByIds(scope);
        ModelAndView view = new ModelAndView();
        view.setViewName("authorization_confirm");
        view.addObject("authorizationRequest", authorizationRequest);
        view.addObject("adminApplication", application);
        view.addObject("adminScopes", scopeDetails);
        return view;
    }
}
