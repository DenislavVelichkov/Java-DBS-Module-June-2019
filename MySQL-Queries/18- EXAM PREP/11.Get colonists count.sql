DELIMITER $$

CREATE FUNCTION udf_count_colonists_by_destination_planet (planet_name VARCHAR (30))
RETURNS INT
BEGIN
DECLARE tot_sum INT;
SET tot_sum := 
(SELECT COUNT(cl.`id`) FROM colonists AS cl
	JOIN travel_cards AS tc
	ON tc.`colonist_id` = cl.`id`
		JOIN journeys AS js
		ON js.`id` = tc.`journey_id`
			JOIN spaceports AS ports
			ON ports.id = js.destination_spaceport_id
				JOIN planets AS pl
				ON ports.planet_id = pl.id
WHERE pl.name = planet_name
);
    
RETURN tot_sum;
END$$