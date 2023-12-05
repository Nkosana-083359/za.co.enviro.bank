CREATE TABLE IF NOT EXISTS password_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(250),
    token_creation_date TIMESTAMP,
    expiry_date TIMESTAMP,
    password_modification_date TIMESTAMP,
    customer_id UUID,
    CONSTRAINT foreign_key_identity_number FOREIGN KEY(customer_id) REFERENCES customer (id) ON UPDATE CASCADE ON DELETE SET NULL
    );