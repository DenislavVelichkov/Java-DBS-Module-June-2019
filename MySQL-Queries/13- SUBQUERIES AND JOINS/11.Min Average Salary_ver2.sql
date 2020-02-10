SELECT AVG(`salary`) AS 'min_average_salary'
FROM employees
GROUP BY `department_id`
ORDER BY AVG(salary) LIMIT 1;