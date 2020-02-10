CREATE TABLE `item_types` (
  `item_type_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
PRIMARY KEY (`item_type_id`));
  
CREATE TABLE `items` (
  `item_id` INT NOT NULL,
  `name` VARCHAR(50) NULL,
  `item_type_id` INT NULL,
PRIMARY KEY (`item_id`),
CONSTRAINT `fk_items_item_type`
FOREIGN KEY (`item_type_id`)
REFERENCES `item_types` (`item_type_id`));

CREATE TABLE `cities` (
  `city_id` INT NOT NULL,
  `name` VARCHAR(50) NULL,
  PRIMARY KEY (`city_id`));

CREATE TABLE `customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `birthday` DATE NULL,
  `city_id` INT NULL,
  PRIMARY KEY (`customer_id`),
  CONSTRAINT `fk_customers_cities`
    FOREIGN KEY (`city_id`)
    REFERENCES `cities` (`city_id`));

CREATE TABLE `orders` (
   `order_id` INT NOT NULL PRIMARY KEY,
   `customer_id` INT,
CONSTRAINT `fk_orders_customers`
FOREIGN KEY (`customer_id`)
REFERENCES `customers` (`customer_id`)
);
    
CREATE TABLE `order_items` (
  `order_id` INT NOT NULL,
  `item_id` INT NOT NULL,
PRIMARY KEY (`order_id`, `item_id`),

CONSTRAINT `fk_order_items_orders`
FOREIGN KEY(`order_id`)
REFERENCES `orders`(`order_id`),

CONSTRAINT `fk_order_items_items`
FOREIGN KEY(`item_id`)
REFERENCES `items`(`item_id`)
);