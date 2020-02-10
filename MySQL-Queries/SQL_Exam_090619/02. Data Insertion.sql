INSERT INTO cards(card_number, card_status, bank_account_id)
SELECT 
	RIGHT(cl.full_name, length(cl.full_name)) AS card_number,
	'Active' AS card_status,
	cl.id AS bank_account_id
FROM clients AS cl
WHERE cl.id BETWEEN 191 AND 200
ORDER BY cl.id, cards.id;