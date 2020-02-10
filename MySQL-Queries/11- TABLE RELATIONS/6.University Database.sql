CREATE TABLE subjects (
  `subject_id` INT NOT NULL,
  `subject_name` VARCHAR(50),
PRIMARY KEY (`subject_id`));

CREATE TABLE majors (
   `major_id` INT(11) NOT NULL PRIMARY KEY,
   `name` VARCHAR(50) NULL
);

CREATE TABLE students (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `student_number` VARCHAR(12) NULL,
  `student_name` VARCHAR(50) NULL,
  `major_id` INT,
PRIMARY KEY (`student_id`),

CONSTRAINT `fk_students_majors`
FOREIGN KEY (`major_id`)
REFERENCES `majors` (`major_id`));
    
CREATE TABLE payments (
   `payment_id` INT(11) NOT NULL PRIMARY KEY,
   `payment_date` DATE,
   `payment_amount` DECIMAL(8,2),
   `student_id` INT(11),
CONSTRAINT `fk_payments_students`
FOREIGN KEY (`student_id`)
REFERENCES `students` (`student_id`)
);
    
CREATE TABLE agenda (
  `student_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
PRIMARY KEY (`student_id`, `subject_id`),

CONSTRAINT `fk_subjects_students_students`
FOREIGN KEY(`student_id`)
REFERENCES `students`(`student_id`),

CONSTRAINT `fk_subjects_students_subjects`
FOREIGN KEY(`subject_id`)
REFERENCES `subjects`(`subject_id`)
);