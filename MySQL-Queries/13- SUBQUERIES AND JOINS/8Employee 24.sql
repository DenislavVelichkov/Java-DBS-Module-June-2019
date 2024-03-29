SELECT e.`employee_id`, e.`first_name`, IF(DATE(p.`start_date`) >= '2005-01-01', p.`name` = NULL, p.`name`) AS 'project_name' FROM employees AS e
JOIN employees_projects AS ep
ON e.`employee_id` = ep.`employee_id`
JOIN projects AS p
ON ep.`project_id` = p.`project_id`
WHERE e.`employee_id` = 24
ORDER BY p.`name`;