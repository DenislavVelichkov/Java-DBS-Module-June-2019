SELECT e.`employee_id`, e.`first_name`, e2.`employee_id`, e2.`first_name` FROM employees AS e
JOIN employees AS e2
ON e2.`employee_id` = e.`manager_id`
WHERE e.`manager_id` IN (3,7)
ORDER BY e.`first_name`;