DECLARE
    employee_count INT;
    department_count INT;
BEGIN
    SELECT COUNT(*) INTO employee_count FROM employees;
    SELECT COUNT(*) INTO department_count FROM departments;
    IF department_count = 0 THEN
        INSERT INTO departments (name) VALUES
        ('HR'),
        ('IT'),
        ('Finance');
    END IF;

    IF employee_count = 0 THEN
        INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, salary,department_id) VALUES
        ('John', 'Doe', 'john.doe@example.com', '555-1234', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 5000,1),
        ('Jane', 'Smith', 'jane.smith@example.com', '555-5678', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 2000,2),
        ('Michael', 'Johnson', 'michael.johnson@example.com', '555-9012', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 6000,3);
    END IF;
END;
/