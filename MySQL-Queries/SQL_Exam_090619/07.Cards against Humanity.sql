SELECT cl.`id`, concat(card.`card_number`, ' : ', cl.`full_name`) AS card_token FROM cards AS card
JOIN bank_accounts AS bank_acc
ON bank_acc.client_id = card.id
JOIN clients AS cl
ON cl.id = bank_acc.client_id
WHERE bank_acc.client_id = cl.id
ORDER BY card.`id` DESC;