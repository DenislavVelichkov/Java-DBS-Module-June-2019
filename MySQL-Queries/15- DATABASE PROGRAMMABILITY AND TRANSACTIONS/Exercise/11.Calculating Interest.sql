DELIMITER $$
CREATE FUNCTION ufn_calculate_future_value(init_sum DOUBLE, rate DOUBLE, years INT)
RETURNS DOUBLE
BEGIN
DECLARE future_value DOUBLE;
SET future_value := init_sum * pow(1 + rate, years);
RETURN future_value;
END;$$

CREATE PROCEDURE usp_calculate_future_value_for_account(account_id INT, interest_rate DOUBLE)
BEGIN
	SELECT ah.`id`, ah.`first_name`, ah.`last_name`, ac.`balance` AS'current_balance',
    cast(ROUND(ufn_calculate_future_value(ac.`balance`, interest_rate, 5), 4) AS CHAR) AS 'balance_in_5_years'
    FROM account_holders AS ah
    JOIN accounts AS ac
    ON ac.`account_holder_id` = ah.`id`
    WHERE ac.`id` = account_id;
END;$$

DELIMITER ;
CALL usp_calculate_future_value_for_account(1, 1.1);

# CREATE FUNCTION ufn_calculate_future_value(init_sum DECIMAL(19,4), rate DECIMAL(19,4), years INT(4))
# RETURNS DECIMAL(19,4)
# BEGIN
#     DECLARE future_value DECIMAL(19,4);
#    
#     SET future_value := init_sum * POW(1 + rate, years);
#    
#     RETURN future_value;
# END;
#  
# CREATE PROCEDURE usp_calculate_future_value_for_account(acc_id INT, int_rate DECIMAL(10,4))
# BEGIN
#     SELECT ac.id, ah.first_name, ah.last_name, ac.balance AS 'current_balance',
#            ufn_calculate_future_value(ac.balance, int_rate, 5) AS 'balance_in_5_years'
#     FROM account_holders AS ah
#     JOIN accounts AS ac
#     ON ac.account_holder_id = ah.id
#     WHERE ac.id = acc_id;
# END;