SELECT br.`name`, COUNT(card.id) AS count_of_cards FROM cards AS card
JOIN bank_accounts AS bc
ON bc.id = card.bank_account_id
JOIN employees_clients AS ec
ON ec.client_id = bc.client_id
JOIN employees AS emp
ON emp.id = ec.employee_id
JOIN branches AS br
ON emp.branch_id = br.id
GROUP BY `name`
ORDER BY count_of_cards DESC, `name`
;