DELIMITER $$

CREATE FUNCTION ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50))
RETURNS INT
BEGIN
	DECLARE char_index INT;
    DECLARE symbol VARCHAR(1);
    SET char_index := 1;
    
    WHILE char_index <= char_length(word) DO
		SET symbol := substring(word, char_index, 1);
        IF locate(symbol, set_of_letters) = 0 THEN RETURN 0;
        END IF;
        
        SET char_index = char_index + 1;
    END WHILE;
    RETURN 1;
END$$