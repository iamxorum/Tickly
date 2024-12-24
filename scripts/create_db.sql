-- Step 1: Create the schema
CREATE SCHEMA IF NOT EXISTS ticketing_system;
SET search_path TO ticketing_system;

-- Step 2: Create tables

-- 2.1 Users table (to store user information like technicians, customers, etc.)
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    role_id INT REFERENCES roles(role_id) ON DELETE SET NULL,
    company_id INT REFERENCES companies(company_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.2 Companies table (to manage companies using the system)
CREATE TABLE companies (
    company_id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    address TEXT,
    phone_number VARCHAR(15),
    email VARCHAR(100)
);

-- 2.3 Roles table (to manage user roles such as Admin, Technician, Customer)
CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- 2.4 Ticket Categories table (to classify tickets)
CREATE TABLE ticket_categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- 2.5 Ticket Statuses table (to manage ticket statuses like Open, Closed, etc.)
CREATE TABLE ticket_statuses (
    status_id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- 2.6 Tickets table (the core table for ticket data)
CREATE TABLE tickets (
    ticket_id SERIAL PRIMARY KEY,
    ticket_subject VARCHAR(255) NOT NULL,
    ticket_description TEXT,
    category_id INT REFERENCES ticket_categories(category_id),
    status_id INT REFERENCES ticket_statuses(status_id),
    assignee_id INT REFERENCES users(user_id),
    created_by INT REFERENCES users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    priority VARCHAR(20) CHECK(priority IN ('Low', 'Normal', 'High', 'Critical')) NOT NULL,
    severity FLOAT NOT NULL,  -- Calculated severity value
    urgency FLOAT NOT NULL,   -- Calculated urgency value
    due_date TIMESTAMP
);

-- 2.7 Ticket Logs table (for historical tracking of ticket changes)
CREATE TABLE ticket_logs (
    log_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    status_id INT REFERENCES ticket_statuses(status_id),
    assignee_id INT REFERENCES users(user_id),
    custom_status VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.8 Ticket Comments table (for user comments on tickets)
CREATE TABLE ticket_comments (
    comment_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    user_id INT REFERENCES users(user_id),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.9 Ticket Attachments table (for file attachments to tickets)
CREATE TABLE ticket_attachments (
    attachment_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    file_name VARCHAR(255),
    file_path TEXT,
    uploaded_by INT REFERENCES users(user_id),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.10 Ticket Assignments table (to track ticket assignee history)
CREATE TABLE ticket_assignments (
    assignment_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    user_id INT REFERENCES users(user_id),
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.11 SLA (Service Level Agreements) table (for tracking SLA deadlines)
CREATE TABLE sla_rules (
    sla_id SERIAL PRIMARY KEY,
    category_id INT REFERENCES ticket_categories(category_id),
    max_resolution_time INTERVAL NOT NULL,
    escalation_time INTERVAL NOT NULL
);

-- 2.12 Notifications table (to manage notifications for users)
CREATE TABLE notifications (
    notification_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    ticket_id INT REFERENCES tickets(ticket_id),
    notification_type VARCHAR(50),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
);

-- 2.13 Audit Logs table (for recording changes to tickets)
CREATE TABLE ticket_audit_logs (
    audit_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    changed_by INT REFERENCES users(user_id),
    change_description TEXT,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.14 Escalation table (for tracking when tickets need to be escalated)
CREATE TABLE ticket_escalations (
    escalation_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    escalated_to INT REFERENCES users(user_id),
    escalated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reason TEXT
);

-- 2.15 Ticket Satisfaction Survey table (for collecting customer feedback)
CREATE TABLE ticket_satisfaction_surveys (
    survey_id SERIAL PRIMARY KEY,
    ticket_id INT REFERENCES tickets(ticket_id),
    rating INT CHECK(rating BETWEEN 1 AND 5),
    feedback TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.16 Maintenance window table (for scheduling maintenance windows)
CREATE TABLE maintenance_windows (
    window_id SERIAL PRIMARY KEY,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    description TEXT,
    created_by INT REFERENCES users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Step 3: Insert sample data into roles and ticket statuses (to initialize the system)
-- Roles sample data
INSERT INTO roles (name, description)
VALUES
('Admin', 'Has full access to all features and tickets'),
('Technician', 'Handles ticket resolution and updates'),
('Manager', 'Oversees ticket handling and escalations'),
('Customer', 'End-user who reports tickets');

-- Ticket Statuses sample data
INSERT INTO ticket_statuses (name, description)
VALUES
('New', 'Ticket has been created but not yet processed'),
('In Progress', 'Ticket is being worked on'),
('Resolved', 'Ticket has been resolved'),
('Closed', 'Ticket has been closed'),
('On Hold', 'Ticket is on hold'),
('Escalated', 'Ticket has been escalated to a higher level');

-- Ticket Categories sample data
INSERT INTO ticket_categories (name, description)
VALUES
('Network', 'Network-related issues'),
('Hardware', 'Hardware-related issues'),
('Software', 'Software-related issues'),
('Security', 'Security-related issues'),
('Other', 'Other types of issues');

-- Insert companies (sample data)
INSERT INTO companies (name, address, phone_number, email)
VALUES
('TechCorp', '123 Tech Road, Tech City', '123-456-7890', 'contact@techcorp.com'),
('SoftWorks', '456 Software Blvd, Dev Town', '987-654-3210', 'support@softworks.com');

-- Insert sample users into the users table

-- Admin user
INSERT INTO users (first_name, last_name, username, email, phone_number, role_id, company_id)
VALUES
('Alice', 'Johnson', 'alice.johnson', 'alice.johnson@techcorp.com', '555-1234', 1, 1);

-- Technician user
INSERT INTO users (first_name, last_name, username, email, phone_number, role_id, company_id)
VALUES
('Bob', 'Smith', 'bob.smith', 'bob.smith@techcorp.com', '555-5678', 2, 1);

-- Manager user
INSERT INTO users (first_name, last_name, username, email, phone_number, role_id, company_id)
VALUES
('Charlie', 'Brown', 'charlie.brown', 'charlie.brown@softworks.com', '555-8765', 3, 2);

-- Customer user
INSERT INTO users (first_name, last_name, username, email, phone_number, role_id, company_id)
VALUES
('David', 'White', 'david.white', 'david.white@techcorp.com', '555-4321', 4, 1);

-- Insert sample tickets into the tickets table

-- Ticket 1: Network issue reported by a customer
INSERT INTO tickets (ticket_subject, ticket_description, category_id, status_id, assignee_id, created_by, priority, severity, urgency, due_date)
VALUES
('Network Connectivity Issue', 'Customer reports slow network connectivity in the office.', 1, 1, 2, 4, 'High', 7.5, 8.0, '2024-12-25 12:00:00');

-- Ticket 2: Software installation issue (Customer reports an issue with software installation)
INSERT INTO tickets (ticket_subject, ticket_description, category_id, status_id, assignee_id, created_by, priority, severity, urgency, due_date)
VALUES
('Software Installation Failure', 'Customer encounters error during software installation.', 3, 1, 2, 4, 'Normal', 5.0, 6.0, '2024-12-26 10:00:00');

-- Ticket 3: Hardware issue (Technician reports malfunctioning equipment)
INSERT INTO tickets (ticket_subject, ticket_description, category_id, status_id, assignee_id, created_by, priority, severity, urgency, due_date)
VALUES
('Printer Malfunction', 'Technician reports malfunctioning printer in the main office.', 2, 1, 2, 2, 'Critical', 9.0, 9.5, '2024-12-24 15:00:00');

-- Ticket 4: Security issue (Manager reports a security breach)
INSERT INTO tickets (ticket_subject, ticket_description, category_id, status_id, assignee_id, created_by, priority, severity, urgency, due_date)
VALUES
('Security Breach Detected', 'Manager reports a potential security breach in the network.', 4, 1, 2, 3, 'Critical', 9.5, 9.8, '2024-12-25 18:00:00');

-- Ticket 5: Miscellaneous issue (Customer reports an issue not related to categories)
INSERT INTO tickets (ticket_subject, ticket_description, category_id, status_id, assignee_id, created_by, priority, severity, urgency, due_date)
VALUES
('General Inquiry', 'Customer has a general inquiry regarding the system.', 5, 1, 2, 4, 'Low', 3.0, 4.0, '2024-12-28 09:00:00');


-- Step 4: Create indexes to optimize performance on frequently queried fields
CREATE INDEX idx_ticket_assignee_id ON tickets (assignee_id);
CREATE INDEX idx_ticket_status_id ON tickets (status_id);
CREATE INDEX idx_ticket_created_at ON tickets (created_at);
CREATE INDEX idx_ticket_category_id ON tickets (category_id);
CREATE INDEX idx_ticket_priority ON tickets (priority);
CREATE INDEX idx_ticket_due_date ON tickets (due_date);
