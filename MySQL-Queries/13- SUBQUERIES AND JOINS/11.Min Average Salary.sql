SELECT MIN(`min_avg_salary`) AS min_avg_salary
FROM
(
	SELECT AVG(`salary`) AS min_avg_salary FROM employees
    GROUP BY `department_id`
) AS min_salaries;