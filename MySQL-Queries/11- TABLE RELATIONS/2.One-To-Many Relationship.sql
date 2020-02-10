CREATE SCHEMA cars;
USE `cars`;

CREATE TABLE manufacturers (
`manufacturer_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
`name` VARCHAR(60) NOT NULL,
`established_on` DATE NOT NULL
);

INSERT INTO `manufacturers`(`name`, `established_on`) 
VALUES('BMW', '1916-03-01'),
('Tesla', '2003-01-01'),
('Lada', '1966-05-01');

CREATE TABLE models(
`model_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`name` VARCHAR(60) NOT NULL,
`manufacturer_id` INT NOT NULL
);

INSERT INTO `models`(
`model_id`, `name`, `manufacturer_id`) VALUES(101, 'X1', 1 );

INSERT INTO `models`(`name`, `manufacturer_id`) 
VALUES
('i6', 1 ),
('Model S', 2 ),
('Model X', 2 ),
('Model 3', 2 ),
('Nova', 3 );

ALTER TABLE `models` 
ADD INDEX `fk_models_manufactorers_idx` (`manufacturer_id` ASC) VISIBLE;
ALTER TABLE `models` 
ADD CONSTRAINT `fk_models_manufactorers`
  FOREIGN KEY (`manufacturer_id`)
  REFERENCES `manufacturers` (`manufacturer_id`);

