package com.rcl.core.auth;

import com.rcl.core.util.CoreConstants;

import java.util.Map;

public class JwtAuthProvider implements AuthProvider {
    private final String token;

    public JwtAuthProvider(String token) {
        this.token = token;
    }

    @Override
    public Map<String, String> getAuthHeaders() {
        return Map.of(CoreConstants.AUTHORIZATION, CoreConstants.BEARER + token);
    }

}