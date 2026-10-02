# Blog Application

A Spring Boot-based blog API with authentication, PostgreSQL persistence, JWT security, and Dockerized local development support.

## Overview

This project is a Java application built with Spring Boot and Maven. It exposes REST endpoints for blog-related features such as authentication, categories, posts, and tags, and it uses PostgreSQL as its primary database.

## Tech Stack

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- Flyway
- Lombok
- MapStruct
- JWT (jjwt)
- Docker / Docker Compose
- Maven Wrapper

## Features

- User authentication with JWT
- Blog post management
- Category management
- Tag management
- Database migrations with Flyway
- Dockerized PostgreSQL setup
- Default seeded test user for local development

## Project Structure

- `src/main/java/dev/ahmed/blog/config` — application configuration and security setup
- `src/main/java/dev/ahmed/blog/controllers` — REST controllers
- `src/main/java/dev/ahmed/blog/domain` — DTOs, entities, and domain enums
- `src/main/java/dev/ahmed/blog/mappers` — mapping logic
- `src/main/java/dev/ahmed/blog/repositories` — JPA repositories
- `src/main/java/dev/ahmed/blog/security` — security and JWT logic
- `src/main/java/dev/ahmed/blog/services` — business logic
- `src/main/resources/db` — Flyway migration scripts
- `src/main/resources/application.yaml` — application configuration

## Prerequisites

Before running the project, make sure you have:

- Java 25
- Maven or the included Maven Wrapper
- Docker and Docker Compose

## Local Setup

1. Clone the repository
2. Start the database container:

```bash
docker compose up -d
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

The project is configured to connect to PostgreSQL at:

- Host: `localhost`
- Port: `5400`
- Database: `mydatabase`
- Username: `myuser`
- Password: `mysecretpassword`

## Default Development User

A test user is created automatically in the security configuration:

- Email: `user@test.com`
- Password: `password`

## API Endpoints

### Authentication

- `POST /api/v1/auth/login`

Example request body:

```json
{
  "email": "user@test.com",
  "password": "password"
}
```

### Public Read Endpoints

- `GET /api/v1/categories/**`
- `GET /api/v1/posts/**`
- `GET /api/v1/tags/**`

All other routes require authentication.

## Security

The application uses stateless JWT-based authentication. Security configuration allows unauthenticated access only to login and public read endpoints, while all other routes require valid authentication.

## Database

The app uses PostgreSQL and Flyway. Flyway migration scripts are located under:

```text
src/main/resources/db
```

## Docker Compose

The repository includes a `compose.yml` file that starts:

- PostgreSQL database
- Adminer UI on port `8888`

Adminer can be accessed at:

```text
http://localhost:8888
```

## Configuration

The main application configuration is in:

```text
src/main/resources/application.yaml
```

Important configuration includes:

- Datasource connection details
- Hibernate settings
- JWT secret configuration

## License

This project does not currently declare a license in the repository metadata.
