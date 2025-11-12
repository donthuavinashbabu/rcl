package com.rcl.retrofit.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestClientFactoryImpl implements RestClientFactory {
    private static final RestClientFactoryImpl INSTANCE = new RestClientFactoryImpl();

    public static RestClientFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        // Base URL will be overridden by @Url
        return new RestClientImpl("http://localhost/");
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        // Base URL will be overridden by @Url
        RestClientImpl retrofitClient = new RestClientImpl("http://localhost/");
        retrofitClient.setAuthProvider(authProvider);
        return retrofitClient;
    }

}
