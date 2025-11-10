package com.rcl.retrofit.service;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.auth.AuthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RetrofitRestClientFactory implements RestClientFactory {
    private static final RetrofitRestClientFactory INSTANCE = new RetrofitRestClientFactory();

    public static RetrofitRestClientFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public RestClient getClient() {
        // Base URL will be overridden by @Url
        return new RetrofitClientImpl("http://localhost/");
    }

    @Override
    public RestClient getClient(AuthProvider authProvider) {
        // Base URL will be overridden by @Url
        RetrofitClientImpl retrofitClient = new RetrofitClientImpl("http://localhost/");
        retrofitClient.setAuthProvider(authProvider);
        return retrofitClient;
    }

}
