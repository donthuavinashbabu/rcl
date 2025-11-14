# Java Rest Clients Library (RCL)

[![Maven Central](https://img.shields.io/maven-central/v/com.rcl/rcl.svg)](https://search.maven.org/artifact/com.rcl/rcl)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java Version](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)

A unified Java library that provides a consistent interface for multiple HTTP client implementations, allowing you to switch between different REST clients without changing your application code.

## 🚀 Features

- **Unified Interface**: Single API for multiple HTTP client implementations
- **Multiple Client Support**: OkHttp, Apache HttpClient, Retrofit, WebClient, Feign, RestTemplate
- **Authentication**: Built-in JWT and custom authentication providers
- **Resilience**: Integrated retry mechanisms with Resilience4j
- **Flexible Configuration**: SSL configuration, interceptors, and custom settings
- **Lightweight**: Modular design - use only what you need

## 📦 Supported HTTP Clients

| Module | HTTP Client | Description |
|--------|-------------|-------------|
| `okhttp` | OkHttp 5.x | High-performance HTTP client |
| `apache-http-client` | Apache HttpClient | Robust and feature-rich client |
| `retrofit` | Retrofit 3.x | Type-safe REST client |
| `webclient` | Spring WebClient | Reactive web client |
| `feign` | OpenFeign | Declarative REST client |
| `rest-template` | Spring RestTemplate | Traditional Spring HTTP client |
| `rcl-all` | All clients | Convenience module with all implementations |

## 🛠️ Installation

### Maven

Add the parent dependency:
```xml
<dependency>
    <groupId>com.rcl</groupId>
    <artifactId>rcl</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

### Individual Modules

For specific HTTP client implementations:

```xml
<!-- OkHttp -->
<dependency>
    <groupId>com.rcl</groupId>
    <artifactId>okhttp</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Apache HttpClient -->
<dependency>
    <groupId>com.rcl</groupId>
    <artifactId>apache-http-client</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- All clients -->
<dependency>
    <groupId>com.rcl</groupId>
    <artifactId>rcl-all</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.rcl:okhttp:1.0.0'
implementation 'com.rcl:apache-http-client:1.0.0'
// or all clients
implementation 'com.rcl:rcl-all:1.0.0'
```

## 🔧 Quick Start

### Basic Usage

```java
import com.rcl.core.RestClient;
import com.rcl.core.RestClientFactory;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;
import com.rcl.core.util.RestClientType;

// Create a client factory
RestClientFactory factory = RestClientFactory.create(RestClientType.OKHTTP);
RestClient client = factory.getClient();

// Make a GET request
HttpRequest request = HttpRequest.builder()
    .url("https://api.example.com/users")
    .header("Accept", "application/json")
    .build();

HttpResponse response = client.get(request);
System.out.println("Status: " + response.getStatusCode());
System.out.println("Body: " + response.getBody());
```

### With Authentication

```java
import com.rcl.core.auth.JwtAuthProvider;

// Create JWT auth provider
JwtAuthProvider authProvider = new JwtAuthProvider("your-jwt-token");

// Create client with authentication
RestClient client = factory.getClient(authProvider);

HttpResponse response = client.get(request);
```

### Advanced Configuration

```java
// Enable interceptors and disable SSL verification
RestClientFactory factory = RestClientFactory.create(RestClientType.OKHTTP)
    .enableInterceptor()
    .disableSsl();

RestClient client = factory.getClient();
```

## 📚 API Reference

### Core Interfaces

#### RestClient
```java
public interface RestClient {
    HttpResponse get(HttpRequest request);
    HttpResponse post(HttpRequest request);
    HttpResponse put(HttpRequest request);
    HttpResponse patch(HttpRequest request);
    HttpResponse delete(HttpRequest request);
}
```

#### RestClientFactory
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

### Models

#### HttpRequest
```java
HttpRequest request = HttpRequest.builder()
    .url("https://api.example.com/endpoint")
    .header("Content-Type", "application/json")
    .body("{\"key\": \"value\"}")
    .build();
```

#### HttpResponse
```java
public class HttpResponse {
    private int statusCode;
    private String body;
    private Map<String, List<String>> headers;
    // getters and setters
}
```

## 🔐 Authentication

### JWT Authentication
```java
JwtAuthProvider authProvider = new JwtAuthProvider("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...");
RestClient client = factory.getClient(authProvider);
```

### Custom Authentication
```java
public class CustomAuthProvider implements AuthProvider {
    @Override
    public Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("X-API-Key", getApiKey());
        return headers;
    }
}
```

## 🔄 Retry and Resilience

The library includes built-in retry mechanisms using Resilience4j:

- **Default Retry**: 3 attempts with 500ms delay
- **Circuit Breaker**: Automatic failure detection
- **Rate Limiting**: Request throttling capabilities

## 🏗️ Architecture

```
rcl/
├── core/                    # Core interfaces and models
├── okhttp/                  # OkHttp implementation
├── apache-http-client/      # Apache HttpClient implementation
├── retrofit/                # Retrofit implementation
├── webclient/               # Spring WebClient implementation
├── feign/                   # OpenFeign implementation
├── rest-template/           # Spring RestTemplate implementation
├── test/                    # Integration tests
└── rcl-all/                 # All implementations bundle
```

## 🧪 Testing

Run all tests:
```bash
mvn clean test
```

Run specific module tests:
```bash
mvn clean test -pl okhttp
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Setup

1. **Prerequisites**: Java 17+, Maven 3.6+
2. **Clone**: `git clone https://github.com/your-org/rcl.git`
3. **Build**: `mvn clean install`
4. **Test**: `mvn test`

### Adding New HTTP Client

1. Create new module: `mvn archetype:generate -DgroupId=com.rcl -DartifactId=new-client -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`
2. Implement `RestClient` and `RestClientFactory` interfaces
3. Add module to parent `pom.xml`
4. Add tests and documentation

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## 🐛 Issues and Support

- **Bug Reports**: [GitHub Issues](https://github.com/your-org/rcl/issues)
- **Feature Requests**: [GitHub Discussions](https://github.com/your-org/rcl/discussions)
- **Documentation**: [Wiki](https://github.com/your-org/rcl/wiki)

## 📈 Roadmap

- [ ] Async/Reactive support for all clients
- [ ] Metrics and monitoring integration
- [ ] GraphQL client support
- [ ] HTTP/3 support
- [ ] Performance benchmarking suite

## 🙏 Acknowledgments

- [OkHttp](https://square.github.io/okhttp/) - Efficient HTTP client
- [Apache HttpClient](https://hc.apache.org/) - Robust HTTP toolkit
- [Retrofit](https://square.github.io/retrofit/) - Type-safe HTTP client
- [Spring WebClient](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html) - Reactive web client
- [OpenFeign](https://github.com/OpenFeign/feign) - Declarative REST client
- [Resilience4j](https://resilience4j.readme.io/) - Fault tolerance library

---

**Made with ❤️ for the Java community**