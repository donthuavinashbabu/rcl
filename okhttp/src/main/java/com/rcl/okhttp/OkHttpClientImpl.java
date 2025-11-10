package com.rcl.okhttp;

import com.rcl.core.RestClient;
import com.rcl.core.RetryExecutor;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.Duration;

public class OkHttpClientImpl implements RestClient {
    private final OkHttpClient client = new OkHttpClient();
    private final RetryExecutor retryExecutor = new RetryExecutor("okhttp", 3, Duration.ofMillis(500));

    @Override
    public HttpResponse get(HttpRequest request) {
        Request.Builder builder = new Request.Builder().url(request.getUrl());

        request.getHeaders().forEach(builder::addHeader);
        Request req = builder.get().build();

        return retryExecutor.execute(() -> {
            try (Response response = client.newCall(req).execute()) {
                return HttpResponse.builder()
                        .statusCode(response.code())
                        .body(response.body().string())
                        .multiMapHeaders(response.headers().toMultimap())
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
}
