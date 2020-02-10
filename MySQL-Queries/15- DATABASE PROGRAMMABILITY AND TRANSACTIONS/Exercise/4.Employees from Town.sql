DELIMITER $$
CREATE PROCEDURE usp_get_employees_from_town(town_name VARCHAR(50))
BEGIN
SELECT e.first_name, e.last_name FROM employees e
JOIN addresses AS ad
ON e.address_id = ad.address_id
JOIN towns as t
ON t.town_id = ad.town_id
WHERE t.name = town_name
ORDER BY e.first_name, e.last_name, e.employee_id;
END$$

CALL usp_get_employees_from_town('Sofia');