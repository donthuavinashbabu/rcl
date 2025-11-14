package com.rcl.okhttp.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.util.RestClientType;
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
        return new RestClientImpl();
    }

    @Override
    public RestClient getClient(RestClientType restClientType) {
        return new RestClientImpl();
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        RestClientImpl okHttpClient = new RestClientImpl();
        okHttpClient.setAuthProvider(authProvider);
        return okHttpClient;
    }

    @Override
    public RestClientType getType() {
        return RestClientType.OKHTTP;
    }

}
