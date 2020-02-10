CREATE TABLE `exams` (
  `exam_id`INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(65) NOT NULL);
  
CREATE TABLE `students` (
  `student_id`INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(65) NOT NULL);
  
CREATE TABLE `students_exams` (
  `student_id` INT,
  `exam_id` INT,
  CONSTRAINT `pk_students_exams`
  PRIMARY KEY (`student_id`, `exam_id`),
  
  CONSTRAINT `fk_students_exams_students`
  FOREIGN KEY(`student_id`)
  REFERENCES students(`student_id`),
  
  CONSTRAINT `fk_students_exams_exams`
  FOREIGN KEY(`exam_id`)
  REFERENCES exams(`exam_id`)
  );
  
  INSERT INTO exams(`exam_id`, `name`) VALUES
  (101, 'Spring MVC');
  INSERT INTO exams(`name`) VALUES
  ('Neo4j'),
  ('Oracle 11g');
  
  INSERT INTO students(`student_id`, `name`) VALUES
  (1, 'Mila');
  INSERT INTO students(`name`) VALUES
  ('Toni'),
  ('Ron');
  
  INSERT INTO students_exams(`student_id`, `exam_id`) VALUES
  (1, 101),
  (1, 102),
  (2, 101),
  (3, 103),
  (2, 102),
  (2, 103)
  ;

  