-- =========================
-- CREATE DATABASE
-- =========================
CREATE DATABASE CarRentalDB;
USE CarRentalDB;

-- =========================
-- OWNER TABLE (LOGIN)
-- =========================
CREATE TABLE Owner (
    owner_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO Owner (username, password) VALUES
('owner1', 'owner123'),
('owner2', 'owner456');

-- =========================
-- CUSTOMERS TABLE (LOGIN)
-- =========================
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    address VARCHAR(255),
    license_number VARCHAR(50) UNIQUE
);

INSERT INTO Customers (name, username, password, email, phone, address, license_number) VALUES
('Ali Khan', 'ali123', 'ali@123', 'ali@gmail.com', '03001234567', 'Karachi', 'LIC001'),
('Sara Ahmed', 'sara123', 'sara@123', 'sara@gmail.com', '03111234567', 'Lahore', 'LIC002'),
('Usman Tariq', 'usman123', 'usman@123', 'usman@gmail.com', '03221234567', 'Islamabad', 'LIC003'),
('Ayesha Malik', 'ayesha123', 'ayesha@123', 'ayesha@gmail.com', '03331234567', 'Karachi', 'LIC004'),
('Bilal Hussain', 'bilal123', 'bilal@123', 'bilal@gmail.com', '03441234567', 'Faisalabad', 'LIC005'),
('Hina Sheikh', 'hina123', 'hina@123', 'hina@gmail.com', '03551234567', 'Multan', 'LIC006'),
('Zain Ali', 'zain123', 'zain@123', 'zain@gmail.com', '03661234567', 'Hyderabad', 'LIC007'),
('Fatima Noor', 'fatima123', 'fatima@123', 'fatima@gmail.com', '03771234567', 'Quetta', 'LIC008');

-- =========================
-- CARS TABLE
-- =========================
CREATE TABLE Cars (
    car_id INT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(50),
    model VARCHAR(50),
    year INT,
    price_per_day DECIMAL(10,2) CHECK (price_per_day > 0),
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES Owner(owner_id)
);

INSERT INTO Cars (brand, model, year, price_per_day, owner_id) VALUES
('Toyota', 'Corolla', 2022, 5000, 1),
('Honda', 'Civic', 2023, 7000, 1),
('Suzuki', 'Alto', 2021, 3000, 2),
('Kia', 'Sportage', 2024, 9000, 2);

-- =========================
-- BOOKINGS TABLE
-- =========================
CREATE TABLE Bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    car_id INT,
    start_date DATE,
    end_date DATE,
    total_amount DECIMAL(10,2),
    booking_status ENUM('Pending','Confirmed','Cancelled') DEFAULT 'Confirmed',

    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES Cars(car_id) ON DELETE CASCADE
);

INSERT INTO Bookings (customer_id, car_id, start_date, end_date, total_amount, booking_status) VALUES
(1, 1, '2026-04-01', '2026-04-05', 20000, 'Confirmed'),
(2, 2, '2026-04-10', '2026-04-12', 14000, 'Confirmed'),
(3, 3, '2026-04-15', '2026-04-18', 9000, 'Confirmed');

-- =========================
-- PAYMENTS TABLE
-- =========================
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    customer_id INT,
    payment_date DATE,
    amount_paid DECIMAL(10,2),
    payment_status ENUM('Pending','Paid','Failed') DEFAULT 'Paid',
    payment_method VARCHAR(50),

    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);

INSERT INTO Payments (booking_id, customer_id, payment_date, amount_paid, payment_status, payment_method) VALUES
(1, 1, '2026-04-01', 20000, 'Paid', 'Cash'),
(2, 2, '2026-04-10', 14000, 'Paid', 'Card'),
(3, 3, '2026-04-15', 9000, 'Paid', 'Online');
