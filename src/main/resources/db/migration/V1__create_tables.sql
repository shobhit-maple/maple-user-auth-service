CREATE TABLE user_login
(
    id              VARCHAR(255) PRIMARY KEY NOT NULL,
    organization_id VARCHAR(255)             NOT NULL,
    user_account_id VARCHAR(255)             NOT NULL,
    email_address   VARCHAR(255)             NOT NULL UNIQUE,
    password_hash   VARCHAR(255)             NOT NULL
);
