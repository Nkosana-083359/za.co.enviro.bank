CREATE TABLE IF NOT EXISTS account_type_lookup (
    account_type_id INTEGER PRIMARY KEY DEFAULT FLOOR(RANDOM()*(25 - 5 + 1)) + 5,
    account_type VARCHAR(255) NOT NULL,
    minimum_balance DECIMAL(10, 2) NOT NULL,
    overdraft_limit DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS customer(
    customer_id SERIAL PRIMARY KEY,
    identity_number INTEGER,
    cname VARCHAR(50) ,
    surname VARCHAR (60),
    phone_number VARCHAR(12)
);

CREATE TABLE IF NOT EXISTS bank_accounts (
    acc_num UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    acc_balance NUMERIC DEFAULT 0,
    customer_id INTEGER ,
    account_type_id INTEGER ,
  CONSTRAINT foreign_key_accountType_id FOREIGN KEY(account_type_id) REFERENCES account_type_lookup (account_type_id) ON UPDATE CASCADE,
  CONSTRAINT foreign_key_identity_number FOREIGN KEY(customer_id) REFERENCES customer (customer_id) on UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS bank_transactions (
     transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     transaction_type VARCHAR(50),
     transaction_amount NUMERIC ,
     reference VARCHAR(50),
     acc_number UUID,
     active BOOLEAN,
     CONSTRAINT foreign_key_acc_number FOREIGN KEY(acc_number) REFERENCES bank_accounts(acc_num) ON UPDATE CASCADE ON DELETE SET NULL
);

