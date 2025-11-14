package com.rcl.core;

import com.rcl.core.auth.AuthProvider;
import com.rcl.core.util.RestClientType;

public interface RestClientFactory {
    RestClient getClient();
    RestClient getClient(RestClientType restClientType);
    RestClient getClient(AuthProvider authProvider);
    RestClientType getType();

   default RestClientFactory enableInterceptor(){
       return null;
   }

    default RestClientFactory disableSsl(){
       return null;
    }
}