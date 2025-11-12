package com.rcl.resttemplate.service;

import com.rcl.core.RestClient;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestClientImpl implements RestClient {
    private final RestTemplate restTemplate = new RestTemplate();
    @Setter
    @Getter
    private AuthProvider authProvider;

    @Override
    public HttpResponse get(HttpRequest request) {
        StringBuilder url = new StringBuilder(request.getUrl());
        Map<String, String> queryParams = request.getQueryParams();
        Map<String, String> pathVariables = request.getPathVariables();
        Map<String, String> headers = request.getHeaders();

        Map<String, String> pathAndQueryParams = new HashMap<>();
        pathAndQueryParams.putAll(queryParams);
        pathAndQueryParams.putAll(pathVariables);

        //            url.append("?");
//            for(Map.Entry<String, String> entry : queryParams.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                url.append(key).append("=").append(value).append(CoreConstants.AMPERSAND);
//            }
//            int lastIndexOfAmpersand = url.lastIndexOf(CoreConstants.AMPERSAND);
//            url.deleteCharAt(lastIndexOfAmpersand);

        String responseBody;
        if (MapUtils.isNotEmpty(pathAndQueryParams)) {
            responseBody = restTemplate.getForObject(url.toString(), String.class, pathAndQueryParams);
        } else {
            responseBody = restTemplate.getForObject(url.toString(), String.class);
        }

        if(MapUtils.isNotEmpty(headers)) {
            HttpHeaders httpHeaders = new HttpHeaders();
            for(Map.Entry<String, String> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue();
                httpHeaders.add(key, value);
            }
            HttpEntity<String> entity = new HttpEntity<String>("parameters", httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, String.class);
            HttpResponse response1 = new HttpResponse();
            response1.setStatusCode(response.getStatusCode().value());
            response1.setHeaders(response.getHeaders().toSingleValueMap());
            response1.setBody(response.getBody());

        }

        HttpResponse response = new HttpResponse();
        response.setBody(responseBody);
        return response;
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
