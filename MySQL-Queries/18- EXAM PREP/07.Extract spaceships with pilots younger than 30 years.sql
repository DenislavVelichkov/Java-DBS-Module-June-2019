SELECT ship.`name`, ship.`manufacturer` FROM spaceships AS ship
JOIN journeys AS js
ON js.`spaceship_id` = ship.`id`
JOIN travel_cards AS tc
ON tc.`journey_id` = js.`id`
JOIN colonists AS cl
ON cl.`id` = tc.`colonist_id`
WHERE tc.`job_during_journey` = 'Pilot' AND cl.`birth_date` > date_sub('2019-01-01', INTERVAL 30 YEAR)
ORDER BY ship.`name`;