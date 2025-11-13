package com.rcl.resttemplate;

import com.rcl.core.RestClient;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.resttemplate.service.RestClientFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class Main {

    public static void main(String[] args) {
//        String url = "http://localhost:8765/xitara/pdp/state-hierarchy/test";
        String url = "http://localhost:8765/xitara/pdp/state-hierarchy/api/v1/find-all/state";

        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        log.info("RestClient: " + restClient.getClass().getName());
        HttpRequest httpRequest = HttpRequest.builder().url(url).build();
        HttpResponse<ResponseEntity<String>> httpResponse = restClient.get(httpRequest);
        log.info("Response: " + httpResponse.getBody());
        log.info("Status Code: " + httpResponse.getStatusCode());
        log.info("Multi map headers: " + httpResponse.getMultiMapHeaders());
        log.info("Headers: " + httpResponse.getHeaders());
        Object object = httpResponse.getObject();
        System.out.println(object);
    }

}
