package com.rcl.okhttp.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OkHttpRestClientFactory implements RestClientFactory {
    private static final OkHttpRestClientFactory INSTANCE = new OkHttpRestClientFactory();

    public static OkHttpRestClientFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        return new OkHttpClientImpl();
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        OkHttpClientImpl okHttpClient = new OkHttpClientImpl();
        okHttpClient.setAuthProvider(authProvider);
        return okHttpClient;
    }

}
