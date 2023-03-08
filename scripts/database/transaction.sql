CREATE TABLE transaction (
                             transaction_id varchar(50) PRIMARY KEY,
                             customer_id varchar(50) NOT NULL,
                             transaction_type ENUM('CREDIT', 'DEBIT') NOT NULL,
                             details varchar(50) NOT NULL,
                             amount BIGINT NOT NULL,
                             balance BIGINT NOT NULL,
                             transaction_date DATE NOT NULL,
                             transaction_time TIME NOT NULL
);