SELECT c.`country_name`, r.`river_name` FROM rivers AS `r`
RIGHT JOIN countries_rivers AS cr
ON cr.`river_id` = r.`id`
RIGHT JOIN countries AS c
ON c.`country_code` = cr.`country_code`
WHERE c.`continent_code` = 'AF'
ORDER BY c.`country_name` LIMIT 5;
