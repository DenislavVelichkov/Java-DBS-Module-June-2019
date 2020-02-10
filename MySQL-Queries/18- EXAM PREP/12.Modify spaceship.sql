DELIMITER $$
CREATE PROCEDURE udp_modify_spaceship_light_speed_rate(spaceship_name VARCHAR(50), light_speed_rate_increse INT(11))
BEGIN
START TRANSACTION;
IF (SELECT `name` FROM spaceships WHERE `name` = spaceship_name) IS NULL 
	THEN SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Spaceship you are trying to modify does not exists.';
	ROLLBACK;
ELSE 
	UPDATE spaceships
    SET `light_speed_rate` = `light_speed_rate` + light_speed_rate_increse;
    COMMIT;
END IF;

END$$