DROP PROCEDURE usp_get_employees_by_salary_level;
DROP FUNCTION ufn_get_salary_leve;

DELIMITER $$
CREATE PROCEDURE usp_get_employees_by_salary_level(level_salary VARCHAR(20))
BEGIN
SELECT first_name, last_name FROM employees AS e
WHERE (SELECT ufn_get_salary_level(e.salary)) = level_salary
ORDER BY first_name DESC, last_name DESC;
END$$

CREATE FUNCTION ufn_get_salary_level(e_salary DOUBLE(10,2))
RETURNS VARCHAR(50)
BEGIN
CASE 
WHEN e_salary < 30000.00 THEN RETURN 'Low';
WHEN e_salary BETWEEN 30000.00 AND 50000.00 THEN RETURN 'Average';
ELSE RETURN 'High';
END CASE;
END$$


DELIMITER ;
CALL usp_get_employees_by_salary_level('High');