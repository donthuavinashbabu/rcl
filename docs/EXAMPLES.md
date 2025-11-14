# RCL Usage Examples

This document provides comprehensive examples of using the Java Rest Clients Library (RCL) in various scenarios.

## Table of Contents

- [Basic Usage](#basic-usage)
- [Authentication](#authentication)
- [Different HTTP Methods](#different-http-methods)
- [Client-Specific Examples](#client-specific-examples)
- [Advanced Configuration](#advanced-configuration)
- [Error Handling](#error-handling)
- [Integration Examples](#integration-examples)

## Basic Usage

### Simple GET Request

```java
import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.core.util.RestClientType;

public class BasicExample {
    public static void main(String[] args) {
        // Create client factory
        RestClientFactory factory = RestClientFactory.create(RestClientType.OKHTTP);
        RestClient client = factory.getClient();
        
        // Build request
        HttpRequest request = HttpRequest.builder()
            .url("https://jsonplaceholder.typicode.com/posts/1")
            .header("Accept", "application/json")
            .build();
        
        // Execute request
        HttpResponse response = client.get(request);
        
        // Process response
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());
    }
}
```

### POST Request with JSON Body

```java
public class PostExample {
    public static void main(String[] args) {
        RestClientFactory factory = RestClientFactory.create(RestClientType.APACHE_HTTP_CLIENT);
        RestClient client = factory.getClient();
        
        String jsonBody = """
            {
                "title": "New Post",
                "body": "This is the post content",
                "userId": 1
            }
            """;
        
        HttpRequest request = HttpRequest.builder()
            .url("https://jsonplaceholder.typicode.com/posts")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(jsonBody)
            .build();
        
        HttpResponse response = client.post(request);
        System.out.println("Created: " + response.getBody());
    }
}
```

## Authentication

### JWT Authentication

```java
import com.rcl.core.auth.JwtAuthProvider;

public class JwtExample {
    public static void main(String[] args) {
        // Create JWT auth provider
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
        JwtAuthProvider authProvider = new JwtAuthProvider(jwtToken);
        
        // Create authenticated client
        RestClientFactory factory = RestClientFactory.create(RestClientType.OKHTTP);
        RestClient client = factory.getClient(authProvider);
        
        // Make authenticated request
        HttpRequest request = HttpRequest.builder()
            .url("https://api.example.com/protected/resource")
            .build();
        
        HttpResponse response = client.get(request);
        System.out.println("Protected resource: " + response.getBody());
    }
}
```

### Custom Authentication

```java
import com.rcl.core.auth.AuthProvider;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthExample {
    
    static class ApiKeyAuthProvider implements AuthProvider {
        private final String apiKey;
        
        public ApiKeyAuthProvider(String apiKey) {
            this.apiKey = apiKey;
        }
        
        @Override
        public Map<String, String> getAuthHeaders() {
            Map<String, String> headers = new HashMap<>();
            headers.put("X-API-Key", apiKey);
            headers.put("Authorization", "Bearer " + generateToken());
            return headers;
        }
        
        private String generateToken() {
            // Custom token generation logic
            return "custom-token-" + System.currentTimeMillis();
        }
    }
    
    public static void main(String[] args) {
        ApiKeyAuthProvider authProvider = new ApiKeyAuthProvider("your-api-key");
        
        RestClientFactory factory = RestClientFactory.create(RestClientType.RETROFIT);
        RestClient client = factory.getClient(authProvider);
        
        HttpRequest request = HttpRequest.builder()
            .url("https://api.example.com/data")
            .build();
        
        HttpResponse response = client.get(request);
        System.out.println("API Response: " + response.getBody());
    }
}
```

## Different HTTP Methods

### Complete CRUD Operations

```java
public class CrudExample {
    private final RestClient client;
    private final String baseUrl = "https://jsonplaceholder.typicode.com/posts";
    
    public CrudExample() {
        RestClientFactory factory = RestClientFactory.create(RestClientType.WEBCLIENT);
        this.client = factory.getClient();
    }
    
    // CREATE
    public HttpResponse createPost(String title, String body, int userId) {
        String jsonBody = String.format("""
            {
                "title": "%s",
                "body": "%s", 
                "userId": %d
            }
            """, title, body, userId);
        
        HttpRequest request = HttpRequest.builder()
            .url(baseUrl)
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .build();
        
        return client.post(request);
    }
    
    // READ
    public HttpResponse getPost(int id) {
        HttpRequest request = HttpRequest.builder()
            .url(baseUrl + "/" + id)
            .header("Accept", "application/json")
            .build();
        
        return client.get(request);
    }
    
    // UPDATE
    public HttpResponse updatePost(int id, String title, String body) {
        String jsonBody = String.format("""
            {
                "id": %d,
                "title": "%s",
                "body": "%s",
                "userId": 1
            }
            """, id, title, body);
        
        HttpRequest request = HttpRequest.builder()
            .url(baseUrl + "/" + id)
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .build();
        
        return client.put(request);
    }
    
    // PARTIAL UPDATE
    public HttpResponse patchPost(int id, String title) {
        String jsonBody = String.format("""
            {
                "title": "%s"
            }
            """, title);
        
        HttpRequest request = HttpRequest.builder()
            .url(baseUrl + "/" + id)
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .build();
        
        return client.patch(request);
    }
    
    // DELETE
    public HttpResponse deletePost(int id) {
        HttpRequest request = HttpRequest.builder()
            .url(baseUrl + "/" + id)
            .build();
        
        return client.delete(request);
    }
}
```

## Client-Specific Examples

### OkHttp with Custom Configuration

```java
import com.rcl.okhttp.service.RestClientFactoryImpl;

public class OkHttpExample {
    public static void main(String[] args) {
        RestClientFactory factory = new RestClientFactoryImpl()
            .enableInterceptor()  // Enable logging interceptor
            .disableSsl();        // Disable SSL verification
        
        RestClient client = factory.getClient();
        
        HttpRequest request = HttpRequest.builder()
            .url("https://httpbin.org/get")
            .header("User-Agent", "RCL-OkHttp/1.0")
            .build();
        
        HttpResponse response = client.get(request);
        System.out.println("OkHttp Response: " + response.getBody());
    }
}
```

### Spring WebClient (Reactive)

```java
public class WebClientExample {
    public static void main(String[] args) {
        RestClientFactory factory = RestClientFactory.create(RestClientType.WEBCLIENT);
        RestClient client = factory.getClient();
        
        // WebClient handles reactive streams internally
        HttpRequest request = HttpRequest.builder()
            .url("https://api.github.com/users/octocat")
            .header("Accept", "application/vnd.github.v3+json")
            .build();
        
        HttpResponse response = client.get(request);
        System.out.println("GitHub User: " + response.getBody());
    }
}
```

## Advanced Configuration

### Multiple Clients with Different Configurations

```java
public class MultiClientExample {
    private final RestClient fastClient;
    private final RestClient secureClient;
    private final RestClient authenticatedClient;
    
    public MultiClientExample() {
        // Fast client for internal APIs
        this.fastClient = RestClientFactory.create(RestClientType.OKHTTP)
            .disableSsl()
            .getClient();
        
        // Secure client for external APIs
        this.secureClient = RestClientFactory.create(RestClientType.APACHE_HTTP_CLIENT)
            .getClient();
        
        // Authenticated client
        JwtAuthProvider auth = new JwtAuthProvider("jwt-token");
        this.authenticatedClient = RestClientFactory.create(RestClientType.FEIGN)
            .enableInterceptor()
            .getClient(auth);
    }
    
    public void makeRequests() {
        // Fast internal call
        HttpRequest internalRequest = HttpRequest.builder()
            .url("http://internal-api/health")
            .build();
        HttpResponse internalResponse = fastClient.get(internalRequest);
        
        // Secure external call
        HttpRequest externalRequest = HttpRequest.builder()
            .url("https://api.external.com/data")
            .build();
        HttpResponse externalResponse = secureClient.get(externalRequest);
        
        // Authenticated call
        HttpRequest authRequest = HttpRequest.builder()
            .url("https://api.secure.com/user/profile")
            .build();
        HttpResponse authResponse = authenticatedClient.get(authRequest);
    }
}
```

### Request/Response Interceptors

```java
public class InterceptorExample {
    public static void main(String[] args) {
        // Enable interceptors for logging and monitoring
        RestClientFactory factory = RestClientFactory.create(RestClientType.OKHTTP)
            .enableInterceptor();
        
        RestClient client = factory.getClient();
        
        // Interceptors will automatically log request/response details
        HttpRequest request = HttpRequest.builder()
            .url("https://httpbin.org/json")
            .header("X-Request-ID", "req-" + System.currentTimeMillis())
            .build();
        
        HttpResponse response = client.get(request);
        // Check logs for intercepted request/response information
    }
}
```

## Error Handling

### Comprehensive Error Handling

```java
import com.rcl.core.model.error.RclException;

public class ErrorHandlingExample {
    private final RestClient client;
    
    public ErrorHandlingExample() {
        this.client = RestClientFactory.create(RestClientType.OKHTTP).getClient();
    }
    
    public void handleErrors() {
        try {
            HttpRequest request = HttpRequest.builder()
                .url("https://httpbin.org/status/404")
                .build();
            
            HttpResponse response = client.get(request);
            
            // Check status code
            if (response.getStatusCode() >= 400) {
                handleHttpError(response);
            } else {
                System.out.println("Success: " + response.getBody());
            }
            
        } catch (RclException e) {
            System.err.println("RCL Error: " + e.getMessage());
            handleRclException(e);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    
    private void handleHttpError(HttpResponse response) {
        switch (response.getStatusCode()) {
            case 400 -> System.err.println("Bad Request: " + response.getBody());
            case 401 -> System.err.println("Unauthorized - check authentication");
            case 403 -> System.err.println("Forbidden - insufficient permissions");
            case 404 -> System.err.println("Not Found: " + response.getBody());
            case 429 -> System.err.println("Rate Limited - retry later");
            case 500 -> System.err.println("Server Error: " + response.getBody());
            default -> System.err.println("HTTP Error " + response.getStatusCode());
        }
    }
    
    private void handleRclException(RclException e) {
        // Log the error, implement retry logic, etc.
        System.err.println("Retrying request due to: " + e.getMessage());
    }
}
```

## Integration Examples

### Spring Boot Integration

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class RclSpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(RclSpringBootApp.class, args);
    }
}

@Configuration
class RclConfig {
    
    @Bean
    public RestClient defaultRestClient() {
        return RestClientFactory.create(RestClientType.WEBCLIENT)
            .enableInterceptor()
            .getClient();
    }
    
    @Bean
    public RestClient authenticatedRestClient() {
        JwtAuthProvider auth = new JwtAuthProvider(getJwtToken());
        return RestClientFactory.create(RestClientType.OKHTTP)
            .getClient(auth);
    }
    
    private String getJwtToken() {
        // Retrieve JWT token from configuration or security context
        return "your-jwt-token";
    }
}

@RestController
class ApiController {
    
    private final RestClient restClient;
    
    public ApiController(RestClient defaultRestClient) {
        this.restClient = defaultRestClient;
    }
    
    @GetMapping("/proxy/{id}")
    public ResponseEntity<String> proxyRequest(@PathVariable String id) {
        HttpRequest request = HttpRequest.builder()
            .url("https://jsonplaceholder.typicode.com/posts/" + id)
            .build();
        
        HttpResponse response = restClient.get(request);
        
        return ResponseEntity.status(response.getStatusCode())
            .body(response.getBody());
    }
}
```

### Microservices Communication

```java
public class MicroserviceClient {
    private final RestClient client;
    private final String serviceBaseUrl;
    
    public MicroserviceClient(String serviceBaseUrl, String authToken) {
        JwtAuthProvider auth = new JwtAuthProvider(authToken);
        this.client = RestClientFactory.create(RestClientType.FEIGN)
            .enableInterceptor()
            .getClient(auth);
        this.serviceBaseUrl = serviceBaseUrl;
    }
    
    public UserDto getUser(Long userId) {
        HttpRequest request = HttpRequest.builder()
            .url(serviceBaseUrl + "/users/" + userId)
            .header("Accept", "application/json")
            .build();
        
        HttpResponse response = client.get(request);
        
        if (response.getStatusCode() == 200) {
            // Parse JSON response to UserDto
            return parseUser(response.getBody());
        } else {
            throw new ServiceException("Failed to get user: " + response.getStatusCode());
        }
    }
    
    public List<OrderDto> getUserOrders(Long userId) {
        HttpRequest request = HttpRequest.builder()
            .url(serviceBaseUrl + "/users/" + userId + "/orders")
            .header("Accept", "application/json")
            .build();
        
        HttpResponse response = client.get(request);
        return parseOrders(response.getBody());
    }
    
    private UserDto parseUser(String json) {
        // JSON parsing logic
        return new UserDto();
    }
    
    private List<OrderDto> parseOrders(String json) {
        // JSON parsing logic
        return new ArrayList<>();
    }
}
```

This examples document provides comprehensive usage patterns for the RCL library across different scenarios and client types.