CREATE DATABASE IF NOT EXISTS expense_tracker;

USE expense_tracker;

CREATE TABLE tbl_expenses
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    expense_name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    expense_amount DOUBLE(5, 2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO tbl_expenses(expense_name, description, expense_amount, category, date)
    VALUES("Water Bill", "water bill", 600.00, "Bills", "2024-12-24");
    
INSERT INTO tbl_expenses(expense_name, description, expense_amount, category, date)
    VALUES("Electricity Bill", "electricity bill", 900.00, "Bills", "2024-12-24");
