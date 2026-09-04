# RideFleet REST API Specification & Payload Documentation

## Authentication Endpoints (`/api/auth`)

### 1. User Authentication (Login)
- **Endpoint**: `POST /api/auth/login`
- **Request Body**:
```json
{
  "username": "customer",
  "password": "customer123"
}
```
- **Response Payload (200 OK)**:
```json
{
  "success": true,
  "message": "User authenticated successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "customer",
    "email": "customer@ridefleet.com",
    "fullName": "Ananya Roy",
    "roles": ["CUSTOMER"]
  }
}
```

### 2. Customer Registration
- **Endpoint**: `POST /api/auth/register`
- **Request Body**:
```json
{
  "username": "newdriver",
  "email": "driver@example.com",
  "password": "password123",
  "fullName": "Rahul Sharma",
  "phoneNumber": "+919876543210",
  "driverLicenseNumber": "DL-KA01-20269876",
  "role": "CUSTOMER"
}
```

---

## Vehicle Catalog Endpoints (`/api/vehicles`)

### 1. Dynamic Search & Filter Vehicles
- **Endpoint**: `GET /api/vehicles?category=SUV&fuelType=DIESEL&transmission=AUTOMATIC&status=AVAILABLE`
- **Response Payload (200 OK)**:
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "vin": "RFVIN10000001",
      "licensePlate": "KA-01-RF-1001",
      "color": "Graphite Black",
      "odometerKm": 14200,
      "hourlyRate": 200.00,
      "dailyRate": 2900.00,
      "securityDeposit": 5000.00,
      "status": "AVAILABLE",
      "fuelLevelPercent": 95,
      "healthScore": 98,
      "model": {
        "modelName": "Innova Crysta VX",
        "modelYear": 2024,
        "seatingCapacity": 7,
        "fuelType": "DIESEL",
        "transmissionType": "AUTOMATIC",
        "brand": { "name": "Toyota" },
        "type": { "category": "SUV" }
      }
    }
  ]
}
```

---

## Booking & Reservation Endpoints (`/api/bookings`)

### 1. Reserve Vehicle
- **Endpoint**: `POST /api/bookings`
- **Headers**: `Authorization: Bearer <jwt_token>`
- **Request Body**:
```json
{
  "vehicleId": 1,
  "pickupBranchId": 1,
  "returnBranchId": 1,
  "pickupDateTime": "2026-09-10T10:00:00",
  "returnDateTime": "2026-09-12T18:00:00",
  "addOnIds": [1, 3],
  "couponCode": "WELCOME10"
}
```

---

## Payment Gateway Simulation Endpoints (`/api/payments`)

### 1. Process Payment Simulation
- **Endpoint**: `POST /api/payments/process`
- **Request Body**:
```json
{
  "bookingId": 1,
  "paymentMethod": "CARD",
  "amount": 11985.60,
  "cardNumber": "4532892178431092"
}
```

---

## Maintenance & Inspection Endpoints (`/api/maintenance` & `/api/inspection`)

### 1. Log Visual Damage Assessment
- **Endpoint**: `POST /api/inspection/damage`
- **Request Body**:
```json
{
  "vehicle": { "id": 1 },
  "severity": "MAJOR",
  "estimatedRepairCost": 4500.00,
  "description": "Scratch observed on front hood right side during customer return inspection."
}
```

### 2. Complete Maintenance Work Order
- **Endpoint**: `PATCH /api/maintenance/work-orders/2/complete`
