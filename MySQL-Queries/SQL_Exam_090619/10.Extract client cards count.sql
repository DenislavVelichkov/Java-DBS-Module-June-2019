DELIMITER $$
CREATE FUNCTION udf_client_cards_count(client_name VARCHAR(30))
RETURNS INT
BEGIN
DECLARE result INT;
SET result = (SELECT COUNT(card.id) FROM clients AS cl
JOIN bank_accounts AS bc
ON bc.client_id = cl.id
JOIN cards AS card
ON card.bank_account_id = bc.id
WHERE client_name = `full_name`);
RETURN result; 
END;$$

SELECT c.full_name, udf_client_cards_count('Baxy David') as `cards` FROM clients c
WHERE c.full_name = 'Baxy David';	