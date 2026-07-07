-- Active: 1783035659506@@@443@EJADATESTAPP
DROP TABLE IF EXISTS EMPLOYEES;

DROP TABLE IF EXISTS DEPARTMENTS;

CREATE TABLE departments (
    department_id NUMBER GENERATED ALWAYS AS IDENTITY,
    department_name VARCHAR2(50),
    CONSTRAINT pk_departments PRIMARY KEY (department_id)
);

CREATE TABLE employees (
    employee_id NUMBER GENERATED ALWAYS AS IDENTITY,
    dept_id NUMBER,
    first_name VARCHAR2(50),
    last_name VARCHAR2(50),
    CONSTRAINT pk_employees PRIMARY KEY (employee_id),
    CONSTRAINT fk_employees_departments FOREIGN KEY (dept_id) REFERENCES departments (department_id)
);

INSERT INTO departments (department_name) VALUES ('HR'), ('IT');

INSERT INTO
    employees (
        dept_id,
        first_name,
        last_name
    )
VALUES (1, 'John', 'Doe'),
    (1, 'Jane', 'Smith'),
    (2, 'Alice', 'Johnson'),
    (2, 'Bob', 'Brown');

SELECT * FROM employees;