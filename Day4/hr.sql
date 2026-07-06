-- See: https://github.com/oracle-samples/db-sample-schemas/tree/main/human_resources for the original HR schema

SELECT
    first_name || ' ' || last_name AS full_name,
    email,
    hire_date,
    department_id
FROM Employees
WHERE (
        first_name LIKE 'G%'
        OR first_name LIKE '%st%'
    )
    AND DEPARTMENT_ID = ANY (80, 30)

SELECT *
FROM EMPLOYEES
WHERE
    HIRE_DATE BETWEEN TO_DATE('01-JAN-2015', 'DD-MON-YYYY') AND TO_DATE('31-DEC-2015', 'DD-MON-YYYY')
    AND COMMISSION_PCT IS NOT NULL
ORDER BY HIRE_DATE DESC