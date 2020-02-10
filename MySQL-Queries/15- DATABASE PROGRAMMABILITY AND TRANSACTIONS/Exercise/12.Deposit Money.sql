CREATE PROCEDURE usp_deposit_money(account_id INT, money_amount DECIMAL)
BEGIN
START TRANSACTION;
IF (money_amount < 0 AND ROUND(money_amount, 4) <> money_amount) THEN
ROLLBACK;
ELSE
UPDATE accounts AS acc
SET acc.`balance` = acc.`balance` + money_amount WHERE account_id = acc.`id`;
END IF;
COMMIT;
END