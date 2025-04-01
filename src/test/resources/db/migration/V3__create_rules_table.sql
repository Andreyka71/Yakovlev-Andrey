CREATE TABLE rules (
    id SERIAL PRIMARY KEY,
    rule VARCHAR(255) NOT NULL,
    lowest_value INT NOT NULL,
    highest_value INT NOT NULL,
    device_id INT,
    CONSTRAINT fk_rules_device_id FOREIGN KEY (device_id) 
        REFERENCES devices(id) ON DELETE CASCADE
);
