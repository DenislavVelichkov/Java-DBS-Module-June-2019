DELIMITER $$

CREATE PROCEDURE  usp_get_employees_salary_above(`salary_value` INT)
BEGIN
SELECT `first_name`, `last_name` FROM employees
WHERE `salary_value` >= `salary`
ORDER BY `first_name`, `last_name`, `employee_id`;

END$$

DELIMITER ;
CALL usp_get_employees_salary_above_35000(36000);
