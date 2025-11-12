package com.rcl.okhttp.service;

import com.rcl.core.RestClient;
import com.rcl.core.RetryExecutor;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.core.model.error.RclException;
import com.rcl.core.util.CoreConstants;
import com.rcl.core.util.Utils;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class RestClientImpl implements RestClient {
    private final OkHttpClient client = new OkHttpClient();
    private final RetryExecutor retryExecutor = new RetryExecutor("okhttp", 3, Duration.ofMillis(500));
    @Setter
    @Getter
    private AuthProvider authProvider;

    @Override
    public HttpResponse get(HttpRequest request) {
        String url = request.getUrl();
        Utils.requireNonBlank(url, CoreConstants.URL);

        System.out.println("URL: " + url);

        Map<String, String> headers = request.getHeaders();
        System.out.println("Request Headers: " + headers);

        Request.Builder builder = new Request.Builder().url(url);

        if(MapUtils.isNotEmpty(headers)) {
            headers.forEach(builder::addHeader);
        }

        if(null != authProvider) {
            authProvider.getAuthHeaders().forEach(builder::addHeader);
        }

        Request req = builder.get().build();

        return retryExecutor.execute(() -> {
            try (Response response = client.newCall(req).execute()) {
                return HttpResponse.builder()
                        .statusCode(response.code())
                        .body(response.body().string())
                        .multiMapHeaders(response.headers().toMultimap())
                        .build();
            } catch (IOException e) {
                throw new RclException(e);
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
