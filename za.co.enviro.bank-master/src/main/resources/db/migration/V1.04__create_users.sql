CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_number VARCHAR(13) UNIQUE NOT NULL,
    name VARCHAR(225) NOT NULL,
    surname VARCHAR(60) NOT NULL,
    phone_number VARCHAR(12) NOT NULL,
    username VARCHAR(60) NOT NULL,
    num_Accounts BIGINT,
    role_id INTEGER,
    CONSTRAINT foreign_key_role FOREIGN KEY(role_id) REFERENCES role (role_id) ON UPDATE CASCADE
    );