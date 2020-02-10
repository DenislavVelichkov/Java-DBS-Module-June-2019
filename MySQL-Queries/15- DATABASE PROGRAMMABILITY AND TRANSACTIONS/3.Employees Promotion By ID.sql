DELIMITER $$
CREATE PROCEDURE usp_raise_salary_by_id(id int)
BEGIN
	START TRANSACTION;
	IF((SELECT count(employee_id) FROM employees WHERE employee_id = id) <> 1) THEN
	ROLLBACK;
	ELSE
		UPDATE employees AS e SET salary = salary * 1.05 
		WHERE e.employee_id = id;
	END IF; 
END$$
