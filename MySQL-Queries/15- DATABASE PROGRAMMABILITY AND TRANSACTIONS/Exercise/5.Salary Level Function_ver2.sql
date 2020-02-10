DELIMITER $$

CREATE FUNCTION ufn_get_salary_level(salary DOUBLE(10,4))
RETURNS VARCHAR(8)
BEGIN
	DECLARE result VARCHAR(8);
   IF salary < 30000 THEN SET result := 'Low';
   ELSEIF salary BETWEEN 30000 AND 50000 THEN SET result := 'Average';
   ELSE SET result := 'High';
   END IF;
   RETURN result;
END$$