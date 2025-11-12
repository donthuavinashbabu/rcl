package com.rcl.resttemplate.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.util.RestClientType;

public class RestClientFactoryImpl implements RestClientFactory {
    private static final RestClientFactoryImpl INSTANCE = new RestClientFactoryImpl();

    public static RestClientFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        return new RestClientImpl();
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        RestClientImpl restClient = new RestClientImpl();
        restClient.setAuthProvider(authProvider);
        return restClient;
    }

    @Override
    public RestClientType getType() {
        return RestClientType.REST_TEMPLATE;
    }
}
