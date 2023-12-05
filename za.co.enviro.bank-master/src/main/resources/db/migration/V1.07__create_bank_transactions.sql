CREATE TABLE IF NOT EXISTS bank_transactions (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_type VARCHAR(50) NOT NULL,
    transaction_amount NUMERIC NOT NULL,
    reference VARCHAR(50),
    source_acc_number UUID NOT NULL,
    destinationAccNumber UUID NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT now(),
    active BOOLEAN,
    CONSTRAINT foreign_key_acc_number FOREIGN KEY(source_acc_number) REFERENCES bank_accounts(acc_num) ON UPDATE CASCADE ON DELETE SET NULL
    );