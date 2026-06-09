# Architecture Base

## Goal

This project uses a simple Spring Boot REST architecture. The goal is to keep the code easy to understand, test, and evolve without adding heavy patterns.

## Package Structure

The code should live under `matheus.dev.habito` and follow these packages:

- `config`: framework configuration such as Jackson, CORS, OpenAPI and general beans.
- `security`: authentication, JWT, filters, security configuration and access control.
- `controller`: REST endpoints and HTTP request/response handling.
- `service`: application rules, transactions and orchestration.
- `repository`: Spring Data JPA repositories.
- `entity`: JPA entities and domain enums persisted in the database.
- `dto`: request and response contracts exposed by the API.
- `mapper`: conversion between DTOs and entities.
- `exception`: custom exceptions for domain and application errors.
- `handler`: global exception handling and standardized error responses.
- `validation`: custom validations when Jakarta Validation is not enough.

## Feature Organization

Inside the layers above, organize code by feature when the project grows, for example:

- `auth`
- `usuario`
- `habito`
- `conclusao`
- `dashboard`

This keeps the project modular without introducing extra architectural complexity.

## Naming Rules

- Packages: lowercase, singular when possible, no accents.
- Classes: `PascalCase`.
- Controllers: end with `Controller`.
- Services: end with `Service`.
- Repositories: end with `Repository`.
- Exceptions: end with `Exception`.
- DTOs: end with `DTO`.
- Methods: `lowerCamelCase` and start with a verb.
- Variables and parameters: `lowerCamelCase`, descriptive, no abbreviations.
- Constants: `UPPER_SNAKE_CASE`.
- Booleans: semantic names such as `ativo`, `concluido`, `temMeta`.

## Design Rules

- Controllers stay thin and do not hold business rules.
- Services contain business rules and transactional behavior.
- Repositories do not contain business logic.
- Entities represent persistence, not the API contract.
- DTOs are the public API boundary.
- Mappers isolate conversion logic.
- Exceptions are converted in a global handler.
- Security is stateless with JWT.

## Quality Rules

- Use `Spotless` for automatic formatting.
- Use `Checkstyle` for naming and style rules.
- Keep validation in the build so style issues fail early.
- Prefer tests for controller, service, repository and security layers.

## Current Scope

The current domain is centered on:

- user registration and login
- habit creation and editing
- daily completion tracking
- weekly dashboard metrics