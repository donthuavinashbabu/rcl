# RCL API Reference

This document provides detailed API reference for the Java Rest Clients Library (RCL).

## Core Interfaces

### RestClient

The main interface for executing HTTP requests.

```java
public interface RestClient {
    HttpResponse get(HttpRequest request);
    HttpResponse post(HttpRequest request);
    HttpResponse put(HttpRequest request);
    HttpResponse patch(HttpRequest request);
    HttpResponse delete(HttpRequest request);
}
```

#### Methods

| Method | Description | Parameters | Returns |
|--------|-------------|------------|---------|
| `get(HttpRequest)` | Execute HTTP GET request | `request` - The HTTP request object | `HttpResponse` |
| `post(HttpRequest)` | Execute HTTP POST request | `request` - The HTTP request object | `HttpResponse` |
| `put(HttpRequest)` | Execute HTTP PUT request | `request` - The HTTP request object | `HttpResponse` |
| `patch(HttpRequest)` | Execute HTTP PATCH request | `request` - The HTTP request object | `HttpResponse` |
| `delete(HttpRequest)` | Execute HTTP DELETE request | `request` - The HTTP request object | `HttpResponse` |

### RestClientFactory

Factory interface for creating RestClient instances.

```java
public interface RestClientFactory {
    RestClient getClient();
    RestClient getClient(RestClientType restClientType);
    RestClient getClient(AuthProvider authProvider);
    RestClientType getType();
    RestClientFactory enableInterceptor();
    RestClientFactory disableSsl();
}
```

#### Methods

| Method | Description | Parameters | Returns |
|--------|-------------|------------|---------|
| `getClient()` | Create default client | None | `RestClient` |
| `getClient(RestClientType)` | Create client of specific type | `restClientType` - Type of client | `RestClient` |
| `getClient(AuthProvider)` | Create authenticated client | `authProvider` - Authentication provider | `RestClient` |
| `getType()` | Get factory type | None | `RestClientType` |
| `enableInterceptor()` | Enable request/response interceptors | None | `RestClientFactory` |
| `disableSsl()` | Disable SSL verification | None | `RestClientFactory` |

#### Static Methods

```java
public static RestClientFactory create(RestClientType type)
```

Creates a factory for the specified client type.

## Model Classes

### HttpRequest

Represents an HTTP request.

```java
public class HttpRequest {
    private String url;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> queryParams;
    
    // Builder pattern
    public static HttpRequestBuilder builder() { ... }
}
```

#### Builder Methods

| Method | Description | Parameters | Returns |
|--------|-------------|------------|---------|
| `url(String)` | Set request URL | `url` - The target URL | `HttpRequestBuilder` |
| `header(String, String)` | Add header | `name`, `value` - Header name and value | `HttpRequestBuilder` |
| `headers(Map<String, String>)` | Set all headers | `headers` - Map of headers | `HttpRequestBuilder` |
| `body(String)` | Set request body | `body` - Request body content | `HttpRequestBuilder` |
| `queryParam(String, String)` | Add query parameter | `name`, `value` - Parameter name and value | `HttpRequestBuilder` |
| `queryParams(Map<String, String>)` | Set all query parameters | `params` - Map of parameters | `HttpRequestBuilder` |
| `build()` | Build the request | None | `HttpRequest` |

#### Example Usage

```java
HttpRequest request = HttpRequest.builder()
    .url("https://api.example.com/users")
    .header("Accept", "application/json")
    .header("Content-Type", "application/json")
    .queryParam("page", "1")
    .queryParam("size", "10")
    .body("{\"name\": \"John\"}")
    .build();
```

### HttpResponse

Represents an HTTP response.

```java
public class HttpResponse {
    private int statusCode;
    private String body;
    private Map<String, List<String>> headers;
    private long responseTime;
    
    // Getters and setters
    public int getStatusCode() { ... }
    public String getBody() { ... }
    public Map<String, List<String>> getHeaders() { ... }
    public List<String> getHeader(String name) { ... }
    public long getResponseTime() { ... }
}
```

#### Methods

| Method | Description | Returns |
|--------|-------------|---------|
| `getStatusCode()` | Get HTTP status code | `int` |
| `getBody()` | Get response body | `String` |
| `getHeaders()` | Get all headers | `Map<String, List<String>>` |
| `getHeader(String)` | Get specific header values | `List<String>` |
| `getResponseTime()` | Get response time in milliseconds | `long` |
| `isSuccessful()` | Check if status code is 2xx | `boolean` |
| `isClientError()` | Check if status code is 4xx | `boolean` |
| `isServerError()` | Check if status code is 5xx | `boolean` |

## Authentication

### AuthProvider

Interface for providing authentication headers.

```java
public interface AuthProvider {
    Map<String, String> getAuthHeaders();
}
```

### JwtAuthProvider

Built-in JWT authentication provider.

```java
public class JwtAuthProvider implements AuthProvider {
    public JwtAuthProvider(String token) { ... }
    
    @Override
    public Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return headers;
    }
}
```

#### Constructor

| Constructor | Description | Parameters |
|-------------|-------------|------------|
| `JwtAuthProvider(String)` | Create JWT auth provider | `token` - JWT token |

#### Example Usage

```java
JwtAuthProvider auth = new JwtAuthProvider("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...");
RestClient client = factory.getClient(auth);
```

### Custom AuthProvider

```java
public class CustomAuthProvider implements AuthProvider {
    private final String apiKey;
    private final String secretKey;
    
    public CustomAuthProvider(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }
    
    @Override
    public Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-API-Key", apiKey);
        headers.put("X-Secret", secretKey);
        headers.put("Authorization", "Custom " + generateSignature());
        return headers;
    }
    
    private String generateSignature() {
        // Custom signature generation logic
        return Base64.getEncoder().encodeToString((apiKey + ":" + secretKey).getBytes());
    }
}
```

## Enums

### RestClientType

Enumeration of supported HTTP client types.

```java
public enum RestClientType {
    OKHTTP,
    APACHE_HTTP_CLIENT,
    RETROFIT,
    WEBCLIENT,
    FEIGN,
    REST_TEMPLATE
}
```

| Value | Description | Implementation |
|-------|-------------|----------------|
| `OKHTTP` | OkHttp client | High-performance HTTP client |
| `APACHE_HTTP_CLIENT` | Apache HttpClient | Robust and feature-rich |
| `RETROFIT` | Retrofit client | Type-safe REST client |
| `WEBCLIENT` | Spring WebClient | Reactive web client |
| `FEIGN` | OpenFeign client | Declarative REST client |
| `REST_TEMPLATE` | Spring RestTemplate | Traditional Spring HTTP client |

## Exceptions

### RclException

Base exception for RCL library errors.

```java
public class RclException extends RuntimeException {
    public RclException(String message) { ... }
    public RclException(String message, Throwable cause) { ... }
    public RclException(Throwable cause) { ... }
}
```

#### Common Scenarios

- Network connectivity issues
- Invalid URL format
- Authentication failures
- Request timeout
- SSL/TLS errors

#### Example Handling

```java
try {
    HttpResponse response = client.get(request);
} catch (RclException e) {
    logger.error("RCL request failed: {}", e.getMessage(), e);
    // Implement retry logic or fallback
}
```

## Utility Classes

### Utils

Utility methods for common operations.

```java
public class Utils {
    public static void requireNonBlank(String value, String fieldName);
    public static void requireNonNull(Object value, String fieldName);
    public static String buildUrl(String baseUrl, Map<String, String> queryParams);
    public static Map<String, String> parseHeaders(String headerString);
}
```

### CoreConstants

Constants used throughout the library.

```java
public class CoreConstants {
    public static final String URL = "URL";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public static final String USER_AGENT = "User-Agent";
    
    // HTTP Methods
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";
}
```

## Retry and Resilience

### RetryExecutor

Built-in retry mechanism using Resilience4j.

```java
public class RetryExecutor {
    public RetryExecutor(String name, int maxAttempts, Duration waitDuration);
    public <T> T execute(Supplier<T> operation);
}
```

#### Configuration

| Parameter | Description | Default |
|-----------|-------------|---------|
| `name` | Retry configuration name | Client-specific |
| `maxAttempts` | Maximum retry attempts | 3 |
| `waitDuration` | Wait time between retries | 500ms |

#### Example Usage

```java
RetryExecutor retryExecutor = new RetryExecutor("api-client", 5, Duration.ofSeconds(1));

HttpResponse response = retryExecutor.execute(() -> {
    return client.get(request);
});
```

## Configuration

### Client-Specific Configuration

Each client implementation may support additional configuration options:

#### OkHttp Configuration

```java
// Custom timeouts
OkHttpClient customClient = new OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build();
```

#### Apache HttpClient Configuration

```java
// Custom connection pool
RequestConfig config = RequestConfig.custom()
    .setConnectTimeout(30000)
    .setSocketTimeout(60000)
    .build();
```

#### WebClient Configuration

```java
// Custom WebClient settings
WebClient webClient = WebClient.builder()
    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
    .build();
```

## Thread Safety

All RCL components are designed to be thread-safe:

- **RestClient instances**: Thread-safe, can be shared across threads
- **RestClientFactory**: Thread-safe factory methods
- **HttpRequest/HttpResponse**: Immutable objects, thread-safe
- **AuthProvider**: Should be implemented as thread-safe

## Performance Considerations

### Connection Pooling

Most HTTP clients use connection pooling by default:

- **OkHttp**: Built-in connection pool (5 connections per host)
- **Apache HttpClient**: Configurable connection manager
- **WebClient**: Netty connection pool

### Memory Usage

- **Request/Response objects**: Lightweight, minimal memory footprint
- **Body content**: Stored as String, consider streaming for large payloads
- **Headers**: Stored as Map, efficient for typical header counts

### Async Operations

While the current API is synchronous, underlying clients may use async operations:

- **WebClient**: Fully reactive, non-blocking
- **OkHttp**: Async execution with blocking facade
- **Retrofit**: Supports both sync and async calls

## Migration Guide

### From Direct HTTP Client Usage

```java
// Before (OkHttp)
OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder()
    .url("https://api.example.com/data")
    .build();
Response response = client.newCall(request).execute();

// After (RCL)
RestClient client = RestClientFactory.create(RestClientType.OKHTTP).getClient();
HttpRequest request = HttpRequest.builder()
    .url("https://api.example.com/data")
    .build();
HttpResponse response = client.get(request);
```

### From Spring RestTemplate

```java
// Before
RestTemplate restTemplate = new RestTemplate();
ResponseEntity<String> response = restTemplate.getForEntity(
    "https://api.example.com/data", String.class);

// After
RestClient client = RestClientFactory.create(RestClientType.REST_TEMPLATE).getClient();
HttpRequest request = HttpRequest.builder()
    .url("https://api.example.com/data")
    .build();
HttpResponse response = client.get(request);
```

This API reference provides comprehensive documentation for all public interfaces and classes in the RCL library.