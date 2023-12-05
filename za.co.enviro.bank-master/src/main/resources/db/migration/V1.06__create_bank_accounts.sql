CREATE TABLE IF NOT EXISTS bank_accounts (
    acc_num UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    acc_balance NUMERIC DEFAULT 0,
    available_balance NUMERIC DEFAULT 0,
    customer_id UUID,
    account_type_id INTEGER,
    CONSTRAINT foreign_key_accountType_id FOREIGN KEY(account_type_id) REFERENCES account_type_lookup (account_type_id) ON UPDATE CASCADE,
    CONSTRAINT foreign_key_identity_number FOREIGN KEY(customer_id) REFERENCES customer (id) ON UPDATE CASCADE
    );