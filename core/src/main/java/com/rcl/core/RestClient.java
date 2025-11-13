package com.rcl.core;

import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;

public interface RestClient {

    HttpResponse get(HttpRequest request);

    HttpResponse post(HttpRequest request);

    HttpResponse put(HttpRequest request);

    HttpResponse patch(HttpRequest request);

    HttpResponse delete(HttpRequest request);

}