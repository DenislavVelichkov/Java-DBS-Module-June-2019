CREATE FUNCTION use_it()
RETURNS INT
BEGIN
DECLARE RESULT INT;
SET RESULT := (
SELECT ec1.employee_id  FROM `employees_clients` AS ec1
GROUP BY ec1.employee_id
ORDER BY COUNT(ec1.employee_id) DESC, ec1.employee_id ASC LIMIT 1
);
RETURN RESULT;
END;
 
UPDATE `employees_clients` AS ec
JOIN `clients` AS c
SET ec.employee_id = use_it()
WHERE ec.employee_id = ec.client_id;