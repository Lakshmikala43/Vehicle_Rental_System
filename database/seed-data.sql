-- ============================================================================
-- RideFleet – Seed Data Script (MySQL 8.0+ / H2 Compatible)
-- Inserts realistic sample data for branches, roles, permissions, vehicles,
-- customers, drivers, staff, bookings, pricing rules, and support tickets.
-- ============================================================================

-- 1. ROLES & PERMISSIONS SEED
INSERT INTO roles (id, name, description) VALUES
(1, 'ADMIN', 'System Administrator with full enterprise privileges'),
(2, 'STAFF', 'Branch Operations Employee'),
(3, 'FLEET_MANAGER', 'Fleet Allocation & Telemetry Manager'),
(4, 'CUSTOMER', 'Standard Customer Account');

-- 2. SAMPLE BRANCHES SEED
INSERT INTO branches (id, branch_code, name, address, city, state, postal_code, phone_number, latitude, longitude, vehicle_capacity, status) VALUES
(1, 'BR-BLR-01', 'Central Metro Hub', 'MG Road, Brigade Junction', 'Bangalore', 'Karnataka', '560001', '+918022001122', 12.9716, 77.5946, 120, 'OPERATIONAL'),
(2, 'BR-BLR-02', 'Airport Terminal 2 Branch', 'KIA Terminal 2 Pickup Hub', 'Bangalore', 'Karnataka', '560300', '+918022003344', 13.1986, 77.7066, 200, 'OPERATIONAL'),
(3, 'BR-HYD-01', 'Gachibowli Financial Hub', 'Financial District Main Rd', 'Hyderabad', 'Telangana', '500032', '+914022005566', 17.4401, 78.3489, 150, 'OPERATIONAL'),
(4, 'BR-MUM-01', 'Bandra Kurla Hub', 'BKC G Block, Bandra East', 'Mumbai', 'Maharashtra', '400051', '+912222007788', 19.0600, 72.8680, 180, 'OPERATIONAL');

-- 3. VEHICLE CATEGORIES & TYPES
INSERT INTO vehicle_types (id, code, name, category, description) VALUES
(1, 'SUV', 'Sports Utility Vehicle', 'SUV', 'Spacious 7-seater for executive highway travel'),
(2, 'SEDAN', 'Executive Premium Sedan', 'Sedan', 'Comfortable 5-seater with luxury leather interior'),
(3, 'EV', 'Electric Vehicle', 'Electric', 'Zero-emission electric vehicle with fast charging support'),
(4, 'LUXURY', 'Performance Luxury', 'Luxury', 'High-performance luxury vehicles for premium occasions'),
(5, 'HATCHBACK', 'City Compact Hatchback', 'Hatchback', 'Agile city hatchback for easy parking');

-- 4. VEHICLE BRANDS
INSERT INTO vehicle_brands (id, name, origin_country) VALUES
(1, 'Toyota', 'Japan'),
(2, 'BMW', 'Germany'),
(3, 'Tesla', 'USA'),
(4, 'Hyundai', 'South Korea'),
(5, 'Tata Motors', 'India'),
(6, 'Honda', 'Japan'),
(7, 'Mercedes-Benz', 'Germany'),
(8, 'Mahindra', 'India');

-- 5. VEHICLE MODELS
INSERT INTO vehicle_models (id, brand_id, type_id, model_name, model_year, seating_capacity, fuel_type, transmission_type) VALUES
(1, 1, 1, 'Innova Crysta VX', 2024, 7, 'DIESEL', 'AUTOMATIC'),
(2, 2, 4, '5 Series 530i', 2023, 5, 'PETROL', 'AUTOMATIC'),
(3, 3, 3, 'Model 3 Long Range', 2024, 5, 'ELECTRIC', 'AUTOMATIC'),
(4, 4, 1, 'Creta SX(O)', 2024, 5, 'PETROL', 'MANUAL'),
(5, 5, 3, 'Nexon EV Empowered+', 2024, 5, 'ELECTRIC', 'AUTOMATIC'),
(6, 6, 2, 'City ZX', 2023, 5, 'PETROL', 'AUTOMATIC'),
(7, 7, 4, 'E-Class E200', 2024, 5, 'PETROL', 'AUTOMATIC'),
(8, 8, 1, 'Thar LX Hard Top', 2024, 4, 'DIESEL', 'AUTOMATIC');

-- 6. ADD-ONS & COUPONS SEED
INSERT INTO add_ons (id, code, name, description, price_per_day, is_active) VALUES
(1, 'ADD_CHILD_SEAT', 'Infant Safety Child Seat', 'ISOFIX certified premium child safety seat', 350.00, TRUE),
(2, 'ADD_GPS_HW', 'Portable GPS Navigation Unit', 'Standalone high-precision GPS tracker', 200.00, TRUE),
(3, 'ADD_INS_ZERO', 'Zero-Depreciation Insurance Shield', 'Complete coverage with zero customer liability', 650.00, TRUE),
(4, 'ADD_ROADSIDE', '24/7 Roadside Emergency Assistance', 'Priority towing & battery jumpstart support', 150.00, TRUE);

INSERT INTO coupons (id, code, discount_type, discount_value, min_booking_amount, max_discount_amount, valid_from, valid_until, max_uses, current_uses, is_active) VALUES
(1, 'WELCOME10', 'PERCENTAGE', 10.00, 1000.00, 500.00, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 1000, 42, TRUE),
(2, 'FESTIVE20', 'PERCENTAGE', 20.00, 3000.00, 1000.00, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 500, 18, TRUE),
(3, 'FREEDOM500', 'FLAT', 500.00, 2000.00, 500.00, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 2000, 105, TRUE);
