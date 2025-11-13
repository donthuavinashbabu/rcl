package com.rcl.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private int statusCode;
    private String body;
    private Map<String, String> headers;
    private Map<String, List<String>> multiMapHeaders;
    private Object object;
}
