-- Add full_name, company, phone_number, fields to users table
ALTER TABLE users
  ADD COLUMN full_name VARCHAR(255) AFTER email,
  ADD COLUMN company VARCHAR(255) AFTER full_name,
  ADD COLUMN phone_number VARCHAR(50) AFTER company;
