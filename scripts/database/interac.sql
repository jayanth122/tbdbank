CREATE TABLE interac(
    interac_id varchar(50) PRIMARY KEY,
    customer_id varchar(50) NOT NULL,
    first_name varchar(25) NOT NULL,
    last_name varchar(25) NOT NULL,
    email varchar(25) NOT NULL,
    message varchar(25) NULL,
    bank_name varchar(25) NOT NULL
    );