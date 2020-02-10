SELECT COUNT(c.`country_code`) AS 'country_count', mc.country_code, mc.mountain_id FROM countries AS c
LEFT JOIN mountains_countries AS mc
ON c.`country_code` = mc.`country_code`
WHERE mc.mountain_id IS NULL;
