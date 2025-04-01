CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT login_unique UNIQUE (login)
);

CREATE TABLE devices (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    device_name VARCHAR(255) NOT NULL,
    user_id INT,
    CONSTRAINT fk_devices_user_id FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE telegram_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id INT UNIQUE,
    CONSTRAINT fk_telegram_tokens_user_id FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE rules (
    id SERIAL PRIMARY KEY,
    rule VARCHAR(255) NOT NULL,
    lowest_value INT NOT NULL,
    highest_value INT NOT NULL,
    device_id INT,
    CONSTRAINT fk_rules_device_id FOREIGN KEY (device_id) 
        REFERENCES devices(id) ON DELETE CASCADE
);