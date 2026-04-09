# Reservation System - Spring Boot REST API

A backend REST API for managing resource reservations, built with Spring Boot.
Features JWT-based authentication, role-based access control, and pessimistic locking
to safely handle concurrent booking requests.

---

## Tech Stack

| Layer       | Technology                      |
|-------------|---------------------------------|
| Language    | Java 17                         |
| Framework   | Spring Boot 4.0.5               |
| Security    | Spring Security 6 + JWT 0.11.5  |
| Persistence | Spring Data JPA + Hibernate     |
| Database    | MySQL                           |
| Validation  | Jakarta Bean Validation         |
| Boilerplate | Lombok                          |
| Build       | Maven                           |

---

## Project Structure

    src/main/java/com/ReservationSystem/
    ├── Controller/
    │   ├── AuthController.java
    │   └── ReservationController.java
    ├── Service/
    │   ├── AuthService.java
    │   ├── ReservationService.java
    │   └── CustomUserDetailsService.java
    ├── Security/
    │   ├── JwtUtil.java
    │   ├── JwtFilter.java
    │   └── SecurityConfig.java
    ├── Model/
    │   ├── User.java
    │   ├── Resource.java
    │   ├── Reservation.java
    │   └── Role.java
    ├── Repository/
    │   ├── UserRepository.java
    │   ├── ResourceRepository.java
    │   └── ReservationRepository.java
    ├── Dto/
    │   ├── LoginRequest.java
    │   ├── RegisterRequest.java
    │   ├── AuthResponse.java
    │   ├── CreateReservationRequest.java
    │   └── ReservationResponse.java
    ├── Mapper/
    │   └── ReservationMapper.java
    └── Exception/
        └── GlobalExceptionHandler.java

---

## Setup and Installation

### Prerequisites
- Java 17+
- Maven
- MySQL

### 1. Clone the repository

    git clone https://github.com/nicolastique/ReservationSystem.git
    cd ReservationSystem

### 2. Create the database

    CREATE DATABASE reservation_db;

### 3. Configure credentials

    cp src/main/resources/application.properties.example src/main/resources/application.properties

Edit the file with your MySQL credentials:

    spring.datasource.url=jdbc:mysql://localhost:3306/reservation_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password

### 4. Insert a test resource

    INSERT INTO resources (name, type) VALUES ('Sala A', 'SALA');

### 5. Run the application

    ./mvnw spring-boot:run

The API will be available at http://localhost:8080

---

## Endpoints

| Method | Endpoint       | Auth Required | Description             |
|--------|----------------|---------------|-------------------------|
| POST   | /auth/register | No            | Register a new user     |
| POST   | /auth/login    | No            | Login and get JWT token |
| POST   | /reservations  | Yes           | Create a reservation    |

---

## Authentication

### Register

    POST /auth/register
    Content-Type: application/json

    {
      "name": "Nicolas",REST API for resource reservation management built with Spring Boot, JWT authentication, role-based access control and pessimistic locking for concurrent booking safety.
      "email": "nicolas@gmail.com",
      "password": "yourpassword"
    }

### Login

    POST /auth/login
    Content-Type: application/json

    {
      "email": "nicolas@gmail.com",
      "password": "yourpassword"
    }

Response:

    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }

---

## Creating a Reservation

    POST /reservations
    Authorization: Bearer <token>
    Content-Type: application/json

    {
      "resourceId": 1,
      "startDate": "2026-05-01T10:00:00",
      "endDate": "2026-05-01T12:00:00"
    }

Success response (200):

    {
      "id": 1,
      "userId": 2,
      "resourceId": 1,
      "startDate": "2026-05-01T10:00:00",
      "endDate": "2026-05-01T12:00:00"
    }

Conflict response (400):

    "Resource already booked for that time range"

---

## Concurrency Handling

When two users try to book the same resource at the same time, the system uses
pessimistic locking (PESSIMISTIC_WRITE) at the database level:

1. The first request locks the resource rows while checking for conflicts.
2. The second request waits up to 3 seconds for the lock to be released.
3. Once the first request commits, the second checks again and correctly detects the conflict.

This prevents double booking under high concurrency without relying on
application-level synchronization.

---

## Security Design

- All /auth/** endpoints are public.
- All other endpoints require a valid JWT token.
- The authenticated user is extracted from the token, never from the request body,
  preventing users from impersonating others.
- Roles are stored in the database (USER, ADMIN) and encoded into the JWT.

---

## Notes

- Passwords are stored in plain text for development purposes.
  For production, migrate to BCryptPasswordEncoder.
- The JWT secret key should be moved to environment variables before going to production.

---

## Author
Nicolas Perez - Backend Developer