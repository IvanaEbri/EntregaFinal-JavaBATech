CREATE DATABASE IF NOT EXISTS nookbooks
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE nookbooksnookbooks;
DROP USER IF EXISTS 'ecom_user'@'localhost';
CREATE USER 'ecom_user'@'localhost' IDENTIFIED BY 'ecom_pass';
GRANT ALL PRIVILEGES ON nookbooks.* TO 'ecom_user'@'localhost';
FLUSH PRIVILEGES;
