CREATE TABLE IF NOT EXISTS user_role (
    role_id INTEGER,
    user_id UUID,
    CONSTRAINT foreign_key_role_id FOREIGN KEY(role_id) REFERENCES role (role_id) ON UPDATE CASCADE,
    CONSTRAINT foreign_key_user_id FOREIGN KEY(user_id) REFERENCES customer (id) ON UPDATE CASCADE
    );