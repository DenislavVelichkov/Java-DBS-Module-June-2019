DELIMITER $$
CREATE FUNCTION ufn_calculate_future_value(init_sum DOUBLE, rate DOUBLE, years INT)
RETURNS DOUBLE
BEGIN
DECLARE future_value DOUBLE;
SET future_value := init_sum * pow(1 + rate, years);
RETURN future_value;

END$$