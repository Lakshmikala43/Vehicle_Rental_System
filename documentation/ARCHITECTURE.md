# RideFleet – Enterprise Architecture & System Specifications

## 1. EXECUTIVE ARCHITECTURE OVERVIEW

RideFleet is an enterprise-grade vehicle rental and fleet management platform engineered to support end-to-end vehicle lifecycles across five independent developer domains:

```
[Customer Identity & Booking Portal] ───► [Dynamic Pricing Engine & Coupon Shield] ───► [Payment Gateway Simulation]
                                                                                               │
[Fleet Board Telemetry Map] ◄─── [Visual Damage Inspection & Operations] ◄──────────────────────┘
```

The system architecture follows a clean multi-module Spring Boot pattern with strict domain isolation:

1. `com.ridefleet.auth` – JWT Authentication, Spring Security Filter Pipeline, RBAC Authorization.
2. `com.ridefleet.customer` – Customer Profile Management, Driver License Validation, Wishlist, Reviews.
3. `com.ridefleet.branch` – Multi-branch Operational Network, Vehicle Allocation, Branch Relocation.
4. `com.ridefleet.vehicle` – Catalog Engine, Dynamic JPA Specifications, Health Scoring, Image Asset Management.
5. `com.ridefleet.booking` – Double-Booking Collision Prevention Engine, Booking State Machine, Contract Agreements.
6. `com.ridefleet.pricing` – Pricing Engine (Base Rates + Hourly/Daily Rules + Weekend Surge 1.15x + GST 18% - Coupon Deduction).
7. `com.ridefleet.payment` – Offline Gateway Simulator (Card/UPI/Cash/Wallet), Bank Decline Rules, Tax Invoicing.
8. `com.ridefleet.inspection` – Pickup/Return Check-in, Interactive Visual Car SVG Component Damage Inspector.
9. `com.ridefleet.maintenance` – Work Order Management, Technician Assignment, Spare Parts Inventory, Insurance Claims.
10. `com.ridefleet.driver` – Driver Roster, Rating System, Duty Assignment.
11. `com.ridefleet.fleet` – HTML5 Canvas Telemetry Telematics Stream.
12. `com.ridefleet.intelligence` – Local Machine Learning / Statistical Recommendation, Demand Forecasting, Maintenance Risk Scoring.
13. `com.ridefleet.analytics` – Revenue KPI Analytics, Fleet Utilization, CSV Export Engine.
14. `com.ridefleet.audit` – Transactional Audit Logging & System Settings.

---

## 2. SECURITY PIPELINE & ROLE-BASED ACCESS CONTROL (RBAC)

Spring Security is configured statelessly via JWT tokens signed with HMAC-SHA256:

- `ROLE_CUSTOMER`: Access to `/api/bookings/**`, `/api/payments/**`, customer dashboard.
- `ROLE_STAFF`: Access to `/api/inspection/**`, `/api/bookings/status`, branch check-ins.
- `ROLE_FLEET_MANAGER`: Access to `/api/fleet/**`, vehicle status modifications, telemetry stream.
- `ROLE_ADMIN`: Access to `/api/admin/**`, `/api/analytics/**`, `/api/maintenance/**`, user role assignments.

---

## 3. DOUBLE-BOOKING COLLISION PREVENTION ENGINE

The collision shield enforces temporal non-overlap at the database query level:

```sql
SELECT COUNT(b.id) FROM bookings b
WHERE b.vehicle_id = :vehicleId
  AND b.status IN ('CONFIRMED', 'ACTIVE', 'READY_FOR_PICKUP', 'RESERVED')
  AND b.pickup_date_time < :returnDateTime
  AND b.return_date_time > :pickupDateTime;
```

If count > 0, `BookingService` rejects the reservation request immediately with a `BadRequestException` ("Vehicle is already booked for the selected time window!").

---

## 4. DYNAMIC PRICING FORMULA

Total rental cost calculation formula:

\[
\text{Total} = \left( \text{BaseRate} \times \text{Duration} \times \text{WeekendSurge} + \sum \text{AddOns} - \text{Discount} \right) \times (1 + \text{Tax}) + \text{Deposit}
\]

Where:
- `WeekendSurge` = 1.15x (applied if rental period touches Saturday or Sunday).
- `Tax` = 18.0% GST.
- `Deposit` = Refundable Security Deposit (e.g. ₹5,000).
