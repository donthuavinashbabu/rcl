package com.rcl.core;

import com.rcl.core.util.RestClientType;

public class RestClientFactory {

    public static RestClient getClient(RestClientType restClientType) {
        /*switch (restClientType) {
            case APACHE_HTTPCLIENT:
                return new ApacheHttpClient();
            case OKHTTP:
                return new OkHttpClient();
            case RETROFIT:
                return new RetrofitClient();
            case SPRING_WEBCLIENT:
                return new SpringWebClient();
            case JAVA_HTTP_URL_CONNECTION:
                return new JavaHttpUrlConnectionClient();
            case FEIGN:
                return new FeignClient();
            default:
                throw new IllegalArgumentException("Unsupported RestClientType: " + restClientType);
        }*/
        // todo: implement clients
        return null;
    }
}
