CREATE TABLE royal_bank
(
    transaction_id           varchar(50) PRIMARY KEY,
    to_customer_id           varchar(50)                                 NOT NULL,
    amount                   BIGINT                                      NOT NULL,
    from_customer_first_name varchar(50)                                 NOT NULL,
    from_customer_last_name  varchar(50)                                 NOT NULL,
    from_customer_email      varchar(25)                                 NOT NULL,
    from_bank_name           ENUM('TBDBANK', 'ROYALBANK', 'SCOTIABANK') NOT NULL,
    message                  varchar(25)                                 NULL
);