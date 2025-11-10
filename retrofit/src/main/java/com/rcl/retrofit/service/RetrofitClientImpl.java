package com.rcl.retrofit.service;

import com.rcl.core.RestClient;
import com.rcl.core.RetryExecutor;
import com.rcl.core.auth.AuthProvider;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.core.util.CoreConstants;
import com.rcl.core.util.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.MapUtils;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
public class RetrofitClientImpl implements RestClient {
    private final RetrofitService service;
    private final RetryExecutor retryExecutor;
    @Setter
    @Getter
    private AuthProvider authProvider;

    public RetrofitClientImpl(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // overridden by @Url
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(RetrofitService.class);
        this.retryExecutor = new RetryExecutor("retrofit", 3, Duration.ofMillis(500));
    }

    @Override
    public HttpResponse get(HttpRequest request) {
        return retryExecutor.execute(() -> {
            try {
                String url = request.getUrl();
                Utils.requireNonBlank(url, CoreConstants.URL);
                log.info("Executing GET request to URL: {}", url);
                Map<String, String> headers = MapUtils.emptyIfNull(request.getHeaders());
                if(null != authProvider) {
                    headers.putAll(authProvider.getAuthHeaders());
                }
                Response<ResponseBody> response = service.get(url, headers).execute();
                int code = response.code();
                Map<String, List<String>> multimap = response.headers().toMultimap();
                HttpResponse httpResponse;
                try(ResponseBody body = response.body()) {
                    if(body != null) {
                        httpResponse = HttpResponse.builder().statusCode(code)
                                .body(body.string())
                                .multiMapHeaders(multimap)
                                .build();
                    } else {
                        httpResponse = HttpResponse.builder().statusCode(code)
                                .multiMapHeaders(multimap)
                                .build();
                    }
                }

                return httpResponse;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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