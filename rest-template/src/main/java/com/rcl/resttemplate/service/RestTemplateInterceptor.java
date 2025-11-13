package com.rcl.resttemplate.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;

import java.io.IOException;

@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        StopWatch stopWatch = new StopWatch("RestTemplateInterceptor");
        stopWatch.start();
        HttpStatusCode status = null;
        try {
            ClientHttpResponse response = execution.execute(request, body);
            status = response.getStatusCode();
            return response;
        } finally {
            stopWatch.stop();
            log.info("Intercepting REST call. URL={}, METHOD={}, TIME={} millis, HTTP-STATUS={}", request.getURI(),
                    request.getMethod(), stopWatch.getTotalTimeMillis(), status);
        }
    }

}