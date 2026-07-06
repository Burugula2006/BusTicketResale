<div align="center">

# 🚌 (BUS SWAP) Bus Ticket Resale Platform

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=flat-square&logo=spring-security&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=flat-square&logo=mysql&logoColor=white)
![HTML](https://img.shields.io/badge/Frontend-HTML%2FCSS%2FJS-E34F26?style=flat-square&logo=html5&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue?style=flat-square)
![Build](https://img.shields.io/badge/build-passing-brightgreen?style=flat-square)

> **A full-stack Bus Ticket Resale Marketplace** built with Spring Boot and Vanilla JS — implementing **JWT Authentication**, **Role-Based Access Control (BUYER/SELLER)**, and an **automated ticket expiry scheduler** to manage the complete lifecycle of resale tickets.

</div>

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Folder Structure](#-folder-structure)
- [Database Schema](#-database-schema)
- [API Endpoints](#-api-endpoints)
- [Installation](#-installation)
- [Running the Backend](#-running-the-backend)
- [Running the Frontend](#-running-the-frontend)
- [Database Configuration](#-database-configuration)
- [Screenshots](#-screenshots)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🚀 Project Overview

The **Bus Ticket Resale Platform** is a full-stack marketplace application where users can register as either a **Seller** or a **Buyer** and trade bus tickets. Sellers can list their unused tickets for resale; Buyers can browse, search, and contact sellers directly via WhatsApp.

The system is designed to be:
- 🔐 **Secure** — JWT-based stateless authentication with role-based access control enforced at the API level
- 👥 **Role-aware** — BUYER and SELLER roles have completely separate permissions enforced by Spring Security
- ⏰ **Self-maintaining** — a scheduled job automatically expires tickets whose travel date has passed
- 🛡️ **Robust** — global exception handling with consistent error responses across all endpoints

---

## ✨ Features

### 🔧 Backend

| Feature | Description |
|---|---|
| 🔐 JWT Authentication | Stateless token-based auth — token generated on login, validated on every request |
| 👥 Role-Based Access Control | BUYER and SELLER roles enforced via Spring Security — not just frontend UI |
| 🎟 Ticket Management | Full CRUD — create, update, delete, mark as sold |
| 🔍 Ticket Search | Search tickets by fromCity, toCity, and travelDate |
| 📊 Seller Dashboard | Per-seller stats — total listings, available, and sold counts |
| ⏰ Ticket Expiry Scheduler | Runs every 60 seconds — auto-expires tickets past their travel datetime |
| 🛡️ Global Exception Handling | Consistent error responses for 400, 401, 403, 404, 500 |
| 🔄 Duplicate Prevention | Blocks duplicate ticket listings by route + operator + seat combination |
| 📖 Swagger / OpenAPI Docs | Auto-generated interactive API documentation |

### 🎨 Frontend

| Feature | Description |
|---|---|
| 🔀 Role-Based UI | Seller and Buyer dashboards are completely separate after login |
| 🎟 Seller Dashboard | List tickets, edit price/contact, mark as sold, delete listings |
| 🔍 Buyer Browse | Browse all available tickets and search by route and date |
| 💬 WhatsApp Contact | One-click WhatsApp deep link with pre-filled message to contact seller |
| 📊 Stats Dashboard | Seller-only stats showing total, available, and sold ticket counts |
| 👤 Profile Page | View account details and account status for both roles |

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT (Browser)                         │
│                    Vanilla HTML + CSS + JS                      │
│              Role-Based UI (BUYER view / SELLER view)           │
└──────────────────────────────┬──────────────────────────────────┘
                               │ HTTP + Bearer JWT Token
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                     SPRING BOOT BACKEND                         │
│                                                                 │
│  ┌───────────────┐  ┌─────────────────┐  ┌──────────────────┐  │
│  │ AuthController│  │TicketController │  │ UserController   │  │
│  └──────┬────────┘  └────────┬────────┘  └────────┬─────────┘  │
│         └───────────────────┬┘                    │             │
│                             ▼                     │             │
│         ┌───────────────────────────────────────────────────┐   │
│         │            Service Layer (Business Logic)          │   │
│         │  AuthService  TicketService  UserService           │   │
│         │              DashboardService                      │   │
│         └───────────────────┬───────────────────────────────┘   │
│                             │                                    │
│  ┌──────────────────────────▼──────────────────────────────┐    │
│  │              Spring Security Filter Chain                │    │
│  │   JwtAuthenticationFilter → Role Validation             │    │
│  └──────────────────────────┬──────────────────────────────┘    │
│                             │                                    │
│  ┌──────────────────────────▼──────────────────────────────┐    │
│  │              Spring Data JPA / Hibernate                 │    │
│  └──────────────────────────┬──────────────────────────────┘    │
│                             │                                    │
│  ┌──────────────────────────▼──────────────────────────────┐    │
│  │         TicketExpiryScheduler (every 60 seconds)         │    │
│  │         Auto-expires past-travel-date tickets            │    │
│  └─────────────────────────────────────────────────────────┘    │
└──────────────────────────────┬──────────────────────────────────┘
                               │ JDBC
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                          MySQL Database                         │
│             ┌──────────────┐    ┌──────────────┐               │
│             │    users     │    │   tickets    │               │
│             └──────────────┘    └──────────────┘               │
└─────────────────────────────────────────────────────────────────┘
```

### 🔄 JWT Authentication Flow

```
User Login (email + password)
           │
           ▼
┌─────────────────────────┐
│  AuthServiceImpl        │
│  Validate credentials   │
│  Load user from DB      │
└──────────┬──────────────┘
           ▼
┌─────────────────────────┐
│  JwtService             │
│  generateToken(email,   │
│  role)                  │
└──────────┬──────────────┘
           ▼
  Return JWT token + role
           │
           ▼
┌─────────────────────────┐
│  Every subsequent call  │
│  Authorization: Bearer  │
│  <token>                │
└──────────┬──────────────┘
           ▼
┌─────────────────────────┐      ┌──────────────────────┐
│  JwtAuthenticationFilter│─YES─►│  SecurityConfig      │
│  Token valid?           │      │  Role check passes?  │──YES──► Controller
└──────────┬──────────────┘      └──────────┬───────────┘
           │ NO                             │ NO
           ▼                               ▼
     401 Unauthorized              403 Forbidden
```

### 🔄 Ticket Expiry Scheduler Flow

```
Every 60 seconds
      │
      ▼
Fetch all AVAILABLE tickets
      │
      ▼
For each ticket:
  travelDateTime = travelDate + travelTime
      │
      ▼
┌─────────────────────────┐       ┌───────────────────┐
│  travelDateTime < now?  │──YES─►│  status = EXPIRED │──► Save to DB
└──────────┬──────────────┘       └───────────────────┘
           │ NO
           ▼
     Leave as AVAILABLE
```

---

## 🛠️ Tech Stack

### Backend

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Core language |
| Spring Boot | 4.0.6 | Application framework |
| Spring Security | — | JWT auth + role-based access |
| Spring Data JPA | — | ORM & database abstraction |
| jjwt (JJWT) | — | JWT token generation & validation |
| MySQL | 8.x | Persistent storage |
| Lombok | — | Boilerplate reduction |
| Maven | — | Build & dependency management |
| Swagger / OpenAPI | — | API documentation |

### Frontend

| Technology | Purpose |
|---|---|
| HTML5 | Page structure and layout |
| CSS3 | Dark theme, role-based colors, animations |
| Vanilla JavaScript | API calls, role routing, DOM manipulation |
| WhatsApp Web API | Deep link for buyer-seller contact |

---

## 📁 Folder Structure

```
bus-ticket-resale-backend/
│
├── src/main/java/com/notes/busticketresalebackend/
│   │
│   ├── config/
│   │   ├── JwtAuthenticationFilter.java     # Intercepts every request, validates JWT
│   │   └── SecurityConfig.java              # Role-based endpoint rules + CORS
│   │
│   ├── controller/
│   │   ├── AuthController.java              # POST /api/auth/register, /login
│   │   ├── TicketController.java            # CRUD + search + mark sold
│   │   ├── DashboardController.java         # GET /api/dashboard/stats
│   │   └── UserController.java             # GET /api/users/profile, /my-listings
│   │
│   ├── dto/
│   │   ├── request/
│   │   │   ├── LoginRequest.java
│   │   │   ├── RegisterRequest.java         # includes Role field
│   │   │   ├── TicketCreateRequest.java
│   │   │   └── TicketUpdateRequest.java
│   │   └── response/
│   │       ├── ApiResponse.java
│   │       ├── AuthResponse.java            # token + email + role
│   │       ├── DashboardStatsResponse.java
│   │       ├── TicketResponse.java
│   │       └── UserProfileResponse.java
│   │
│   ├── entity/
│   │   ├── User.java                        # id, name, email, role, accountStatus
│   │   ├── Ticket.java                      # full ticket entity with seller FK
│   │   ├── Role.java                        # enum: BUYER, SELLER
│   │   ├── TicketStatus.java                # enum: AVAILABLE, SOLD, EXPIRED
│   │   └── AccountStatus.java               # enum: ACTIVE, RESTRICTED, BLOCKED
│   │
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java      # handles 400, 401, 403, 404, 500
│   │   ├── BadRequestException.java
│   │   ├── ResourceNotFoundException.java
│   │   └── UnauthorizedException.java
│   │
│   ├── repository/
│   │   ├── TicketRepository.java            # custom queries: findByStatus, search
│   │   └── UserRepository.java
│   │
│   ├── scheduler/
│   │   └── TicketExpiryScheduler.java       # @Scheduled every 60s — auto-expire
│   │
│   ├── security/
│   │   ├── CustomUserDetailsService.java    # loads user + role for Spring Security
│   │   └── JwtService.java                  # generate + extract + validate JWT
│   │
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── TicketService.java
│   │   ├── DashboardService.java
│   │   ├── UserService.java
│   │   └── impl/
│   │       ├── AuthServiceImpl.java
│   │       ├── TicketServiceImpl.java
│   │       ├── DashboardServiceImpl.java
│   │       └── UserServiceImpl.java
│   │
│   └── BusTicketResaleBackendApplication.java
│
├── src/main/resources/
│   └── application.properties
│
├── frontend/
│   └── busswap.html                         # Complete single-file frontend
│
└── pom.xml
```

---

## 🗄️ Database Schema

### `users`

| Column | Type | Description |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated primary key |
| `name` | VARCHAR | Full name of the user |
| `email` | VARCHAR (UNIQUE) | Login email |
| `password` | VARCHAR | BCrypt-hashed password |
| `phone_number` | VARCHAR | Contact number |
| `role` | ENUM | `BUYER` or `SELLER` |
| `warning_count` | INT | Number of warnings (default 0) |
| `account_status` | ENUM | `ACTIVE`, `RESTRICTED`, `BLOCKED` |
| `created_at` | DATETIME | Auto-set on insert |
| `updated_at` | DATETIME | Auto-set on update |

### `tickets`

| Column | Type | Description |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated primary key |
| `from_city` | VARCHAR | Departure city |
| `to_city` | VARCHAR | Destination city |
| `travel_date` | DATE | Date of travel |
| `travel_time` | TIME | Time of travel |
| `bus_operator` | VARCHAR | e.g. VRL, Orange Travels |
| `bus_type` | VARCHAR | Sleeper, AC Seater, Volvo etc. |
| `seat_number` | VARCHAR | e.g. A1, B12 |
| `original_price` | DOUBLE | Original ticket price |
| `resale_price` | DOUBLE | Seller's asking price |
| `contact_number` | VARCHAR | Seller's contact for buyers |
| `description` | VARCHAR | Optional notes from seller |
| `status` | ENUM | `AVAILABLE`, `SOLD`, `EXPIRED` |
| `seller_id` | BIGINT (FK) | References `users.id` |
| `created_at` | DATETIME | Auto-set on insert |
| `updated_at` | DATETIME | Auto-set on update |

---

## 📡 API Endpoints

### Base URL: `http://localhost:8080`

#### 🔓 Public Endpoints (no token required)

| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register new user | `{ name, email, password, phoneNumber, role }` |
| `POST` | `/api/auth/login` | Login and get JWT | `{ email, password }` |

#### 🎟 Ticket Endpoints (BUYER + SELLER)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/tickets` | Both | Get all available tickets |
| `GET` | `/api/tickets/search` | Both | Search by fromCity, toCity, travelDate |

#### 🎟 Ticket Endpoints (SELLER only)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/tickets` | SELLER | List a new ticket |
| `PUT` | `/api/tickets/{id}` | SELLER | Update resalePrice, contact, description |
| `PATCH` | `/api/tickets/{id}/sold` | SELLER | Mark ticket as sold |
| `DELETE` | `/api/tickets/{id}` | SELLER | Delete a listing |

#### 👤 User Endpoints (authenticated)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/users/profile` | Both | Get current user profile |
| `GET` | `/api/users/my-listings` | SELLER | Get all listings by current seller |
| `GET` | `/api/dashboard/stats` | SELLER | Get total/available/sold counts |

---

### Example Requests & Responses

<details>
<summary><b>POST /api/auth/register — Register a Seller</b></summary>

**Request:**
```json
{
  "name": "Raghavendra",
  "email": "ragha@gmail.com",
  "password": "pass@123",
  "phoneNumber": "+91 9999999999",
  "role": "SELLER"
}
```

**Response `200 OK`:**
```json
{
  "message": "User registered successfully"
}
```
</details>

<details>
<summary><b>POST /api/auth/login — Login</b></summary>

**Request:**
```json
{
  "email": "ragha@gmail.com",
  "password": "pass@123"
}
```

**Response `200 OK`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "message": "Login successful",
  "email": "ragha@gmail.com",
  "role": "SELLER"
}
```
</details>

<details>
<summary><b>POST /api/tickets — List a Ticket (SELLER only)</b></summary>

**Request:**
```json
{
  "fromCity": "Hyderabad",
  "toCity": "Bangalore",
  "travelDate": "2026-06-20",
  "travelTime": "21:00:00",
  "busOperator": "Orange Travels",
  "busType": "AC Sleeper",
  "seatNumber": "A10",
  "originalPrice": 1200.0,
  "resalePrice": 950.0,
  "contactNumber": "+91 9999999999",
  "description": "Change of plans, urgent sale"
}
```

**Response `200 OK`:**
```json
{
  "message": "Ticket listed successfully"
}
```
</details>

<details>
<summary><b>GET /api/tickets/search — Search Tickets</b></summary>

**Request:**
```
GET /api/tickets/search?fromCity=Hyderabad&toCity=Bangalore&travelDate=2026-06-20
Authorization: Bearer <token>
```

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "fromCity": "Hyderabad",
    "toCity": "Bangalore",
    "travelDate": "2026-06-20",
    "travelTime": "21:00:00",
    "busOperator": "Orange Travels",
    "busType": "AC Sleeper",
    "seatNumber": "A10",
    "resalePrice": 950.0,
    "contactNumber": "+91 9999999999",
    "description": "Change of plans, urgent sale",
    "status": "AVAILABLE",
    "sellerName": "Raghavendra"
  }
]
```
</details>

<details>
<summary><b>POST /api/tickets/{id}/sold — Mark as Sold (SELLER only)</b></summary>

**Request:**
```
PATCH /api/tickets/1/sold
Authorization: Bearer <token>
```

**Response `200 OK`:**
```json
{
  "message": "Ticket marked as sold"
}
```
</details>

> 📖 Full interactive docs at **`http://localhost:8080/swagger-ui.html`** once the backend is running.

---

## 🔧 Installation

### Prerequisites

- ✅ [Java 21+](https://openjdk.org/)
- ✅ [Maven 3.8+](https://maven.apache.org/)
- ✅ [MySQL 8.x](https://www.mysql.com/)
- ✅ Any modern browser (Chrome, Firefox, Edge)

### Clone the Repository

```bash
git clone https://github.com/Burugula2006/bus-ticket-resale-backend.git
cd bus-ticket-resale-backend
```

---

## ▶️ Running the Backend

**1. Create the database:**
```sql
CREATE DATABASE bus_ticket_resale_db;
```

**2. Update `src/main/resources/application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bus_ticket_resale_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8080
```

**3. Build and run:**
```bash
mvn clean install
mvn spring-boot:run
```

Backend starts at **`http://localhost:8080`** · Swagger UI at **`http://localhost:8080/swagger-ui.html`**

---

## 🌐 Running the Frontend

No build step required — it's a single HTML file.

1. Open `frontend/busswap.html` in your browser
2. Make sure your Spring Boot backend is running on port `8080`
3. Register as a **Seller** or **Buyer** and start using the platform

> ⚠️ Ensure the backend is running before opening the frontend.

---

## 🗃️ Database Configuration

Spring Boot with `ddl-auto=update` auto-creates tables on first run. To verify:

```sql
USE bus_ticket_resale_db;
SHOW TABLES;
-- tickets
-- users
```

---

## 📸 Screenshots

> _Add screenshots of your application here._

| Auth Page | Seller Dashboard | Buyer Browse |
|:---:|:---:|:---:|
| ![Auth](screenshots/auth.png) | ![Seller](screenshots/seller-dashboard.png) | ![Buyer](screenshots/buyer-browse.png) |

---

## 🔮 Future Enhancements

| Enhancement | Description |
|---|---|
| 🔔 Notifications | Email/SMS alert to seller when a buyer contacts them |
| 💳 Payment Integration | In-app payment via Razorpay or Stripe |
| ⭐ Seller Ratings | Buyers can rate sellers after successful transactions |
| 🐳 Docker Support | Containerize backend + MySQL with `docker-compose` |
| ♻️ Refresh Tokens | Auto-refresh JWT without forcing re-login |
| 🔍 Advanced Filters | Filter by bus type, price range, seat preference |
| 📱 React Frontend | Migrate frontend to React for component-based architecture |

---

## 🤝 Contributing

1. Fork the repository
2. Create a branch: `git checkout -b feature/your-feature-name`
3. Commit: `git commit -m 'feat: add some feature'`
4. Push: `git push origin feature/your-feature-name`
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

⭐ **If you found this project helpful, consider giving it a star!**

Built with ❤️ using Spring Boot & Vanilla JS

[![GitHub](https://img.shields.io/badge/GitHub-Burugula2006-181717?style=flat-square&logo=github)](https://github.com/Burugula2006)

</div>
