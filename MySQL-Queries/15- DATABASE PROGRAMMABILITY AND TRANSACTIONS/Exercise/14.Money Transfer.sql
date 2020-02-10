

DELIMITER $$
CREATE PROCEDURE usp_transfer_money(from_account_id INT(11), to_account_id INT(11), amount DECIMAL(19,4))
BEGIN
START TRANSACTION;
IF (from_account_id IS NULL OR to_account_id IS NULL OR amount IS NULL) THEN ROLLBACK;
ELSEIF (amount <= 0 OR ROUND(amount, 4) <> amount) THEN ROLLBACK;
ELSEIF ((SELECT `id` FROM accounts WHERE id = from_account_id) IS NULL OR
		(SELECT `id` FROM accounts WHERE id = to_account_id) IS NULL) THEN ROLLBACK;
ELSEIF ((SELECT `balance` FROM accounts WHERE id = from_account_id) - amount < 0) THEN ROLLBACK;
ELSE
UPDATE accounts AS acc
SET `balance` = `balance` + amount WHERE acc.`id` = to_account_id;
UPDATE accounts AS acc
SET `balance` = `balance` - amount WHERE acc.`id` = from_account_id;
END IF;
COMMIT;
END;$$

CALL usp_transfer_money(1,2,0.0001);$$
SELECT * FROM accounts WHERE id IN(1,2);$$

DROP PROCEDURE usp_transfer_money;

