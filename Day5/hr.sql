-- Active: 1783324735509@@@443@HR
-- practice on some sql functions

-- count employees in each department sorted by employee count in descending order and department name in ascending order
SELECT d.DEPARTMENT_NAME, count(*) AS employee_count
FROM employees e
    JOIN departments d ON e.department_id = d.department_id
GROUP BY
    d.DEPARTMENT_NAME
ORDER BY employee_count DESC, d.DEPARTMENT_NAME ASC;

-- update employee names to lower case for first name and upper case for last name
UPDATE employees
SET
    first_name = lower(first_name),
    last_name = upper(last_name)

-- reset them to normal casing
UPDATE employees
SET
    first_name = initcap(first_name),
    last_name = initcap(last_name)

-- get the length of first names of employees
SELECT concat(
        first_name, length(first_name)
    ) AS first_name_length
FROM employees;

-- pad all first names and last names with underscores and show full name at the end
SELECT
    padded_first_name || ' ' || padded_last_name AS padded_names
FROM (
        SELECT
            LPAD(
                first_name, (
                    SELECT max(length(first_name)) AS max_length
                    FROM employees
                ), '_'
            ) AS padded_first_name, RPAD(
                last_name, (
                    SELECT max(length(last_name)) AS max_length
                    FROM employees
                ), '_'
            ) AS padded_last_name
        FROM employees
    );

SELECT
    first_name,
    last_name,
    NVL(COMMISSION_PCT, 0) AS COMMISSION_PCT
FROM employees

SELECT
    first_name || ' (' || instr(UPPER(first_name), UPPER('A')) || ')',
    substr(TRIM(d.department_name), 1, 5) AS dept_prefix
FROM employees
    JOIN departments d ON employees.department_id = d.department_id

SELECT to_number('123.45') AS test_num FROM dual;
-- = 123.45

SELECT floor(123.45) AS test_floor FROM dual;
-- = 123

SELECT ceil(123.45) AS test_ceil FROM dual;
-- = 124

SELECT round(123.45) AS rounded_down, round(123.55) AS rounded_up
FROM dual;
-- = 123, 124

SELECT power(2, 3) AS test_power FROM dual;
-- = 8

SELECT
    min(salary) AS min_salary,
    max(salary) AS max_salary,
    avg(salary) AS avg_salary
FROM employees;

SELECT
    NVL(
        d.department_name,
        'Unknown department'
    ),
    round(avg(salary), 2) AS average_salary,
    count(*) AS employee_count
FROM employees e
    LEFT JOIN departments d ON e.department_id = d.department_id
GROUP BY
    d.department_name
HAVING
    employee_count > 1
ORDER BY average_salary DESC;