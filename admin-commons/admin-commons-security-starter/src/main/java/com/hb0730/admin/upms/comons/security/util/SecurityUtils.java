package com.hb0730.admin.upms.comons.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.LinkedHashMap;

/**
 * @author bing_huang
 */
public class SecurityUtils {


    @SuppressWarnings({"unchecked"})
    private static LinkedHashMap<String, Object> getAuthenticationDetails() {
        return (LinkedHashMap<String, Object>) getAuthentication().getDetails();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
