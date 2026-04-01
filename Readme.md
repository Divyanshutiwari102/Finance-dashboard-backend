# Finance Data Processing and Access Control Dashboard (Spring Boot)

A clean, production-ready backend for financial record management with JWT authentication, role-based access control, and dashboard analytics.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL 8
- Swagger / OpenAPI (springdoc)
- Maven
- Docker + Docker Compose

---

## Project Structure

```text
finance-dashboard-backend/
├─ pom.xml
├─ README.md
├─ Dockerfile
├─ docker-compose.yml
├─ postman/
│  └─ Finance-Dashboard-API.postman_collection.json
└─ src/
   └─ main/
      ├─ java/
      │  └─ org/com/example/finance/...
      └─ resources/
         └─ application.properties
```

---

## Features

- JWT-based authentication (`/api/auth/login`)
- Role-based authorization:
    - `ADMIN`
    - `ANALYST`
    - `VIEWER`
- User management (admin only)
- Financial records CRUD
- Filtered + paginated record listing
- Dashboard summary analytics
- Validation + centralized error handling

---

## Default Seed Users

- **ADMIN**: `admin@finance.com` / `Admin@123`
- **ANALYST**: `analyst@finance.com` / `Analyst@123`
- **VIEWER**: `viewer@finance.com` / `Viewer@123`

---

## Run Locally (Without Docker)

### 1) Create database
You can either create DB manually:

```sql
CREATE DATABASE finance_dashboard;
```

or let app create it (if configured in datasource URL).

### 2) Update `application.properties`
Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_dashboard?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3) Build and run

```bash
mvn clean install
mvn spring-boot:run
```

### 4) Open Swagger UI
- `http://localhost:8080/swagger-ui.html`
- If redirected, use: `http://localhost:8080/swagger-ui/index.html`

---

## Run with Docker

### Build and start

```bash
docker compose up --build
```

### Services
- App: `http://localhost:8080`
- MySQL mapped on host: `localhost:3307`

### Stop

```bash
docker compose down
```

---

## Authentication

### Login
`POST /api/auth/login`

```json
{
  "email": "admin@finance.com",
  "password": "Admin@123"
}
```

Use returned token in header for protected APIs:

```http
Authorization: Bearer <token>
```

---

## Role Access Matrix

| Endpoint Type | ADMIN | ANALYST | VIEWER |
|---|---:|---:|---:|
| Auth Login | ✅ | ✅ | ✅ |
| User Management | ✅ | ❌ | ❌ |
| Record Create/Update/Delete | ✅ | ❌ | ❌ |
| Record Read | ✅ | ✅ | ✅ |
| Dashboard Summary | ✅ | ✅ | ✅ |

> Unauthorized role access returns **403 Forbidden**.

---

## API Endpoints

### Auth
- `POST /api/auth/login`
- `POST /api/auth/register` *(recommended: ADMIN-only in production)*

### Users (ADMIN only)
- `POST /api/users`
- `GET /api/users`
- `PATCH /api/users/{id}/status`
- `PATCH /api/users/{id}/role`

### Financial Records
- `POST /api/records` (ADMIN)
- `PUT /api/records/{id}` (ADMIN)
- `DELETE /api/records/{id}` (ADMIN)
- `GET /api/records/{id}` (ADMIN/ANALYST/VIEWER)
- `GET /api/records?from=&to=&category=&type=&page=&size=` (ADMIN/ANALYST/VIEWER)

### Dashboard
- `GET /api/dashboard/summary` (ADMIN/ANALYST/VIEWER)

---

## Sample Record Payload

```json
{
  "amount": 2500,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "Salary credited"
}
```

---

## Postman

Import collection from:

- `postman/Finance-Dashboard-API.postman_collection.json`

Collection variables:
- `baseUrl = http://localhost:8080`
- `token = ""`
- `userId = 1`
- `recordId = 1`

---

## Error Handling

Global exception response format:

```json
{
  "timestamp": "2026-04-01T17:25:23.810636600Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/records/1",
  "validationErrors": null
}
```

---

## Notes

- DTO-based request/response
- Bean validation enabled
- Centralized exception handling
- BCrypt password hashing
- SQL logging configurable via `application.properties`

---