CREATE TABLE customer
(
    customer_id SERIAL PRIMARY KEY,
    user_name     VARCHAR(25) NOT NULL,
    password      VARCHAR(25) NOT NULL,
    first_name    VARCHAR(25) NOT NULL,
    last_name     VARCHAR(25) NOT NULL,
    date_of_birth DATE        NOT NULL,
    email         VARCHAR(25) NOT NULL,
    country_code  VARCHAR(5)  NOT NULL,
    mobile_number VARCHAR(12) NOT NULL,
    street_number VARCHAR(10) NOT NULL,
    unit_number   INT         NOT NULL,
    street_name   VARCHAR(25) NOT NULL,
    city          VARCHAR(25) NOT NULL,
    province      VARCHAR(25) NOT NULL,
    postal_code   VARCHAR(10) NOT NULL,
    sin_number    BIGINT      NOT NULL
);
