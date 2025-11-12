package com.rcl.webclient.service;

import com.rcl.core.RestClient;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class RestClientImpl implements RestClient {
    private final WebClient webClient;
    private final Retry retry;
    @Setter
    @Getter
    private AuthProvider authProvider;

    public RestClientImpl() {
        this.webClient = WebClient.builder()
                .build();

        this.retry = Retry.of("webclient", RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                // fixme
//                .retryOnResult(response -> response.statusCode().is5xxServerError())
                .build());
    }

    public RestClientImpl(AuthProvider authProvider) {
        this.webClient = WebClient.builder()
                .defaultHeaders(headers -> headers.setAll(authProvider.getAuthHeaders()))
                .build();

        this.retry = Retry.of("webclient", RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                // fixme
//                .retryOnResult(response -> response.statusCode().is5xxServerError())
//                .retryOnResult(httpResponse -> httpResponse.getStatusCode() >= 500)
                .retryExceptions(IOException.class, TimeoutException.class)
                .build());
    }

    @Override
    public HttpResponse get(HttpRequest request) {
        Mono<HttpResponse> mono = webClient.get()
                .uri(request.getUrl())
                .headers(h -> h.setAll(MapUtils.emptyIfNull(request.getHeaders())))
                .retrieve()
                .toEntity(String.class)
                .map(response ->
                        HttpResponse.builder()
                                .statusCode(response.getStatusCode().value())
                                .body(response.getBody())
                                .headers(response.getHeaders().toSingleValueMap())
                                .build()
                );

        return mono.transformDeferred(RetryOperator.of(retry)).block();
    }

    @Override
    public HttpResponse post(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse put(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse delete(HttpRequest request) {
        return null;
    }

    // Implement post, put, delete similarly
}
