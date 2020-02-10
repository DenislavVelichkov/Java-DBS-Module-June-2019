SELECT concat(first_name, ' ', last_name) AS 'name', started_on, COUNT(ec.client_id) AS count_of_clients FROM employees AS emp
JOIN employees_clients AS ec
ON emp.id = ec.employee_id
JOIN clients AS cl ON
cl.id = ec.client_id
GROUP BY `name`
ORDER BY count_of_clients DESC, emp.id LIMIT 5;