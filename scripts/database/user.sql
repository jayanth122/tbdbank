CREATE TABLE user (
    user_id varchar(50) ,
    user_name varchar(25) PRIMARY KEY,
    account_type ENUM('CUSTOMER', 'EMPLOYEE', 'MANAGER', 'ADMINISTRATOR') NOT NULL,
    password varchar(25) NOT NULL)
;