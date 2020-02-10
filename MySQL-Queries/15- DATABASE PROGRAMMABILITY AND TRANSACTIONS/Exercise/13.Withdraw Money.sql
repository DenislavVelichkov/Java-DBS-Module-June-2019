DELIMITER $$
CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DOUBLE)
BEGIN
START TRANSACTION;
IF ((SELECT acc.`balance` FROM accounts AS acc WHERE account_id = acc.`id`)) - money_amount < 0 OR money_amount < 0 THEN
ROLLBACK;
ELSE 
UPDATE accounts AS acc
SET acc.`balance` = ROUND(acc.`balance` - money_amount, 4) WHERE account_id = acc.`id`;
END IF;
END$$

CALL usp_withdraw_money (3, 0.00001);
select * from accounts
WHERE id = 3;