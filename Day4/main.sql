-- Active: 1783035659506@@@443@EJADATESTAPP
DROP TABLE IF EXISTS Employees;

CREATE TABLE Employees (
		EmployeeID INT,
		FirstName VARCHAR(50),
		LastName VARCHAR(50),
		Email VARCHAR(100),
		HireDate DATE,
		PRIMARY KEY (EmployeeID)
);

DESC Employees; -- this only works in sqlplus and oracle sql developer

-- equivalent to desc employees:
SELECT column_name, data_type, data_length,nullable, data_precision, data_scale
FROM user_tab_columns
WHERE table_name = 'EMPLOYEES'
ORDER BY column_id;

ALTER TABLE Employees ADD Salary NUMBER(10);

ALTER TABLE Employees MODIFY Salary NUMBER(12);

ALTER TABLE EMPLOYEES RENAME COLUMN Salary TO BaseSalary;

ALTER TABLE Employees DROP COLUMN BaseSalary;

RENAME Employees TO Staff;

DROP TABLE Staff;


CREATE TABLE IF NOT EXISTS Employees (
		EmployeeID INT,
		FirstName VARCHAR(50),
		LastName VARCHAR(50),
		Email VARCHAR(100),
		HireDate DATE,
		PRIMARY KEY (EmployeeID)
);

-- insert some filler data
INSERT INTO Employees (EmployeeID, FirstName, LastName, Email, HireDate) VALUES
(1, 'John', 'Doe', 'johndoe@test.com', TO_DATE('2020-01-15', 'YYYY-MM-DD')),
(2, 'Jane', 'Smith', 'janesmith@test.com', TO_DATE('2021-03-22', 'YYYY-MM-DD')),
(3, 'Alice', 'Johnson', 'alicejohnson@test.com', TO_DATE('2022-06-30', 'YYYY-MM-DD'));

SELECT * FROM Employees;

DELETE FROM Employees;

TRUNCATE TABLE Employees;

DELETE FROM Employees WHERE EmployeeID = 1;

COMMIT;

UPDATE Employees
SET Email = 'johndoe@newdomain2.com',
		HireDate = TO_DATE('2020-02-01', 'YYYY-MM-DD')
WHERE EmployeeID = 1

ROLLBACK;
