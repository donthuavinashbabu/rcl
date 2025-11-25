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
        if (restClient == null) {
            restClient = new RestClientImpl();
        }
        return restClient;
    }

    @Override
    public RestClient getClient(RestClientType restClientType) {
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

    @Override
    public RestClientFactory enableInterceptor() {
        if(restClient == null){
            getClient();
        }
        restClient.enableInterceptor();
        return this;
    }

    @Override
    public RestClientFactory disableSsl() {
        if(restClient == null){
            getClient();
        }
        restClient.disableSsl();
        return this;
    }

    @Override
    public RestClientFactory configureTimeouts(int connectTimeoutMillis, int readTimeoutMillis) {
        if(restClient == null){
            getClient();
        }
        restClient.configureTimeouts(connectTimeoutMillis, readTimeoutMillis);
        return this;
    }
}