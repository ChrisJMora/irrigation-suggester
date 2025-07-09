-- =============================================
-- SAMPLE DATA INSERTION
-- =============================================
/*
 * This script populates the database with sample data for demonstration purposes.
 * Includes sample application users, employees, locations, soil types, crops, and irrigation schedules.
 *
 * Note: Passwords are hashed using BCrypt algorithm ($2a$10$...)
 */

---- =============================================
---- ADMINISTRATIVE DATA (Application User Accounts)
---- =============================================
--
--/**
-- * Insert sample system application users with hashed passwords
-- * Password format: BCrypt ($2a$10$...)
-- *
-- * Sample passwords (all hashed to "password123"):
-- */
--INSERT INTO admin.app_user (
--    user_name,
--    user_password,
--    user_type,
--    user_email
--) VALUES
--    ('admin1', '$2a$10$dgiyqtCrdY9rhF6xFX1HZuZYpI8rADBvHk.L.wr3kukpplXK7Fs7y', 'Administrator', 'admin1@example.com'),
--    ('supervisor1', '$2a$10$eCm0QEK0.ZWGX4G3ku1YGeY7614OfV9ZHC9EB9dzs5lNf1RP8qLyu', 'Supervisor', 'supervisor1@example.com'),
--    ('admin2', '$2a$10$PefC2T7LOoZ6P3K3i3dCk.ocUMQVzANWPt0MnDkcjQDHgLs0/2m0.', 'Administrator', 'admin2@example.com');

-- =============================================
-- HUMAN RESOURCES DATA (Employee Records)
-- =============================================

/**
 * Insert sample employee records
 * Each employee is linked to an application user account
 */
INSERT INTO human_resources.employee (
    user_id,
    employee_first_name,
    employee_last_name
) VALUES
    (1, 'Xavier', 'Moya'),
    (2, 'Julio', 'Romo'),
    (3, 'Emily', 'Vargas');

-- =============================================
-- AGRICULTURAL DATA
-- =============================================

-- -------------------------
-- GEOGRAPHICAL LOCATIONS
-- -------------------------
/**
 * Insert sample farm locations with coordinates
 * Coordinates represent major US cities for demonstration
 */
INSERT INTO agriculture.location (
    location_latitude,
    location_longitude
) VALUES
    (34.0522, -118.2437),  -- Los Angeles, CA
    (40.7128, -74.0060),   -- New York, NY
    (37.7749, -122.4194);  -- San Francisco, CA

-- -------------------------
-- SOIL TYPES
-- -------------------------
/**
 * Insert common soil types with water retention values
 * Water retention values are approximate percentages
 */
INSERT INTO agriculture.soil (
    soil_name,
    soil_water_retention
) VALUES
    ('Sandy', 0.15),  -- Low water retention
    ('Loamy', 0.30),  -- Moderate water retention
    ('Clay', 0.40);   -- High water retention

-- -------------------------
-- CROP INFORMATION
-- -------------------------
/**
 * Insert sample crops with their water requirements
 * Each crop is assigned to a location and soil type
 */
INSERT INTO agriculture.crop (
    crop_name,
    crop_water_required,  -- In inches per week
    crop_root_height,     -- In meters
    location_id,
    soil_id
) VALUES
    ('Tomato', 0.5, 0.6, 1, 2),
    ('Lettuce', 0.3, 0.3, 2, 1),
    ('Carrot', 0.4, 0.4, 3, 3),
    ('Cucumber', 0.4, 0.5, 1, 2),
    ('Pepper', 0.5, 0.4, 2, 1),
    ('Spinach', 0.3, 0.2, 3, 3),
    ('Onion', 0.4, 0.3, 1, 2),
    ('Garlic', 0.3, 0.2, 2, 1),
    ('Broccoli', 0.5, 0.4, 3, 3),
    ('Cauliflower', 0.5, 0.4, 1, 2);

-- =============================================
-- IRRIGATION SCHEDULES
-- =============================================

/**
 * Generate sample irrigation schedules for October-November 2024
 * This creates realistic schedules for demonstration purposes
 */

-- Crop 1: Tomato (3 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-05', '08:00:00', '09:00:00', '2024-10-05 07:30:00', '2024-10-05 07:30:00', 'Completed', 1),
    ('2024-10-12', '08:00:00', '09:00:00', '2024-10-12 07:45:00', '2024-10-12 07:45:00', 'Completed', 1),
    ('2024-10-26', '08:00:00', '09:00:00', '2024-10-26 07:15:00', '2024-10-26 07:15:00', 'Completed', 1);

-- Crop 2: Lettuce (2 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-08', '08:00:00', '09:00:00', '2024-10-08 07:20:00', '2024-10-08 07:20:00', 'Completed', 2),
    ('2024-10-22', '08:00:00', '09:00:00', '2024-10-22 07:35:00', '2024-10-22 07:35:00', 'Completed', 2);

-- Crop 3: Carrot (4 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-03', '08:00:00', '09:00:00', '2024-10-03 07:10:00', '2024-10-03 07:10:00', 'Completed', 3),
    ('2024-10-10', '08:00:00', '09:00:00', '2024-10-10 07:25:00', '2024-10-10 07:25:00', 'Completed', 3),
    ('2024-10-24', '08:00:00', '09:00:00', '2024-10-24 07:40:00', '2024-10-24 07:40:00', 'Completed', 3),
    ('2024-11-07', '08:00:00', '09:00:00', '2024-11-07 07:50:00', '2024-11-07 07:50:00', 'Pending', 3);

-- Crop 4: Cucumber (3 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-07', '08:00:00', '09:00:00', '2024-10-07 07:15:00', '2024-10-07 07:15:00', 'Completed', 4),
    ('2024-10-21', '08:00:00', '09:00:00', '2024-10-21 07:30:00', '2024-10-21 07:30:00', 'Completed', 4),
    ('2024-11-04', '08:00:00', '09:00:00', '2024-11-04 07:45:00', '2024-11-04 07:45:00', 'Pending', 4);

-- Crop 5: Pepper (1 schedule)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-15', '08:00:00', '09:00:00', '2024-10-15 07:20:00', '2024-10-15 07:20:00', 'Completed', 5);

-- Crop 6: Spinach (5 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-02', '08:00:00', '09:00:00', '2024-10-02 07:10:00', '2024-10-02 07:10:00', 'Completed', 6),
    ('2024-10-09', '08:00:00', '09:00:00', '2024-10-09 07:25:00', '2024-10-09 07:25:00', 'Completed', 6),
    ('2024-10-16', '08:00:00', '09:00:00', '2024-10-16 07:40:00', '2024-10-16 07:40:00', 'Completed', 6),
    ('2024-10-30', '08:00:00', '09:00:00', '2024-10-30 07:55:00', '2024-10-30 07:55:00', 'Completed', 6),
    ('2024-11-13', '08:00:00', '09:00:00', '2024-11-13 07:10:00', '2024-11-13 07:10:00', 'Pending', 6);

-- Crop 7: Onion (2 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-11', '08:00:00', '09:00:00', '2024-10-11 07:30:00', '2024-10-11 07:30:00', 'Completed', 7),
    ('2024-10-25', '08:00:00', '09:00:00', '2024-10-25 07:45:00', '2024-10-25 07:45:00', 'Completed', 7);

-- Crop 8: Garlic (3 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-06', '08:00:00', '09:00:00', '2024-10-06 07:15:00', '2024-10-06 07:15:00', 'Completed', 8),
    ('2024-10-20', '08:00:00', '09:00:00', '2024-10-20 07:30:00', '2024-10-20 07:30:00', 'Completed', 8),
    ('2024-11-03', '08:00:00', '09:00:00', '2024-11-03 07:45:00', '2024-11-03 07:45:00', 'Pending', 8);

-- Crop 9: Broccoli (4 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-04', '08:00:00', '09:00:00', '2024-10-04 07:20:00', '2024-10-04 07:20:00', 'Completed', 9),
    ('2024-10-18', '08:00:00', '09:00:00', '2024-10-18 07:35:00', '2024-10-18 07:35:00', 'Completed', 9),
    ('2024-11-01', '08:00:00', '09:00:00', '2024-11-01 07:50:00', '2024-11-01 07:50:00', 'Pending', 9),
    ('2024-11-15', '08:00:00', '09:00:00', '2024-11-15 07:05:00', '2024-11-15 07:05:00', 'Pending', 9);

-- Crop 10: Cauliflower (2 schedules)
INSERT INTO agriculture.schedule (
    schedule_date,
    schedule_start,
    schedule_end,
    schedule_created_at,
    schedule_updated_at,
    schedule_status,
    crop_id
) VALUES
    ('2024-10-13', '08:00:00', '09:00:00', '2024-10-13 07:25:00', '2024-10-13 07:25:00', 'Completed', 10),
    ('2024-10-27', '08:00:00', '09:00:00', '2024-10-27 07:40:00', '2024-10-27 07:40:00', 'Completed', 10);