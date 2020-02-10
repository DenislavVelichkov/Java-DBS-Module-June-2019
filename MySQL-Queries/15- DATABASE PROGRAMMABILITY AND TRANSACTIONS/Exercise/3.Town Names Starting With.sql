DELIMITER $$

CREATE PROCEDURE  usp_get_towns_starting_with(`town_name` VARCHAR(50))
BEGIN
SELECT t.`name` FROM towns t
WHERE t.`name` LIKE concat(town_name,'','%')
ORDER BY t.`name`;

END$$

DELIMITER ;
CALL usp_get_towns_starting_with('b');