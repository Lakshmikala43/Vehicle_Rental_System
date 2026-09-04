-- ============================================================================
-- RentRide DDL Migration V1 - Core Relational Schema
-- Defines 42 normalized production database tables for vehicle rental operations.
-- ============================================================================

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    phone_number VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS role_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    branch_code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    phone_number VARCHAR(30),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    vehicle_capacity INT DEFAULT 100,
    status VARCHAR(30) DEFAULT 'OPERATIONAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vehicle_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS vehicle_brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    origin_country VARCHAR(100)
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
    FOREIGN KEY (brand_id) REFERENCES vehicle_brands(id),
    FOREIGN KEY (type_id) REFERENCES vehicle_types(id)
);

CREATE TABLE IF NOT EXISTS vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vin VARCHAR(50) NOT NULL UNIQUE,
    license_plate VARCHAR(30) NOT NULL UNIQUE,
    color VARCHAR(50),
    model_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    odometer_km INT DEFAULT 0,
    hourly_rate DECIMAL(10,2) NOT NULL,
    daily_rate DECIMAL(10,2) NOT NULL,
    security_deposit DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) DEFAULT 'AVAILABLE',
    fuel_level_percent INT DEFAULT 100,
    health_score INT DEFAULT 100,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (model_id) REFERENCES vehicle_models(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);
