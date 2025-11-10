package com.rcl.apachehttp.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApacheHttpRestClientFactory implements RestClientFactory {
    private static final ApacheHttpRestClientFactory INSTANCE = new ApacheHttpRestClientFactory();

    public static ApacheHttpRestClientFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        return new ApacheHttpClientImpl();
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        ApacheHttpClientImpl apacheHttpClient = new ApacheHttpClientImpl();
        apacheHttpClient.setAuthProvider(authProvider);
        return apacheHttpClient;
    }

}
