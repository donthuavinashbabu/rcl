# Contributing to Java Rest Clients Library (RCL)

Thank you for your interest in contributing to RCL! This document provides guidelines and information for contributors.

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

### Development Setup

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/your-username/rcl.git
   cd rcl
   ```
3. **Build the project**:
   ```bash
   mvn clean install
   ```
4. **Run tests**:
   ```bash
   mvn test
   ```

## 📋 How to Contribute

### Reporting Issues

- Use GitHub Issues to report bugs or request features
- Search existing issues before creating new ones
- Provide clear, detailed descriptions with steps to reproduce
- Include relevant system information (Java version, OS, etc.)

### Submitting Changes

1. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**:
   - Follow the coding standards (see below)
   - Add tests for new functionality
   - Update documentation as needed

3. **Test your changes**:
   ```bash
   mvn clean test
   ```

4. **Commit your changes**:
   ```bash
   git commit -m "feat: add new HTTP client implementation"
   ```

5. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request** on GitHub

## 🎯 Adding New HTTP Client Implementation

To add support for a new HTTP client:

1. **Create new module**:
   ```bash
   mvn archetype:generate -DgroupId=com.rcl -DartifactId=new-client -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```

2. **Implement required interfaces**:
   - `RestClient` - Core HTTP operations
   - `RestClientFactory` - Client creation and configuration

3. **Add dependencies** to module's `pom.xml`

4. **Update parent `pom.xml`** to include new module

5. **Add comprehensive tests**

6. **Update documentation**

## 📝 Coding Standards

### Java Code Style

- Use Java 17+ features where appropriate
- Follow standard Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Maximum line length: 120 characters
- Use 4 spaces for indentation (no tabs)

### Code Structure

```java
package com.rcl.newclient.service;

import com.rcl.core.RestClient;
import com.rcl.core.model.HttpRequest;
import com.rcl.core.model.HttpResponse;

public class NewClientImpl implements RestClient {
    
    @Override
    public HttpResponse get(HttpRequest request) {
        // Implementation
    }
    
    // Other HTTP methods...
}
```

### Error Handling

- Use `RclException` for library-specific errors
- Wrap third-party exceptions appropriately
- Provide meaningful error messages

### Testing

- Write unit tests for all public methods
- Use JUnit 5 for testing
- Aim for >80% code coverage
- Include integration tests for HTTP operations

## 🔧 Project Structure

```
rcl/
├── core/                    # Core interfaces and models
│   ├── src/main/java/
│   └── src/test/java/
├── [client-name]/           # HTTP client implementation
│   ├── src/main/java/
│   └── src/test/java/
├── test/                    # Integration tests
└── rcl-all/                 # Aggregator module
```

## 📚 Documentation

### Code Documentation

- Use JavaDoc for public APIs
- Include usage examples in JavaDoc
- Document complex algorithms or business logic

### README Updates

When adding new features:
- Update feature list
- Add usage examples
- Update API reference if needed

## 🧪 Testing Guidelines

### Unit Tests

```java
@Test
void shouldReturnSuccessfulResponse() {
    // Given
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/test")
        .build();
    
    // When
    HttpResponse response = client.get(request);
    
    // Then
    assertEquals(200, response.getStatusCode());
    assertNotNull(response.getBody());
}
```

### Integration Tests

- Test against real HTTP endpoints when possible
- Use mock servers for consistent testing
- Test error scenarios and edge cases

## 🚦 Pull Request Guidelines

### Before Submitting

- [ ] Code follows project style guidelines
- [ ] Tests pass locally
- [ ] New functionality includes tests
- [ ] Documentation is updated
- [ ] Commit messages follow convention

### PR Description Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
```

## 🏷️ Commit Message Convention

Use conventional commits format:

```
type(scope): description

[optional body]

[optional footer]
```

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Test additions/modifications
- `chore`: Maintenance tasks

Examples:
```
feat(okhttp): add support for custom timeouts
fix(core): resolve null pointer in auth provider
docs(readme): update installation instructions
```

## 🤝 Code Review Process

1. **Automated checks** must pass (CI/CD pipeline)
2. **Peer review** by at least one maintainer
3. **Testing** in different environments if needed
4. **Documentation review** for user-facing changes

## 📞 Getting Help

- **GitHub Discussions**: General questions and ideas
- **GitHub Issues**: Bug reports and feature requests
- **Email**: [maintainer-email] for sensitive issues

## 🎉 Recognition

Contributors will be:
- Listed in the project's contributors section
- Mentioned in release notes for significant contributions
- Invited to join the maintainers team for consistent contributors

Thank you for contributing to RCL! 🚀