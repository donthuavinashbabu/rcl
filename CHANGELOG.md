# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Initial open source release preparation
- Comprehensive documentation
- Contributing guidelines
- Apache 2.0 license

## [1.0.0] - 2024-01-XX

### Added
- Core REST client interface and factory pattern
- OkHttp client implementation
- Apache HttpClient implementation  
- Retrofit client implementation
- Spring WebClient implementation
- OpenFeign client implementation
- Spring RestTemplate implementation
- JWT authentication provider
- Custom authentication provider interface
- Retry mechanism with Resilience4j integration
- SSL configuration options
- Request/Response interceptor support
- Comprehensive logging
- Multi-module Maven project structure
- Integration test suite

### Features
- **Unified API**: Single interface for multiple HTTP client implementations
- **Authentication**: Built-in JWT and extensible auth providers
- **Resilience**: Configurable retry policies and circuit breakers
- **Flexibility**: Modular design with optional SSL and interceptor configuration
- **Performance**: Optimized implementations for each HTTP client
- **Testing**: Comprehensive test coverage across all modules

### Dependencies
- Java 17+ requirement
- Maven 3.6+ for building
- Lombok for reducing boilerplate code
- Resilience4j for fault tolerance
- Apache Commons utilities
- SLF4J for logging

### Modules
- `core`: Core interfaces and models
- `okhttp`: OkHttp 5.x implementation
- `apache-http-client`: Apache HttpClient implementation
- `retrofit`: Retrofit 3.x implementation  
- `webclient`: Spring WebClient implementation
- `feign`: OpenFeign implementation
- `rest-template`: Spring RestTemplate implementation
- `test`: Integration tests and examples
- `rcl-all`: Convenience module with all implementations

### Documentation
- Complete README with usage examples
- API documentation with JavaDoc
- Contributing guidelines
- License information
- Module-specific documentation

---

## Release Notes Format

### Added
- New features and functionality

### Changed  
- Changes in existing functionality

### Deprecated
- Soon-to-be removed features

### Removed
- Removed features

### Fixed
- Bug fixes

### Security
- Security vulnerability fixes

---

## Upcoming Releases

### [1.1.0] - Planned
- Async/Reactive support for all clients
- Metrics and monitoring integration
- Performance benchmarking suite
- Enhanced error handling

### [1.2.0] - Planned  
- GraphQL client support
- HTTP/3 support where available
- Advanced caching mechanisms
- Request/Response transformation utilities

### [2.0.0] - Future
- Breaking API changes for improved consistency
- Minimum Java version upgrade
- New client implementations
- Enhanced configuration options