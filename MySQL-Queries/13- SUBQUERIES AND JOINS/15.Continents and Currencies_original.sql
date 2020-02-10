CREATE TABLE groups_currency
SELECT
    continent_code,
    currency_code,
    count(currency_code) AS currency_usage
FROM countries
GROUP BY continent_code, currency_code
HAVING currency_usage > 1;
 
SELECT
    allgr.continent_code,
    allgr.currency_code,
    allgr.currency_usage
FROM
    groups_currency AS allgr,
    (SELECT
        continent_code,
        currency_code,
        max(currency_usage) AS currency_usage
    FROM
        groups_currency
    GROUP BY continent_code
    ) AS maxims
WHERE allgr.continent_code = maxims.continent_code AND allgr.currency_usage = maxims.currency_usage
ORDER BY
    continent_code,
    currency_code;