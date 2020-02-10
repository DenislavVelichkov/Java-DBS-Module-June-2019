CREATE TABLE persons (
`person_id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(60) NOT NULL,
`salary` DECIMAL(10, 2) ,
`passport_id` INT UNIQUE
);

INSERT INTO `persons`(
`first_name`, `salary`, `passport_id`
) VALUES(
'Roberto', 43300.00, 102
);

INSERT INTO `persons`(
`first_name`, `salary`, `passport_id`
) VALUES(
'Tom', 56100.00, 103
);

INSERT INTO `persons`(
`first_name`, `salary`, `passport_id`
) VALUES(
'Yana', 60200.00, 101
);

CREATE TABLE passports (
`passport_id` INT PRIMARY KEY AUTO_INCREMENT,
`passport_number` VARCHAR(60) NOT NULL UNIQUE
);

INSERT INTO `passports`(
`passport_id`, `passport_number`
) VALUES(
101, 'N34FG21B'
);

INSERT INTO `passports`(
`passport_number`
) VALUES(
'K65LO4R7'
);

INSERT INTO `passports`(
`passport_number`
) VALUES(
'ZE657QP2'
);

ALTER TABLE `persons` 
ADD CONSTRAINT `fk_persons_passports`
  FOREIGN KEY (`passport_id`)
  REFERENCES `passports` (`passport_id`);
