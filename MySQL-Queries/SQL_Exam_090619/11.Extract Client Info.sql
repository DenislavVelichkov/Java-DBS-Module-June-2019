DELIMITER $$
CREATE PROCEDURE udp_clientinfo(full_name VARCHAR(50))
BEGIN
SELECT cl.`full_name`, cl.`age`, bc.`account_number`, concat('$',bc.`balance`) AS balance FROM clients AS cl
JOIN bank_accounts AS bc
ON bc.`client_id` = cl.`id`
WHERE cl.`full_name` = full_name ;
END;$$

CALL udp_clientinfo('Hunter Wesgate');