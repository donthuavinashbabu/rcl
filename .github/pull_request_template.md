# Pull Request

## Description
Brief description of the changes in this PR.

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Code refactoring
- [ ] Test improvements

## Related Issues
Fixes #(issue number)
Closes #(issue number)
Related to #(issue number)

## Changes Made
- [ ] Added new HTTP client implementation: [client name]
- [ ] Enhanced existing functionality: [description]
- [ ] Fixed bug in: [component/module]
- [ ] Updated documentation
- [ ] Added/updated tests
- [ ] Other: [description]

## HTTP Client Modules Affected
- [ ] Core
- [ ] OkHttp
- [ ] Apache HttpClient
- [ ] Retrofit
- [ ] WebClient
- [ ] Feign
- [ ] RestTemplate
- [ ] All modules (rcl-all)

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed
- [ ] All existing tests pass

### Test Coverage
- [ ] New code is covered by tests
- [ ] Test coverage maintained/improved
- [ ] Edge cases tested

## Code Quality
- [ ] Code follows project style guidelines
- [ ] Self-review of code completed
- [ ] Code is properly documented (JavaDoc)
- [ ] No new compiler warnings
- [ ] Performance impact considered

## Documentation
- [ ] README updated (if needed)
- [ ] API documentation updated
- [ ] Examples updated/added
- [ ] CHANGELOG updated

## Breaking Changes
If this PR introduces breaking changes, please describe:
- What breaks?
- Migration path for users
- Deprecation notices added

## Performance Impact
- [ ] No performance impact
- [ ] Performance improved
- [ ] Performance impact acceptable
- [ ] Performance benchmarks included

## Security Considerations
- [ ] No security implications
- [ ] Security review completed
- [ ] Sensitive data handling reviewed
- [ ] Authentication/authorization changes reviewed

## Deployment Notes
Any special deployment considerations or steps required.

## Screenshots/Examples
If applicable, add screenshots or code examples demonstrating the changes.

```java
// Example usage of new feature
RestClient client = RestClientFactory.create(RestClientType.OKHTTP).getClient();
// ... rest of example
```

## Checklist
- [ ] I have performed a self-review of my code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes
- [ ] Any dependent changes have been merged and published

## Additional Notes
Any additional information that reviewers should know.