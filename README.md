# RestAssured API Automation - Clean Architecture

This project follows Clean Architecture principles for better maintainability, testability, and scalability.

## Package Structure

```
src/main/java/com/restassured/
├── domain/                    # Domain Layer (Core Business Logic)
│   └── entities/             # Business Entities
│       ├── User.java
│       ├── LoginRequest.java
│       └── LoginResponse.java
├── infrastructure/           # Infrastructure Layer (External Concerns)
│   ├── config/              # Configuration
│   │   └── ConfigReader.java
│   ├── http/                # HTTP Client
│   │   └── BaseService.java
│   ├── services/            # Infrastructure Services
│   │   └── AuthService.java
│   └── utils/               # Utilities
│       └── TestDataGenerator.java
└── tests/                   # Test Layer
    ├── TestBase.java
    └── LoginAPITest.java
```

## Architecture Layers

### 1. Domain Layer (`domain/`)
- **Purpose**: Contains core business logic and entities
- **Contains**: Business entities, domain models
- **Dependencies**: No external dependencies

### 2. Infrastructure Layer (`infrastructure/`)
- **Purpose**: Handles external concerns like HTTP, configuration, utilities
- **Contains**: HTTP clients, configuration readers, utility classes
- **Dependencies**: Can depend on domain layer

### 3. Test Layer (`tests/`)
- **Purpose**: Contains test classes and test utilities
- **Contains**: Test classes, test base classes
- **Dependencies**: Can depend on all other layers

## Key Benefits

1. **Separation of Concerns**: Each layer has a specific responsibility
2. **Testability**: Easy to unit test each layer independently
3. **Maintainability**: Clear structure makes code easier to maintain
4. **Scalability**: Easy to add new features without affecting existing code
5. **Dependency Inversion**: High-level modules don't depend on low-level modules

## Usage

The project is organized to follow clean architecture principles where:
- Domain entities are pure business objects
- Infrastructure handles external concerns
- Tests are organized in their own layer
- Dependencies flow inward toward the domain layer 