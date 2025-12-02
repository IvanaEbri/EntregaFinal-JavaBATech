CREATE DATABASE nookbook_techlabba_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ecom_user'@'%' IDENTIFIED BY 'ecom_pass';
GRANT ALL PRIVILEGES ON nookbook_techlabba_db.* TO 'ecom_user'@'%';
FLUSH PRIVILEGES;

