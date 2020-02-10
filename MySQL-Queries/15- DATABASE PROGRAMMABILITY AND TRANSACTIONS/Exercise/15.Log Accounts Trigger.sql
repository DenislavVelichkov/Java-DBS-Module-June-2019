DELIMITER $$

CREATE TABLE `logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `old_sum` decimal(14,4) DEFAULT NULL,
  `new_sum` decimal(14,4) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
);$$

CREATE TRIGGER tr_balance_after_update
AFTER UPDATE
ON accounts
FOR EACH ROW
BEGIN
	INSERT INTO `logs`(account_id, old_sum, new_sum)
	VALUES (OLD.id, OLD.balance, NEW.balance);
END;$$





