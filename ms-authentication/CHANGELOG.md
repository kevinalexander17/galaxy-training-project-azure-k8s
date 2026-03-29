# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- JWT-based authentication system with role-based access control
- User registration and login endpoints with validation
- Centralized message constants for better maintainability
- Request/response logging filter with sensitive data protection
- H2 in-memory database for development and testing
- OpenAPI/Swagger documentation integration
- Password encryption using BCrypt
- MapStruct for DTO-Entity mapping

### Fixed
- `HttpMessageNotReadableException` caused by LoggingFilter consuming request stream
- JWT token structure to use UUID as primary subject identifier
- Proper request/response body caching for logging purposes

### Changed
- Refactored authentication flow to use centralized constants
- Updated JWT claims structure for better security and consistency
- Improved error handling and response formatting

## [0.0.1] - 2026-03-29

### Added
- Initial Spring Boot project structure
- Basic authentication microservice setup
- Security configuration with JWT support
- User entity with role-based permissions
- Database schema for users and roles
- Global exception handling
- Comprehensive logging system

### Security
- JWT token-based authentication
- Password hashing with BCrypt
- Role-based access control (ADMIN, CLIENT)
- Request/response logging with sensitive data redaction
- CSRF protection disabled for API endpoints
- Stateless session management

### Technical Details
- **Java Version**: 25
- **Spring Boot**: 3.5.12
- **Build Tool**: Gradle 8.x
- **Database**: H2 (in-memory)
- **Security**: Spring Security 6.x
- **Documentation**: SpringDoc OpenAPI 3.0

---

## Migration Guide

### From 0.0.1 to Unreleased
No breaking changes introduced. All existing API endpoints remain compatible.

### JWT Token Structure Update
The JWT token structure was updated to include userId as the primary subject:

**Before:**
```json
{
  "sub": "username",
  "role": "ADMIN"
}
```

**After:**
```json
{
  "sub": "user-uuid",
  "userName": "username", 
  "role": "ADMIN"
}
```

This change improves security by using UUID as the primary identifier while maintaining backward compatibility through the `userName` claim.
