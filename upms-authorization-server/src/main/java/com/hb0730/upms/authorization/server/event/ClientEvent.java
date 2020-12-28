package com.hb0730.upms.authorization.server.event;

import com.hb0730.admin.upms.commons.enums.ActionEnum;
import org.springframework.context.ApplicationEvent;

/**
 * @author bing_huang
 */
public class ClientEvent extends ApplicationEvent {
    private final String clientId;
    private final ActionEnum action;

    public ClientEvent(Object source, String clientId, ActionEnum action) {
        super(source);
        this.clientId = clientId;
        this.action = action;
    }

    public String getClientId() {
        return clientId;
    }

    public ActionEnum getAction() {
        return action;
    }
}
