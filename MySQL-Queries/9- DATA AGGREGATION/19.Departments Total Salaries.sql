SELECT `department_id`, SUM(`salary`) FROM `employees`
GROUP BY `department_id`;