package com.rcl.core.auth;

import java.util.Map;

public interface AuthProvider {
    Map<String, String> getAuthHeaders();
}