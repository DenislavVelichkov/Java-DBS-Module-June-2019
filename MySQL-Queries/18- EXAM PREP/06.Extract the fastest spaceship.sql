SELECT ss.`name` AS 'spaceship_name', sp.`name` AS 'spaceport_name' FROM spaceships AS ss
JOIN journeys AS js
ON js.`spaceship_id` = ss.`id`
JOIN spaceports AS sp 
ON js.`destination_spaceport_id` = sp.`id`
ORDER BY ss.`light_speed_rate` DESC LIMIT 1;