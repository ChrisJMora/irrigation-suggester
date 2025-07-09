
/*
 * Agricultural Management System Database
 *
 * This script creates a complete database structure for an agricultural management system
 * with modules for administration, human resources, and agricultural operations.
 *
 * Database: demo_db
 * Schemas: admin, human_resources, agriculture
 */

-- =============================================
-- SCHEMA CREATION
-- =============================================

--DROP SCHEMA IF EXISTS admin CASCADE;
DROP SCHEMA IF EXISTS human_resources CASCADE;
DROP SCHEMA IF EXISTS agriculture CASCADE;

CREATE SCHEMA admin;
CREATE SCHEMA human_resources;
CREATE SCHEMA agriculture;

-- =============================================
-- ADMIN SCHEMA (User Management)
-- =============================================

DROP TABLE IF EXISTS human_resources.employee;
--DROP TABLE IF EXISTS admin.app_user;

--CREATE TABLE admin.app_user (
--    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    user_name VARCHAR(255) NOT NULL,
--    user_password VARCHAR(60) NOT NULL,
--    user_type VARCHAR(50) NOT NULL,
--    user_email VARCHAR(255) NOT NULL
--);
--
--ALTER TABLE admin.app_user
--    ADD CONSTRAINT chk_app_user_type CHECK (user_type IN ('Administrator', 'Supervisor'));
--
--CREATE UNIQUE INDEX idx_app_user_username ON admin.app_user (user_name);
--CREATE UNIQUE INDEX idx_app_user_email ON admin.app_user (user_email);
--
--ALTER TABLE admin.app_user
--    ADD CONSTRAINT chk_app_user_username_length CHECK (CHAR_LENGTH(user_name) > 0);
--
--ALTER TABLE admin.app_user
--    ADD CONSTRAINT chk_app_user_email_length CHECK (CHAR_LENGTH(user_email) > 0);
--
--ALTER TABLE admin.app_user
--    ADD CONSTRAINT chk_app_user_password_length CHECK (CHAR_LENGTH(user_password) > 0);

-- =============================================
-- HUMAN RESOURCES SCHEMA (Employee Management)
-- =============================================

CREATE TABLE human_resources.employee (
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    employee_first_name VARCHAR(255) NOT NULL,
    employee_last_name VARCHAR(255)
);

ALTER TABLE human_resources.employee
    ADD CONSTRAINT chk_employee_first_name_length CHECK (CHAR_LENGTH(employee_first_name) > 0);

ALTER TABLE human_resources.employee
    ADD CONSTRAINT chk_employee_last_name_length CHECK (CHAR_LENGTH(employee_last_name) > 0);

ALTER TABLE human_resources.employee
    ADD CONSTRAINT fk_employee_app_user FOREIGN KEY (user_id) REFERENCES admin.app_user (user_id);

-- =============================================
-- AGRICULTURE SCHEMA (Agricultural Operations)
-- =============================================

DROP TABLE IF EXISTS agriculture.schedule;
DROP TABLE IF EXISTS agriculture.suggested_schedule;
DROP TABLE IF EXISTS agriculture.sensor_record;
DROP TABLE IF EXISTS agriculture.sensor;
DROP TABLE IF EXISTS agriculture.sprinkler;
DROP TABLE IF EXISTS agriculture.forecast;
DROP TABLE IF EXISTS agriculture.crop;
DROP TABLE IF EXISTS agriculture.location;
DROP TABLE IF EXISTS agriculture.soil;

CREATE TABLE agriculture.location (
    location_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_latitude REAL NOT NULL,
    location_longitude REAL NOT NULL
);

CREATE TABLE agriculture.soil (
    soil_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    soil_name VARCHAR(255) NOT NULL,
    soil_water_retention REAL NOT NULL
);

ALTER TABLE agriculture.soil
    ADD CONSTRAINT chk_soil_water_retention_positive CHECK (soil_water_retention > 0);

CREATE TABLE agriculture.crop (
    crop_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_name VARCHAR(255) NOT NULL,
    crop_water_required REAL NOT NULL,
    crop_root_height REAL NOT NULL,
    location_id BIGINT NOT NULL,
    soil_id BIGINT NOT NULL
);

ALTER TABLE agriculture.crop
    ADD CONSTRAINT chk_crop_water_required_positive CHECK (crop_water_required > 0);

ALTER TABLE agriculture.crop
    ADD CONSTRAINT fk_crop_location FOREIGN KEY (location_id) REFERENCES agriculture.location (location_id);

ALTER TABLE agriculture.crop
    ADD CONSTRAINT fk_crop_soil FOREIGN KEY (soil_id) REFERENCES agriculture.soil (soil_id);

CREATE TABLE agriculture.sensor (
    sensor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_id BIGINT NOT NULL
);

ALTER TABLE agriculture.sensor
    ADD CONSTRAINT fk_sensor_crop FOREIGN KEY (crop_id) REFERENCES agriculture.crop (crop_id);

CREATE TABLE agriculture.sensor_record (
    sensor_record_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sensor_record_humidity REAL NOT NULL,
    sensor_record_date DATE NOT NULL,
    sensor_record_time TIME NOT NULL,
    sensor_id BIGINT NOT NULL
);

ALTER TABLE agriculture.sensor_record
    ADD CONSTRAINT chk_sensor_humidity_range CHECK (sensor_record_humidity > 0 AND sensor_record_humidity <= 100);

ALTER TABLE agriculture.sensor_record
    ADD CONSTRAINT fk_sensor_record_sensor FOREIGN KEY (sensor_id) REFERENCES agriculture.sensor (sensor_id);

CREATE TABLE agriculture.sprinkler (
    sprinkler_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sprinkler_type_irrigation VARCHAR(255) NOT NULL,
    sprinkler_caudal REAL NOT NULL,
    sprinkler_status VARCHAR(255) NOT NULL,
    crop_id BIGINT NOT NULL
);

ALTER TABLE agriculture.sprinkler
    ADD CONSTRAINT fk_sprinkler_crop FOREIGN KEY (crop_id) REFERENCES agriculture.crop (crop_id);

CREATE TABLE agriculture.forecast (
    forecast_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    forecast_probability REAL NOT NULL,
    forecast_precipitation SMALLINT NOT NULL,
    forecast_date DATE NOT NULL,
    location_id BIGINT NOT NULL
);

ALTER TABLE agriculture.forecast
    ADD CONSTRAINT chk_forecast_precipitation_range CHECK (forecast_precipitation >= 0 AND forecast_precipitation <= 100);

ALTER TABLE agriculture.forecast
    ADD CONSTRAINT fk_forecast_location FOREIGN KEY (location_id) REFERENCES agriculture.location (location_id);

CREATE TABLE agriculture.schedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    schedule_date DATE NOT NULL,
    schedule_start TIME NOT NULL,
    schedule_end TIME NOT NULL,
    schedule_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    schedule_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    schedule_status VARCHAR(255) NOT NULL DEFAULT 'Pending',
    crop_id BIGINT NOT NULL
);

ALTER TABLE agriculture.schedule
    ADD CONSTRAINT fk_schedule_crop FOREIGN KEY (crop_id) REFERENCES agriculture.crop (crop_id);

CREATE TABLE agriculture.suggested_schedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    schedule_delay_time TIME NOT NULL,
    schedule_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    schedule_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    schedule_status VARCHAR(255) NOT NULL DEFAULT 'Pending',
    crop_id BIGINT NOT NULL
);

ALTER TABLE agriculture.suggested_schedule
    ADD CONSTRAINT fk_suggested_schedule_crop FOREIGN KEY (crop_id) REFERENCES agriculture.crop (crop_id);