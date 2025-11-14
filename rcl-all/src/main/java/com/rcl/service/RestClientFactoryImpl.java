package com.rcl.service;

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
        throw new RuntimeException("Provide RestClientType");
    }

    @Override
    public RestClient getClient(RestClientType restClientType) {
        return switch (restClientType) {
            case APACHE_HTTP_CLIENT -> com.rcl.apachehttp.service.RestClientFactoryImpl.getInstance().getClient();
            case OKHTTP -> com.rcl.okhttp.service.RestClientFactoryImpl.getInstance().getClient();
            case RETROFIT -> com.rcl.retrofit.service.RestClientFactoryImpl.getInstance().getClient();
            case WEBCLIENT -> com.rcl.webclient.service.RestClientFactoryImpl.getInstance().getClient();
            case REST_TEMPLATE -> com.rcl.resttemplate.service.RestClientFactoryImpl.getInstance().getClient();
            default -> throw new RuntimeException("Unsupported RestClientType");
        };
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        return null;
    }

    @Override
    public RestClientType getType() {
        return null;
    }
}
