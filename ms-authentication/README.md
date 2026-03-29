# MS Authentication Microservice

A robust Spring Boot microservice providing JWT-based authentication and authorization with role-based access control.

## 🚀 Features

- **JWT Authentication**: Secure token-based authentication with configurable expiration
- **Role-Based Access Control**: Support for ADMIN and CLIENT roles
- **Password Security**: BCrypt encryption for secure password storage
- **Request Logging**: Comprehensive logging with sensitive data protection
- **API Documentation**: Integrated OpenAPI/Swagger documentation
- **Validation**: Input validation using Jakarta Bean Validation
- **Exception Handling**: Centralized error handling with consistent response format
- **Database**: H2 in-memory database with JPA/Hibernate

## 📋 Prerequisites

- **Java 25** or higher
- **Gradle 8.x** or higher
- **Git** for version control

## 🛠️ Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/kevinalexander17/galaxy-training-project-azure-k8s.git
cd galaxy-training-project-azure-k8s/ms-authentication
```

### 2. Build the Application
```bash
./gradlew build
```

### 3. Run the Application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 4. Access API Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 📚 API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "userName": "john.doe",
  "password": "securePassword123",
  "role": "CLIENT"
}
```

#### Login User
```http
POST /api/auth/login
Content-Type: application/json

{
  "userName": "john.doe",
  "password": "securePassword123"
}
```

### Response Format
All API responses follow a consistent format:

```json
{
  "success": true,
  "message": "User registered successfully",
  "timestamp": "2026-03-29T12:00:00.000000",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

## 🔐 Security Configuration

### JWT Token Structure
```json
{
  "sub": "user-uuid",
  "userName": "john.doe",
  "role": "CLIENT",
  "iat": 1774806588,
  "exp": 1774809288
}
```

### Using Protected Endpoints
```http
GET /api/protected-endpoint
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Security Headers
- **X-Frame-Options**: Disabled (required for H2 console)
- **Session Management**: Stateless
- **CSRF Protection**: Disabled (API-focused)

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE kc_user (
    user_uuid VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id VARCHAR(255) UNIQUE,
    enabled BOOLEAN NOT NULL,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6)
);
```

### Roles Table
```sql
CREATE TABLE kc_role (
    role_uuid VARCHAR(255) PRIMARY KEY,
    name ENUM('ADMIN','CLIENT') UNIQUE NOT NULL,
    enabled BOOLEAN NOT NULL,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6)
);
```

## ⚙️ Configuration

### Application Properties
```properties
# Server Configuration
server.port=8080

# JWT Configuration
security.jwt.secret=your-secret-key-here
security.jwt.expiration=3600000  # 1 hour in milliseconds

# Database Configuration (H2)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Integration Tests
```bash
./gradlew integrationTest
```

### Test Coverage
```bash
./gradlew jacocoTestReport
```

## 📝 Development Best Practices

### Code Quality
- **Lombok**: Reduces boilerplate code with annotations
- **MapStruct**: Type-safe mapping between DTOs and entities
- **Validation**: Input validation using Jakarta Bean Validation
- **Constants**: Centralized constants for messages and configuration

### Security Best Practices
- **Password Hashing**: Never store plain text passwords
- **JWT Security**: Use strong secret keys and reasonable expiration
- **Input Validation**: Validate all user inputs
- **Error Handling**: Don't expose sensitive information in error messages
- **Logging**: Log requests/responses but redact sensitive data

### API Design
- **RESTful**: Follow REST principles
- **Consistent Responses**: Use standardized response format
- **HTTP Status Codes**: Use appropriate HTTP status codes
- **Versioning**: Plan for API versioning
- **Documentation**: Keep API documentation updated

## 🔧 Development Tools

### IDE Configuration
- **Recommended IDE**: IntelliJ IDEA or VS Code
- **Java Version**: 25
- **Code Style**: Follow Google Java Style Guide
- **Imports**: Organize imports and remove unused imports

### Build Tools
```bash
# Clean build
./gradlew clean build

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Generate dependency tree
./gradlew dependencies
```

## 🚀 Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t ms-authentication:latest .

# Run container
docker run -p 8080:8080 ms-authentication:latest
```

### Kubernetes Deployment
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-authentication
spec:
  replicas: 3
  selector:
    matchLabels:
      app: ms-authentication
  template:
    metadata:
      labels:
        app: ms-authentication
    spec:
      containers:
      - name: ms-authentication
        image: ms-authentication:latest
        ports:
        - containerPort: 8080
```

## 🐛 Troubleshooting

### Common Issues

#### JWT Token Not Working
- Check the secret key configuration
- Verify token expiration
- Ensure proper Authorization header format

#### Database Connection Issues
- Verify H2 database configuration
- Check if database is running
- Review connection string

#### Logging Issues
- Ensure logging level is appropriate
- Check if sensitive data is being properly redacted
- Verify log file permissions

### Health Checks
```bash
# Application Health
curl http://localhost:8080/actuator/health

# Application Info
curl http://localhost:8080/actuator/info
```

## 📊 Monitoring

### Application Metrics
- **Spring Boot Actuator**: Built-in metrics and health checks
- **Custom Metrics**: Request/response logging with timing
- **Performance**: Monitor JWT token generation and validation

### Logging
- **Request Logging**: Full request/response logging
- **Security Logging**: Authentication and authorization events
- **Error Logging**: Comprehensive error tracking

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Review Checklist
- [ ] Code follows project style guidelines
- [ ] Tests are included and passing
- [ ] Documentation is updated
- [ ] Security implications are considered
- [ ] Performance impact is assessed

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For questions and support:
- **Issues**: Create an issue on GitHub
- **Documentation**: Check the API documentation
- **Examples**: Review the test cases for usage examples

---

**Note**: This microservice is part of a larger microservices architecture. Ensure proper service discovery and configuration management when deploying in a distributed environment.
