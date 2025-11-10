package com.rcl.webclient.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebClientFactory implements RestClientFactory {
    private static final WebClientFactory INSTANCE = new WebClientFactory();

    public static WebClientFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        return new WebClientImpl();
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        return new WebClientImpl(authProvider);
    }

}
