package com.rcl.apachehttp.service;

import com.rcl.core.RestClient;
import com.rcl.core.RetryExecutor;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.core.util.CoreConstants;
import com.rcl.core.util.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RestClientImpl implements RestClient {
    private final CloseableHttpClient client = HttpClients.createDefault();
    private final RetryExecutor retryExecutor = new RetryExecutor("apache", 3, Duration.ofMillis(500));
    @Setter
    @Getter
    private AuthProvider authProvider;

    @Override
    public HttpResponse get(HttpRequest request) {
        String url = request.getUrl();
        Utils.requireNonBlank(url, CoreConstants.URL);
        log.info("URL: {}", url);

        Map<String, String> requestHeaders = request.getHeaders();

        HttpGet httpGet = new HttpGet(url);
        if(MapUtils.isNotEmpty(requestHeaders)) {
            requestHeaders.forEach(httpGet::addHeader);
        }

        if(null != authProvider) {
            authProvider.getAuthHeaders().forEach(httpGet::addHeader);
        }

        return retryExecutor.execute(() -> {
            try {
                HttpClientResponseHandler<HttpResponse> handler = response -> {
                    int status = response.getCode();
                    String body = response.getEntity() != null ? EntityUtils.toString(response.getEntity()) : "";
                    Map<String, String> headers = Arrays.stream(response.getHeaders())
                            .collect(Collectors.toMap(Header::getName, Header::getValue));
                    return HttpResponse.builder()
                            .statusCode(status)
                            .body(body)
                            .headers(headers)
                            .build();
                };

                return client.execute(httpGet, handler);
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