DELIMITER $$

CREATE TABLE `logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `old_sum` decimal(14,4) DEFAULT NULL,
  `new_sum` decimal(14,4) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
);$$

CREATE TRIGGER tr_notification_emails
AFTER UPDATE
ON accounts
FOR EACH ROW
BEGIN
	INSERT INTO `logs`(account_id, old_sum, new_sum)
	VALUES (OLD.id, OLD.balance, NEW.balance);
END;$$


CREATE TABLE `notification_emails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);$$

CREATE TRIGGER tr_logs_update
AFTER INSERT
ON `logs`
FOR EACH ROW
BEGIN
	INSERT INTO `notification_emails`(`recipient`, `subject`, `body`)
	VALUES (NEW.account_id,
    concat('Balance change for account:',' ', NEW.account_id),
    concat('On ', NOW(), ' your balance was changed from ', NEW.old_sum,' to ', NEW.new_sum, '.')
    );
END;$$

