SELECT pl.`name`, sp.`name` FROM planets AS pl
JOIN spaceports AS sp 
ON sp.`planet_id` = pl.`id`
JOIN journeys AS js
ON js.`destination_spaceport_id` = sp.`id`
WHERE js.`purpose` = 'Educational'
ORDER BY sp.`name` DESC;