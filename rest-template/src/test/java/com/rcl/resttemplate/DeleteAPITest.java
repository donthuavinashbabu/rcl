package com.rcl.resttemplate;

import com.rcl.core.RestClient;
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
public class DeleteAPITest {

    @Test
    @DisplayName("DELETE API with text body")
    void deleteAPI() {
        String url = "http://localhost:9000/delete/api/v1/students";
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
        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        HttpResponse httpResponse = restClient.delete(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("DELETE API with json body")
    void deleteAPI2() {
        String url = "http://localhost:9000/delete/api/v2/students";
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
        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        HttpResponse httpResponse = restClient.delete(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("DELETE API with json body request headers")
    void deleteAPI3() {
        String url = "http://localhost:9000/delete/api/v3/students";
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
        HttpResponse httpResponse = restClient.delete(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("DELETE API with json body request params")
    void deleteAPI4() {
        String url = "http://localhost:9000/delete/api/v4/students";
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
        HttpResponse httpResponse = restClient.delete(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("DELETE API with json body path variables")
    void deleteAPI5() {
        String url = "http://localhost:9000/delete/api/v5/students/{name}/{book}";
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
        HttpResponse httpResponse = restClient.delete(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }

    @Test
    @DisplayName("DELETE API with json body request parameter request header path variables")
    void deleteAPI6() {
        String url = "http://localhost:9000/delete/api/v6/students/{id}";
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
        HttpResponse httpResponse = restClient.delete(httpRequest);
        System.out.println("Response Body: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
    }
}
