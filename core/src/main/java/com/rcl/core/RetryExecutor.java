package com.rcl.core;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class RetryExecutor {
    private final Retry retry;

    public RetryExecutor(String name, int maxAttempts, Duration waitDuration) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(waitDuration)
                .retryExceptions(IOException.class, TimeoutException.class)
                .build();
        this.retry = Retry.of(name, config);
    }

    public <T> T execute(Supplier<T> supplier) {
        return Retry.decorateSupplier(retry, supplier).get();
    }
}
