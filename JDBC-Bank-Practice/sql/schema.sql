-- ============================================================
-- JDBC Banking Management System - Database Schema
-- ============================================================
-- Run this script once to set up the database and tables.
-- Compatible with MySQL 8.0+
-- ============================================================

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS banking_app
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE banking_app;

-- ============================================================
-- TABLE: customers
-- Stores core customer identity information.
-- One customer can have multiple accounts (1:N relationship).
-- ============================================================
CREATE TABLE IF NOT EXISTS customers (
    customer_id  INT          NOT NULL AUTO_INCREMENT,
    full_name    VARCHAR(100) NOT NULL,
    email        VARCHAR(150) NOT NULL,
    phone        VARCHAR(15)  NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_customers     PRIMARY KEY (customer_id),
    CONSTRAINT uq_customer_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABLE: accounts
-- Stores bank account details linked to a customer.
-- account_type: 'SAVINGS' or 'CURRENT'
-- balance uses DECIMAL(15,2) for precise monetary arithmetic.
-- ============================================================
CREATE TABLE IF NOT EXISTS accounts (
    account_id     INT             NOT NULL AUTO_INCREMENT,
    customer_id    INT             NOT NULL,
    account_number VARCHAR(20)     NOT NULL,
    account_type   ENUM('SAVINGS','CURRENT') NOT NULL DEFAULT 'SAVINGS',
    balance        DECIMAL(15, 2)  NOT NULL DEFAULT 0.00,
    created_at     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_accounts          PRIMARY KEY (account_id),
    CONSTRAINT uq_account_number    UNIQUE (account_number),
    CONSTRAINT fk_accounts_customer FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
        ON DELETE RESTRICT   -- Prevent deleting customer with active accounts
        ON UPDATE CASCADE,

    CONSTRAINT chk_balance_non_negative CHECK (balance >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABLE: transactions
-- Immutable audit log of all financial movements.
-- from_account is NULL for DEPOSIT (money comes from outside).
-- to_account is NULL for WITHDRAWAL (money goes outside).
-- transaction_type: DEPOSIT | WITHDRAWAL | TRANSFER
-- ============================================================
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id   BIGINT          NOT NULL AUTO_INCREMENT,
    from_account     VARCHAR(20)     NULL,
    to_account       VARCHAR(20)     NULL,
    transaction_type ENUM('DEPOSIT','WITHDRAWAL','TRANSFER') NOT NULL,
    amount           DECIMAL(15, 2)  NOT NULL,
    transaction_time TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remarks          VARCHAR(255)    NULL,

    CONSTRAINT pk_transactions PRIMARY KEY (transaction_id),
    CONSTRAINT chk_amount_positive CHECK (amount > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABLE: audit_logs (Bonus Feature)
-- Tracks all significant system events for compliance.
-- ============================================================
CREATE TABLE IF NOT EXISTS audit_logs (
    log_id      BIGINT       NOT NULL AUTO_INCREMENT,
    action      VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50)  NOT NULL,
    entity_id   VARCHAR(50)  NOT NULL,
    performed_by VARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
    log_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    details     TEXT         NULL,

    CONSTRAINT pk_audit_logs PRIMARY KEY (log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- INDEXES for performance optimization
-- ============================================================

-- Speed up account lookups by customer
CREATE INDEX idx_accounts_customer_id ON accounts(customer_id);

-- Speed up transaction history queries by account number
CREATE INDEX idx_transactions_from_account ON transactions(from_account);
CREATE INDEX idx_transactions_to_account   ON transactions(to_account);

-- Speed up time-based transaction queries
CREATE INDEX idx_transactions_time ON transactions(transaction_time DESC);

-- ============================================================
-- STORED PROCEDURE: sp_transfer_funds
-- Demonstrates CallableStatement usage (Bonus Feature).
-- Performs atomic fund transfer entirely within the database.
-- ============================================================
DELIMITER $$

CREATE PROCEDURE IF NOT EXISTS sp_transfer_funds(
    IN  p_from_account  VARCHAR(20),
    IN  p_to_account    VARCHAR(20),
    IN  p_amount        DECIMAL(15,2),
    OUT p_status        VARCHAR(100)
)
BEGIN
    DECLARE v_from_balance DECIMAL(15,2);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status = 'ERROR: Transaction failed and rolled back.';
    END;

    START TRANSACTION;

    -- Lock both rows to prevent race conditions (SELECT ... FOR UPDATE)
    SELECT balance INTO v_from_balance
    FROM accounts
    WHERE account_number = p_from_account
    FOR UPDATE;

    IF v_from_balance IS NULL THEN
        SET p_status = 'ERROR: Source account not found.';
        ROLLBACK;
    ELSEIF v_from_balance < p_amount THEN
        SET p_status = 'ERROR: Insufficient balance.';
        ROLLBACK;
    ELSE
        -- Deduct from sender
        UPDATE accounts
        SET balance = balance - p_amount
        WHERE account_number = p_from_account;

        -- Credit to receiver
        UPDATE accounts
        SET balance = balance + p_amount
        WHERE account_number = p_to_account;

        -- Log the transaction
        INSERT INTO transactions (from_account, to_account, transaction_type, amount, remarks)
        VALUES (p_from_account, p_to_account, 'TRANSFER', p_amount, 'Stored procedure transfer');

        COMMIT;
        SET p_status = 'SUCCESS: Transfer completed.';
    END IF;
END$$

DELIMITER ;

-- ============================================================
-- Verify schema creation
-- ============================================================
SELECT 'Schema created successfully.' AS status;
SHOW TABLES;
