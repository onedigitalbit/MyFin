DROP DATABASE IF EXISTS myfin;
CREATE DATABASE myfin;
USE myfin;

DROP USER IF EXISTS 'myfin_user1'@'localhost';
CREATE USER 'myfin_user1'@'localhost' IDENTIFIED BY 'myfin_user1';

GRANT ALL PRIVILEGES ON myfin.* TO 'myfin_user1'@'localhost';
FLUSH PRIVILEGES;


DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS banks;
DROP TABLE IF EXISTS loan_payments;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS sub_categories;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    loan_type ENUM('Lent', 'Borrowed') NOT NULL,
    borrower_or_lender_name VARCHAR(255) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description VARCHAR(255),
    due_date DATE,
    status ENUM('Pending', 'Paid') DEFAULT 'Pending',
    remaining_balance DECIMAL(15, 2) NOT NULL, -- To track remaining balance
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE loan_payments (
    loan_payment_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT,
    payment_amount DECIMAL(15, 2) NOT NULL,
    payment_date DATE NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loans(loan_id)
);

CREATE TABLE banks (
    bank_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    bank_name VARCHAR(255) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    branch_name VARCHAR(255),
    IFSC_code VARCHAR(11),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    account_name VARCHAR(255) NOT NULL,
    account_type ENUM('BANK', 'CASH', 'BROKER', 'OTHER','STOCK','REAL_ESTATE','COMMODITY','LOAN') NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE sub_categories (
    sub_category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    subcategory_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE transactions (
    transaction_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    transaction_type ENUM('CREDIT', 'DEBIT') NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description TEXT,
    transaction_date DATE NOT NULL,
    category_id INT,
    sub_category_id INT,
    linked_loan_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (sub_category_id) REFERENCES sub_categories(sub_category_id),
    FOREIGN KEY (linked_loan_id) REFERENCES loans(loan_id)
);


-- Insert sample data for Users
INSERT INTO users (name, email, phone_number) VALUES
('John Doe', 'john.doe@example.com', '1234567890');

-- Insert sample data for Categories
INSERT INTO categories (category_name) VALUES
('Food'),
('Grocery'),
('Home'),
('Investment'),
('Transfer');

-- Insert sample data for SubCategories
INSERT INTO sub_categories (category_id, subcategory_name) VALUES
(1, 'Meal'),
(1, 'Tea'),
(1, 'Breakfast'),
(2, 'Banana'),
(2, 'Sugar'),
(3, 'Bill'),
(4, 'Stock'),
(4, 'Gold');

-- Insert sample data for Accounts
INSERT INTO accounts (user_id, account_name, account_type, balance) VALUES
(1, 'BOI Account', 'BANK', 10000.00),
(1, 'SBI Account', 'BANK', 10000.00),
(1, 'Cash Wallet', 'CASH', 500.00),
(1, 'UPSTOX Fund', 'BROKER', 20000.00),
(1, 'ZERODHA Fund', 'BROKER', 20000.00),
(1, 'HCL','STOCK',10000),
(1, 'RajRoyal','REAL_ESTATE',50000),
(1, 'Gold','COMMODITY',10000),
(1, 'Land','LOAN',5000),
(1, 'Borrow','LOAN',1000);

-- Insert sample data for loans
INSERT INTO loans (user_id, loan_type, borrower_or_lender_name, amount,remaining_balance, description, created_at, status, due_date) VALUES
(1, 'Lent', 'Alice', 5000.00,  2000.00,'paid by upi','2024-11-03','Pending', '2025-12-31'),
(1, 'Borrowed', 'Bob', 3000.00, 3000.00, 'took by cash','2024-09-11','Paid', '2025-06-30');

INSERT INTO loan_payments (loan_id, payment_amount, payment_date) VALUES
(1, 3000, '2025-01-01');

-- Insert sample data for Transactions
-- Purchase some grocery (banana, sugar)
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id, sub_category_id) VALUES
(2, 'DEBIT', 100.00, 'Purchased bananas', '2025-01-08', 2, 4),
(2, 'DEBIT', 50.00, 'Purchased sugar', '2025-01-08', 2, 5);

-- Transfer money from one bank account to another
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id) VALUES
(1, 'DEBIT', 2000.00, 'Transferred to Cash Wallet', '2025-01-08', 5),
(2, 'CREDIT', 2000.00, 'Received from Savings Account', '2025-01-08', 5);

-- Purchase land for investment
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id, sub_category_id) VALUES
(1, 'DEBIT', 15000.00, 'Purchased Downtown Property', '2024-12-01', 4, NULL);

-- Purchase gold
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id, sub_category_id) VALUES
(1, 'DEBIT', 1000.00, 'Purchased 10 grams of gold', '2025-01-05', 4, 8);

-- Add freelance payment
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id) VALUES
(1, 'CREDIT', 2000.00, 'Freelance project payment', '2025-01-07', NULL);

-- Add salary payment
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id) VALUES
(1, 'CREDIT', 5000.00, 'Monthly salary', '2025-01-05', NULL);

-- Sell gold
INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date, category_id, sub_category_id) VALUES
(1, 'CREDIT', 1100.00, 'Sold 10 grams of gold', '2025-01-10', 4, 8);
