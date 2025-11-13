package com.rcl.resttemplate.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.util.RestClientType;

public class RestClientFactoryImpl implements RestClientFactory {
    private static final RestClientFactoryImpl INSTANCE = new RestClientFactoryImpl();
    private RestClientImpl restClient;
    public static RestClientFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        restClient = new RestClientImpl();
        return restClient;
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

    public void enableInterceptor() {
        restClient.enableInterceptor();
    }

    public void disableSsl() {
        restClient.disableSsl();
    }

}