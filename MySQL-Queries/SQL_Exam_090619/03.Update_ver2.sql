UPDATE `employees_clients` `t`
JOIN
(SELECT `em`.`employee_id`, count(`em`.`client_id`) AS `count` FROM `employees_clients` `em` GROUP BY `em`.`employee_id` ORDER BY `count`,`em`.`employee_id`) AS `s`
 
SET `t`.`employee_id` = `s`.`employee_id`
WHERE `t`.`employee_id` = `t`.`client_id`;