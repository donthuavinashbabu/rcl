package com.rcl.retrofit.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

import java.util.Map;

public interface RetrofitService {
    @GET
    Call<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headers);

    @POST
    Call<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    // Add PUT, DELETE similarly
}