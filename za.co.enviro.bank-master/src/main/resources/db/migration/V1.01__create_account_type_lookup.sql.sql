
CREATE TABLE IF NOT EXISTS account_type_lookup (
    account_type_id SERIAL PRIMARY KEY,
    account_type VARCHAR(255) NOT NULL,
    minimum_balance DECIMAL(10, 2) NOT NULL,
    overdraft_limit DECIMAL(10, 2) NOT NULL
);
