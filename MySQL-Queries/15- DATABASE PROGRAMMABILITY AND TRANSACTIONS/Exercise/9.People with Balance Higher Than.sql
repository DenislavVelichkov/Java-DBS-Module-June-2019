DELIMITER $$
CREATE PROCEDURE usp_get_holders_with_balance_higher_than(given_value INT)
BEGIN
SELECT `first_name`, `last_name` FROM account_holders AS ah
JOIN accounts AS acc 
ON acc.account_holder_id = ah.id
GROUP BY ah.id
HAVING SUM(`balance`) > given_value
ORDER BY acc.id;
END$$

CALL usp_get_holders_with_balance_higher_than(7000);