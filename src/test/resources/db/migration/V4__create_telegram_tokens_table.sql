CREATE TABLE telegram_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id INT UNIQUE,
    CONSTRAINT fk_telegram_tokens_user_id FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE
);