CREATE TABLE devices (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    device_name VARCHAR(255) NOT NULL,
    user_id INT,
    CONSTRAINT fk_devices_user_id FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE
);