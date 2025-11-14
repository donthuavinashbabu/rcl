package com.rcl.retrofit.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.util.RestClientType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestClientFactoryImpl implements RestClientFactory {
    private static final RestClientFactoryImpl INSTANCE = new RestClientFactoryImpl();
    public static final String DUMMY_URL = "http://localhost/";

    public static RestClientFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        // Base URL will be overridden by @Url
        return new RestClientImpl(DUMMY_URL);
    }

    @Override
    public RestClient getClient(RestClientType restClientType) {
        return new RestClientImpl(DUMMY_URL);
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        // Base URL will be overridden by @Url
        RestClientImpl retrofitClient = new RestClientImpl(DUMMY_URL);
        retrofitClient.setAuthProvider(authProvider);
        return retrofitClient;
    }

    @Override
    public RestClientType getType() {
        return RestClientType.RETROFIT;
    }

}
