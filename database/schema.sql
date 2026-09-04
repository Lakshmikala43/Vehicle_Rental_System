-- ============================================================================
-- RideFleet – Vehicle Rental & Fleet Management System
-- Database Schema Script (MySQL 8.0+ / H2 Compatible)
-- Total Entities: 42 Tables with Foreign Key Constraints & Indexes
-- ============================================================================

-- 1. USERS & RBAC MODULE
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    phone_number VARCHAR(20),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    module VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 2. USER PROFILES MODULE
CREATE TABLE IF NOT EXISTS customer_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    driver_license_number VARCHAR(50) NOT NULL,
    license_expiry_date DATE,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100) DEFAULT 'India',
    membership_tier VARCHAR(30) DEFAULT 'BRONZE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    branch_code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20),
    phone_number VARCHAR(30),
    email VARCHAR(100),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    vehicle_capacity INT DEFAULT 100,
    status VARCHAR(30) DEFAULT 'OPERATIONAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS staff_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    employee_code VARCHAR(50) NOT NULL UNIQUE,
    designation VARCHAR(100),
    department VARCHAR(100),
    branch_id BIGINT,
    shift_start TIME,
    shift_end TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS drivers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    badge_number VARCHAR(50) NOT NULL UNIQUE,
    license_category VARCHAR(50) NOT NULL,
    experience_years INT DEFAULT 1,
    rating DOUBLE PRECISION DEFAULT 5.0,
    availability_status VARCHAR(30) DEFAULT 'AVAILABLE',
    hourly_rate DECIMAL(10,2) DEFAULT 250.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS driver_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    document_url VARCHAR(255) NOT NULL,
    verification_status VARCHAR(30) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE
);

-- 3. VEHICLE CATALOG & FLEET MODULE
CREATE TABLE IF NOT EXISTS vehicle_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS vehicle_brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    origin_country VARCHAR(100),
    logo_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS vehicle_models (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    model_name VARCHAR(100) NOT NULL,
    model_year INT NOT NULL,
    seating_capacity INT NOT NULL,
    fuel_type VARCHAR(30) NOT NULL,
    transmission_type VARCHAR(30) NOT NULL,
    engine_capacity_cc INT,
    fuel_tank_capacity_l INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (brand_id) REFERENCES vehicle_brands(id),
    FOREIGN KEY (type_id) REFERENCES vehicle_types(id)
);

CREATE TABLE IF NOT EXISTS vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vin VARCHAR(50) NOT NULL UNIQUE,
    license_plate VARCHAR(30) NOT NULL UNIQUE,
    model_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    color VARCHAR(50),
    odometer_km INT NOT NULL DEFAULT 0,
    hourly_rate DECIMAL(10,2) NOT NULL,
    daily_rate DECIMAL(10,2) NOT NULL,
    security_deposit DECIMAL(10,2) NOT NULL DEFAULT 5000.00,
    status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    fuel_level_percent INT DEFAULT 100,
    health_score INT DEFAULT 100,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (model_id) REFERENCES vehicle_models(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

CREATE TABLE IF NOT EXISTS vehicle_features (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feature_code VARCHAR(50) NOT NULL UNIQUE,
    feature_name VARCHAR(100) NOT NULL,
    icon_name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS vehicle_feature_map (
    vehicle_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    PRIMARY KEY (vehicle_id, feature_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE,
    FOREIGN KEY (feature_id) REFERENCES vehicle_features(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vehicle_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vehicle_locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id BIGINT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    speed_kmh DOUBLE PRECISION DEFAULT 0.0,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vehicle_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id BIGINT NOT NULL,
    previous_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    reason VARCHAR(255),
    changed_by_user_id BIGINT,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);

-- 4. PRICING & COUPON ENGINE
CREATE TABLE IF NOT EXISTS pricing_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(100) NOT NULL,
    vehicle_type_id BIGINT,
    base_hourly_rate DECIMAL(10,2) NOT NULL,
    base_daily_rate DECIMAL(10,2) NOT NULL,
    weekend_multiplier DOUBLE PRECISION DEFAULT 1.15,
    surge_multiplier DOUBLE PRECISION DEFAULT 1.0,
    tax_percent DECIMAL(5,2) DEFAULT 18.00,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(id)
);

CREATE TABLE IF NOT EXISTS seasonal_pricing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    season_name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    price_multiplier DOUBLE PRECISION NOT NULL DEFAULT 1.2,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS coupons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount_type VARCHAR(20) NOT NULL DEFAULT 'PERCENTAGE', -- PERCENTAGE or FLAT
    discount_value DECIMAL(10,2) NOT NULL,
    min_booking_amount DECIMAL(10,2) DEFAULT 0.00,
    max_discount_amount DECIMAL(10,2),
    valid_from TIMESTAMP NOT NULL,
    valid_until TIMESTAMP NOT NULL,
    max_uses INT DEFAULT 1000,
    current_uses INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS coupon_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    booking_id BIGINT NOT NULL,
    discount_applied DECIMAL(10,2) NOT NULL,
    used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (coupon_id) REFERENCES coupons(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS add_ons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price_per_day DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- 5. BOOKINGS & RENTAL CONTRACTS MODULE
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_reference VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    pickup_branch_id BIGINT NOT NULL,
    return_branch_id BIGINT NOT NULL,
    pickup_date_time TIMESTAMP NOT NULL,
    return_date_time TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    base_amount DECIMAL(10,2) NOT NULL,
    add_ons_amount DECIMAL(10,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) NOT NULL,
    security_deposit DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    FOREIGN KEY (pickup_branch_id) REFERENCES branches(id),
    FOREIGN KEY (return_branch_id) REFERENCES branches(id)
);

CREATE TABLE IF NOT EXISTS booking_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    add_on_id BIGINT,
    item_description VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
    FOREIGN KEY (add_on_id) REFERENCES add_ons(id)
);

CREATE TABLE IF NOT EXISTS booking_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    previous_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    notes VARCHAR(255),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rental_agreements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    agreement_number VARCHAR(50) NOT NULL UNIQUE,
    booking_id BIGINT NOT NULL UNIQUE,
    customer_signature_hash VARCHAR(255),
    terms_accepted BOOLEAN DEFAULT TRUE,
    signed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    agreement_pdf_url VARCHAR(255),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS driver_assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL UNIQUE,
    driver_id BIGINT NOT NULL,
    assignment_status VARCHAR(30) DEFAULT 'ASSIGNED',
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (driver_id) REFERENCES drivers(id)
);

-- 6. PAYMENTS & INVOICES MODULE
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_reference VARCHAR(50) NOT NULL UNIQUE,
    booking_id BIGINT NOT NULL,
    payment_method VARCHAR(30) NOT NULL, -- CARD, UPI, CASH, WALLET
    payment_status VARCHAR(30) NOT NULL DEFAULT 'INITIATED',
    amount DECIMAL(10,2) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    gateway_txn_id VARCHAR(100) NOT NULL,
    response_code VARCHAR(30),
    response_message VARCHAR(255),
    payload_json TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS refunds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    refund_reference VARCHAR(50) NOT NULL UNIQUE,
    payment_id BIGINT NOT NULL,
    refund_amount DECIMAL(10,2) NOT NULL,
    reason VARCHAR(255),
    status VARCHAR(30) DEFAULT 'PROCESSED',
    processed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (payment_id) REFERENCES payments(id)
);

CREATE TABLE IF NOT EXISTS invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_number VARCHAR(50) NOT NULL UNIQUE,
    booking_id BIGINT NOT NULL UNIQUE,
    subtotal DECIMAL(10,2) NOT NULL,
    tax_total DECIMAL(10,2) NOT NULL,
    grand_total DECIMAL(10,2) NOT NULL,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    invoice_pdf_path VARCHAR(255),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- 7. OPERATIONS: INSPECTIONS, DAMAGE, MAINTENANCE & INSURANCE
CREATE TABLE IF NOT EXISTS vehicle_inspections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    inspection_type VARCHAR(20) NOT NULL, -- PICKUP or RETURN
    inspector_id BIGINT NOT NULL,
    odometer_reading INT NOT NULL,
    fuel_level_percent INT NOT NULL,
    overall_condition VARCHAR(30) NOT NULL,
    inspection_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    FOREIGN KEY (inspector_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS inspection_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_id BIGINT NOT NULL,
    component_name VARCHAR(100) NOT NULL, -- Tires, Brakes, Windshield, Scratch, Interior
    status VARCHAR(30) NOT NULL, -- OK, DAMAGED, NEEDS_ATTENTION
    remarks VARCHAR(255),
    FOREIGN KEY (inspection_id) REFERENCES vehicle_inspections(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS damage_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    damage_code VARCHAR(50) NOT NULL UNIQUE,
    vehicle_id BIGINT NOT NULL,
    booking_id BIGINT,
    inspection_id BIGINT,
    severity VARCHAR(30) NOT NULL, -- MINOR, MAJOR, SEVERE
    description TEXT NOT NULL,
    estimated_repair_cost DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) DEFAULT 'REPORTED',
    image_urls TEXT,
    reported_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS maintenance_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id BIGINT NOT NULL,
    service_type VARCHAR(100) NOT NULL,
    interval_km INT,
    interval_months INT,
    last_service_odometer INT,
    last_service_date DATE,
    next_due_odometer INT,
    next_due_date DATE,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS maintenance_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_order_number VARCHAR(50) NOT NULL UNIQUE,
    vehicle_id BIGINT NOT NULL,
    maintenance_type VARCHAR(30) NOT NULL, -- PREVENTIVE, CORRECTIVE
    description TEXT NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    vendor_name VARCHAR(150),
    start_date DATE NOT NULL,
    completion_date DATE,
    status VARCHAR(30) DEFAULT 'IN_PROGRESS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE TABLE IF NOT EXISTS maintenance_parts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    maintenance_id BIGINT NOT NULL,
    part_name VARCHAR(100) NOT NULL,
    part_number VARCHAR(50),
    quantity INT NOT NULL DEFAULT 1,
    unit_cost DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (maintenance_id) REFERENCES maintenance_records(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS insurance_policies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_number VARCHAR(100) NOT NULL UNIQUE,
    vehicle_id BIGINT NOT NULL UNIQUE,
    provider_name VARCHAR(150) NOT NULL,
    coverage_type VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    premium_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE TABLE IF NOT EXISTS insurance_claims (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_number VARCHAR(50) NOT NULL UNIQUE,
    policy_id BIGINT NOT NULL,
    damage_report_id BIGINT NOT NULL,
    claimed_amount DECIMAL(10,2) NOT NULL,
    approved_amount DECIMAL(10,2) DEFAULT 0.00,
    claim_status VARCHAR(30) DEFAULT 'SUBMITTED',
    filed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (policy_id) REFERENCES insurance_policies(id),
    FOREIGN KEY (damage_report_id) REFERENCES damage_reports(id)
);

-- 8. REVIEWS, SUPPORT & INTELLIGENCE MODULE
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    overall_rating INT NOT NULL CHECK (overall_rating BETWEEN 1 AND 5),
    cleanliness_rating INT CHECK (cleanliness_rating BETWEEN 1 AND 5),
    comfort_rating INT CHECK (comfort_rating BETWEEN 1 AND 5),
    service_rating INT CHECK (service_rating BETWEEN 1 AND 5),
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE TABLE IF NOT EXISTS support_tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_number VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    subject VARCHAR(200) NOT NULL,
    category VARCHAR(50) NOT NULL,
    priority VARCHAR(20) DEFAULT 'MEDIUM',
    status VARCHAR(20) DEFAULT 'OPEN',
    assigned_staff_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (assigned_staff_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS support_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    message_text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ticket_id) REFERENCES support_tickets(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS recommendation_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    recommendation_score DOUBLE PRECISION NOT NULL,
    recommended_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE TABLE IF NOT EXISTS prediction_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model_type VARCHAR(50) NOT NULL, -- DEMAND_FORECAST or MAINTENANCE_RISK
    target_id BIGINT NOT NULL,
    predicted_score DOUBLE PRECISION NOT NULL,
    prediction_metadata TEXT,
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action_type VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100) NOT NULL,
    entity_id BIGINT,
    details TEXT,
    ip_address VARCHAR(45),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS system_settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);
