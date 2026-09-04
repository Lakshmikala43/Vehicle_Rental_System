# RentRide – Enterprise Vehicle Rental & Fleet Management System

> **Enterprise-Grade Vehicle Rental & Fleet Lifecycle Operations Platform**
> Built with Java 17, Spring Boot 3, REST APIs, JPA/Hibernate, MySQL/H2 DB, HTML5, CSS Grid/Flexbox, and Modular JavaScript.

---

## Installation

To install dependencies and set up the project locally:

```bash
# 1. Clone the repository
git clone https://github.com/org/rentride-fleet.git
cd rentride-fleet

# 2. Install Frontend Node Dependencies & Lockfile
npm install

# 3. Install Backend Java Maven Dependencies
cd backend
mvn clean install -DskipTests
cd ..
```

---

## Build

To compile and build the complete application:

```bash
# Build Java Backend WAR/JAR artifact
cd backend
mvn clean package -DskipTests
cd ..

# Build Docker Container Image
docker build -t rentride:latest .
```

Alternatively, use the Makefile:
```bash
make build
```

---

## Run

To launch the application locally:

### Option A: Standard Local Execution
```bash
# Start Web Server on Port 3000
npm start
# Or using python directly:
# python -m http.server 3000 --directory frontend
```
Then navigate your browser to **`http://localhost:3000`**.

### Option B: Spring Boot Backend + Frontend Execution
```bash
# Terminal 1: Run Spring Boot Backend (Port 8080)
cd backend && mvn spring-boot:run

# Terminal 2: Run Frontend Server (Port 3000)
python -m http.server 3000 --directory frontend
```

### Option C: Docker Container Execution
```bash
docker run -p 8080:8080 -p 3000:3000 rentride:latest
```

---

## Dependencies

The project relies on the following core dependencies:

### Backend Dependencies
- **Java 17+** (JDK)
- **Spring Boot 3.2.0**
- **Spring Boot Starter Web** (REST API controllers)
- **Spring Boot Starter Data JPA** (Hibernate ORM & Repositories)
- **Spring Boot Starter Security** (JWT Authentication & RBAC)
- **Spring Boot Starter Validation** (Bean Validation `@NotNull`, `@Min`)
- **H2 Database Engine** (In-memory dev database) / **MySQL 8.0 Driver**
- **JotHM / JJWT 0.11.5** (JSON Web Tokens)

### Frontend Dependencies
- **HTML5 & Modern CSS3** (CSS Grid, Flexbox, Custom Variables)
- **Modular ES6+ JavaScript** (Fetch API, HTML5 Canvas Telemetry Engine)

---

## Usage

### Demo Accounts & Credentials

| Role Portal | Username | Password | Target Dashboard |
| :--- | :--- | :--- | :--- |
| **Customer** | `customer` | `customer123` | Customer Rental Dashboard (`customer-dashboard.html`) |
| **Staff / Ops** | `staff` | `staff123` | Staff Operations Portal (`staff-portal.html`) |
| **Fleet Manager** | `fleetmgr` | `fleet123` | Fleet Board & Telemetry (`fleet-board.html`) |
| **Admin Executive** | `admin` | `admin123` | Executive Admin Control Panel (`admin-dashboard.html`) |

### Key Features
1. **Customer Portal**: Vehicle search, date collision prevention shield, add-on selection, promo coupon engine, payment simulation, and tax invoicing.
2. **Staff Operations Portal**: Check-in / check-out processing, visual SVG vehicle damage inspector.
3. **Admin Executive Portal**: Platform financial analytics, maintenance work order issuance, mark completed workflows, and CSV export.
4. **Fleet Telemetry Map**: HTML5 Canvas visual board showing branch nodes and moving vehicle telemetry markers.
