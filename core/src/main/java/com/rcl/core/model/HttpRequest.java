package com.rcl.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequest {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private String body;
}
