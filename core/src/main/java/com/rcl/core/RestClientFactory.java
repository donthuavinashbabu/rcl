package com.rcl.core;

import com.rcl.core.auth.AuthProvider;

public interface RestClientFactory {
    RestClient getClient();
    RestClient getClient(AuthProvider authProvider);
}