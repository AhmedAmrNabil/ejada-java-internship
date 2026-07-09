DECLARE
    employee_count INT;
BEGIN
    SELECT COUNT(*) INTO employee_count FROM employees;
    IF employee_count = 0 THEN
        INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary) VALUES
        ('John', 'Doe', 'john.doe@example.com', '555-1234', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'IT_PROG', 5000),
        ('Jane', 'Smith', 'jane.smith@example.com', '555-5678', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'SALES', 2000),
        ('Michael', 'Johnson', 'michael.johnson@example.com', '555-9012', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'IT_PROG', 6000);
    END IF;
END;
/