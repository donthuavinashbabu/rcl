package com.rcl.resttemplate.service;

import com.rcl.core.RestClient;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

@Slf4j
public class RestClientImpl implements RestClient {
    private final RestTemplate restTemplate = new RestTemplate();
    @Setter
    @Getter
    private AuthProvider authProvider;

    public void enableInterceptor() {
        restTemplate.setInterceptors(List.of(new RestTemplateInterceptor()));
    }

    public void disableSsl() {
        try {
            // Trust all certificates
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

            // Build connection manager with custom SSL
            PoolingHttpClientConnectionManager connManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setSSLSocketFactory(sslSocketFactory)
                            .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);
            restTemplate.setRequestFactory(requestFactory);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create SSL context", e);
        }
    }

    public void configureTimeouts(int connectTimeoutMillis, int readTimeoutMillis) {
        HttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        requestFactory.setConnectTimeout(connectTimeoutMillis);
        requestFactory.setConnectionRequestTimeout(connectTimeoutMillis);
        requestFactory.setReadTimeout(readTimeoutMillis);

        restTemplate.setRequestFactory(requestFactory);
    }


    @Override
    public HttpResponse get(HttpRequest request) {
        String url = request.getUrl();
        Map<String, String> queryParams = request.getQueryParams();
        Map<String, String> pathVariables = request.getPathVariables();
        Map<String, String> headerParams = request.getHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        // Add query parameters if present
        if (MapUtils.isNotEmpty(queryParams)) {
            queryParams.forEach(builder::queryParam);
        }

        // Build URI with or without path variables
        URI uri = MapUtils.isNotEmpty(pathVariables)
                ? builder.buildAndExpand(pathVariables).toUri()
                : builder.build().toUri();

        log.info("URI={}", uri);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(headerParams)) {
            headerParams.forEach(headers::set);
        }

        // Wrap headers in HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Build response
        return buildResponse(apiResponse);
    }

    @Override
    public HttpResponse post(HttpRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getUrl());

        // Add query parameters
        if (MapUtils.isNotEmpty(request.getQueryParams())) {
            request.getQueryParams().forEach(builder::queryParam);
        }

        // Build URI with or without path variables
        URI uri = MapUtils.isNotEmpty(request.getPathVariables())
                ? builder.buildAndExpand(request.getPathVariables()).toUri()
                : builder.build().toUri();

        log.info("POST URI={}", uri);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(request.getHeaders())) {
            request.getHeaders().forEach(headers::set);
        }

        setContentType(request, headers);

        // Wrap body and headers
        HttpEntity<Object> entity = new HttpEntity<>(request.getBody(), headers);

        // Execute POST
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                String.class
        );

        return buildResponse(apiResponse);
    }

    @Override
    public HttpResponse put(HttpRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getUrl());

        // Add query parameters
        if (MapUtils.isNotEmpty(request.getQueryParams())) {
            request.getQueryParams().forEach(builder::queryParam);
        }

        // Build URI with or without path variables
        URI uri = MapUtils.isNotEmpty(request.getPathVariables())
                ? builder.buildAndExpand(request.getPathVariables()).toUri()
                : builder.build().toUri();

        log.info("PUT URI={}", uri);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(request.getHeaders())) {
            request.getHeaders().forEach(headers::set);
        }
        setContentType(request, headers);

        // Wrap body and headers
        HttpEntity<Object> entity = new HttpEntity<>(request.getBody(), headers);

        // Execute PUT
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                entity,
                String.class
        );

        return buildResponse(apiResponse);
    }

    @Override
    public HttpResponse patch(HttpRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getUrl());

        // Add query parameters
        if (MapUtils.isNotEmpty(request.getQueryParams())) {
            request.getQueryParams().forEach(builder::queryParam);
        }

        // Build URI with or without path variables
        URI uri = MapUtils.isNotEmpty(request.getPathVariables())
                ? builder.buildAndExpand(request.getPathVariables()).toUri()
                : builder.build().toUri();

        log.info("PATCH URI={}", uri);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(request.getHeaders())) {
            request.getHeaders().forEach(headers::set);
        }
        setContentType(request, headers);

        // Wrap body and headers
        HttpEntity<Object> entity = new HttpEntity<>(request.getBody(), headers);

        // Execute PATCH
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                uri,
                HttpMethod.PATCH,
                entity,
                String.class
        );

        return buildResponse(apiResponse);
    }

    @Override
    public HttpResponse delete(HttpRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getUrl());

        // Add query parameters
        if (MapUtils.isNotEmpty(request.getQueryParams())) {
            request.getQueryParams().forEach(builder::queryParam);
        }

        // Build URI with or without path variables
        URI uri = MapUtils.isNotEmpty(request.getPathVariables())
                ? builder.buildAndExpand(request.getPathVariables()).toUri()
                : builder.build().toUri();

        log.info("DELETE URI={}", uri);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(request.getHeaders())) {
            request.getHeaders().forEach(headers::set);
        }

        setContentType(request, headers);

        HttpEntity<Object> entity = new HttpEntity<>(request.getBody(), headers);

        // Execute DELETE
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        return buildResponse(apiResponse);
    }

    private HttpResponse buildResponse(ResponseEntity<String> apiResponse) {
        HttpResponse response = new HttpResponse();
        response.setObject(apiResponse);
        response.setStatusCode(apiResponse.getStatusCode().value());
        response.setHeaders(apiResponse.getHeaders().toSingleValueMap());
        response.setBody(apiResponse.getBody());
        return response;
    }

    private static void setContentType(HttpRequest request, HttpHeaders headers) {
        String contentType = request.getContentType();
        if (contentType != null && !contentType.isEmpty()) {
            headers.setContentType(MediaType.parseMediaType(contentType));
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
    }
}
