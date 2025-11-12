package com.rcl.webclient;

import com.rcl.core.RestClient;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.webclient.service.RestClientFactoryImpl;

public class Main {

    public static void main(String[] args) {
//        String url = "http://localhost:8765/xitara/pdp/state-hierarchy/test";
        String url = "http://localhost:8765/xitara/pdp/state-hierarchy/api/v1/find-all/state";

        RestClient restClient = RestClientFactoryImpl.getInstance().getClient();
        System.out.println("RestClient: " + restClient.getClass().getName());
        HttpRequest httpRequest = HttpRequest.builder().url(url).build();
        HttpResponse httpResponse = restClient.get(httpRequest);
        System.out.println("Response: " + httpResponse.getBody());
        System.out.println("Status Code: " + httpResponse.getStatusCode());
        System.out.println("Multi map headers: " + httpResponse.getMultiMapHeaders());
        System.out.println("Headers: " + httpResponse.getHeaders());
    }

}
