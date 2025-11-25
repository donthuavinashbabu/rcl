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
import org.springframework.http.MediaType;

import java.util.Map;

@Disabled
@Slf4j
public class PostAPITest {

    @Test
    @DisplayName("POST API with text body")
    void postAPI() {
        String url = "http://localhost:9000/post/api/v1/students";
        String requestBody = """
                {
                    "name": "name-1",
                    "book": "book-1"
                }
                """;

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .body(requestBody)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .build();
//        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        RestClientFactory restClientFactory = RestClientFactoryImpl.getInstance()
                .disableSsl().enableInterceptor().configureTimeouts(3000, 3000);
        RestClient restClient = restClientFactory.getClient();
        HttpResponse httpResponse = restClient.post(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("POST API with json body")
    void postAPI2() {
        String url = "http://localhost:9000/post/api/v2/students";
        String requestBody = """
                {
                    "name": "name-1",
                    "book": "book-1"
                }
                """;

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .body(requestBody)
                .build();
//        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        RestClientFactory restClientFactory = RestClientFactoryImpl.getInstance()
                .disableSsl().enableInterceptor().configureTimeouts(3000, 3000);
        RestClient restClient = restClientFactory.getClient();
        HttpResponse httpResponse = restClient.post(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("POST API with json body request headers")
    void postAPI3() {
        String url = "http://localhost:9000/post/api/v3/students";
        String requestBody = "{}";

        Map<String, String> headers = Map.of(
                "name", "name-1",
                "book", "book-1"
        );

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .body(requestBody)
                .headers(headers)
                .build();
        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        HttpResponse httpResponse = restClient.post(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("POST API with json body request params")
    void postAPI4() {
        String url = "http://localhost:9000/post/api/v4/students";
        String requestBody = "{}";

        Map<String, String> queryParams = Map.of(
                "name", "name-1",
                "book", "book-1"
        );

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .body(requestBody)
                .queryParams(queryParams)
                .build();
        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        HttpResponse httpResponse = restClient.post(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("POST API with json body path variables")
    void postAPI5() {
        String url = "http://localhost:9000/post/api/v5/students/{name}/{book}";
        String requestBody = "{}";

        Map<String, String> pathVariables = Map.of(
                "name", "name-1",
                "book", "book-1"
        );

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .body(requestBody)
                .pathVariables(pathVariables)
                .build();
        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        HttpResponse httpResponse = restClient.post(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("POST API with json body request parameter request header path variables")
    void postAPI6() {
        String url = "http://localhost:9000/post/api/v6/students/{id}";
        String requestBody = "{}";

        Map<String, String> queryParams = Map.of(
                "book", "book-1"
        );

        Map<String, String> headers = Map.of(
                "name", "name-1"
        );

        Map<String, String> pathVariables = Map.of(
                "id", "100"
        );

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .body(requestBody)
                .queryParams(queryParams)
                .headers(headers)
                .pathVariables(pathVariables)
                .build();
        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        HttpResponse httpResponse = restClient.post(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }
}
