package com.ozeeesoftware.forumrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    String getAuthUserName() {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            return null;
        }
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        String username = getAuthUserName();
        return Optional.of(username != null ? username : "anonymousUser");
    }

}
