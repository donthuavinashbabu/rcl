---
name: Bug report
about: Create a report to help us improve
title: '[BUG] '
labels: 'bug'
assignees: ''

---

**Describe the bug**
A clear and concise description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Create RestClient with '...'
2. Make request to '....'
3. See error

**Expected behavior**
A clear and concise description of what you expected to happen.

**Code Sample**
```java
// Minimal code sample that reproduces the issue
RestClient client = RestClientFactory.create(RestClientType.OKHTTP).getClient();
HttpRequest request = HttpRequest.builder()
    .url("https://example.com/api")
    .build();
HttpResponse response = client.get(request);
```

**Error Message/Stack Trace**
```
Paste the full error message and stack trace here
```

**Environment (please complete the following information):**
 - RCL Version: [e.g. 1.0.0]
 - Java Version: [e.g. 17, 21]
 - HTTP Client: [e.g. OkHttp, Apache HttpClient]
 - OS: [e.g. Windows 11, Ubuntu 20.04, macOS 12]
 - Framework: [e.g. Spring Boot 3.2, Quarkus 3.5]

**Additional context**
Add any other context about the problem here.

**Checklist**
- [ ] I have searched existing issues to ensure this is not a duplicate
- [ ] I have provided a minimal code sample that reproduces the issue
- [ ] I have included the full error message and stack trace
- [ ] I have specified my environment details