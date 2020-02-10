SELECT pl.`name` AS planet_name, COUNT(js.`id`) AS journey_count FROM planets AS pl
JOIN spaceports AS sp
ON sp.`planet_id` = pl.`id`
JOIN journeys AS js
ON sp.`id` = js.`destination_spaceport_id`
GROUP BY pl.`name`
ORDER BY journey_count DESC, pl.`name`;