package com.rcl.resttemplate;

import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.resttemplate.service.RestClientFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Disabled
@Slf4j
public class GetAPITest {

    @Test
    @DisplayName("GET API with no headers, no params, no path variables return text response")
    void getApi() {
        String url = "http://localhost:9000/get/api/v1/hello-world";

        RestClientFactory restClientFactory = RestClientFactoryImpl.getInstance()
                .disableSsl().enableInterceptor().configureTimeouts(3000, 3000);
        RestClient restClient = restClientFactory.getClient();

        log.info("RestClient: " + restClient.getClass().getName());

        HttpRequest httpRequest = HttpRequest.builder().url(url).build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

    @Test
    @DisplayName("GET API with no headers, no params, no path variables return json response")
    void getApi2() {
        String url = "http://localhost:9000/get/api/v1/students";

//        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        RestClientFactory restClientFactory = RestClientFactoryImpl.getInstance()
                .disableSsl().enableInterceptor().configureTimeouts(3000, 3000);
        RestClient restClient = restClientFactory.getClient();
        log.info("RestClient: " + restClient.getClass().getName());

        HttpRequest httpRequest = HttpRequest.builder().url(url).build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

    @Test
    public void getApiWithHeaders() {
        String url = "http://localhost:9000/get/api/v4/students";

//        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        RestClientFactory restClientFactory = RestClientFactoryImpl.getInstance()
                .disableSsl().enableInterceptor().configureTimeouts(3000, 3000);
        RestClient restClient = restClientFactory.getClient();
        log.info("RestClient: " + restClient.getClass().getName());

        Map<String, String> headers = Map.of(
                "name", "name-1",
                "book", "book-1"
        );

        HttpRequest httpRequest = HttpRequest.builder().url(url)
                .headers(headers)
                .build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

    @Test
    public void getAPIWithRequestParams() {
        String url = "http://localhost:9000/get/api/v5/students";

        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        log.info("RestClient: " + restClient.getClass().getName());

        Map<String, String> queryParams = Map.of(
                "name", "name-1",
                "book", "book-1"
        );

        HttpRequest httpRequest = HttpRequest.builder().url(url)
                .queryParams(queryParams)
                .build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

    @Test
    public void getAPIWithPathVariables() {
        String url = "http://localhost:9000/get/api/v6/students/{name}/{book}";

        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        log.info("RestClient: " + restClient.getClass().getName());

        Map<String, String> pathVariables = Map.of(
                "name", "name-1",
                "book", "book-1"
        );

        HttpRequest httpRequest = HttpRequest.builder().url(url)
                .pathVariables(pathVariables)
                .build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

    @Test
    public void getAPIWithPaginationRequestParams() {
        String url = "http://localhost:9000/get/api/v7/students";

        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        log.info("RestClient: " + restClient.getClass().getName());

        Map<String, String> queryParams = Map.of(
                "page", "0",
                "size", "1",
                "sort", "asc"
        );

        HttpRequest httpRequest = HttpRequest.builder().url(url)
                .queryParams(queryParams)
                .build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

}
