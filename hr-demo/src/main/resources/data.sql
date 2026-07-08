MERGE INTO EMPLOYEES e
USING (VALUES 
    ('John', 'Doe', 'john.doe@example.com', '555-1234', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'IT_PROG', 5000),
    ('Jane', 'Smith', 'jane.smith@example.com', '555-5678', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'SALES', 2000),
    ('Michael', 'Johnson', 'michael.johnson@example.com', '555-9012', TO_DATE('2020-01-01', 'YYYY-MM-DD'), 'IT_PROG', 6000)
) AS new_data (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY)
ON (e.EMAIL = new_data.EMAIL)
WHEN MATCHED THEN
    UPDATE SET
        e.FIRST_NAME = new_data.FIRST_NAME,
        e.LAST_NAME = new_data.LAST_NAME,
        e.PHONE_NUMBER = new_data.PHONE_NUMBER,
        e.HIRE_DATE = new_data.HIRE_DATE,
        e.JOB_ID = new_data.JOB_ID,
        e.SALARY = new_data.SALARY
WHEN NOT MATCHED THEN
    INSERT (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY)
    VALUES (new_data.FIRST_NAME, new_data.LAST_NAME, new_data.EMAIL, 
            new_data.PHONE_NUMBER, new_data.HIRE_DATE, new_data.JOB_ID, new_data.SALARY);