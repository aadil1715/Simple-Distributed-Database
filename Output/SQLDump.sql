DROP TABLE IF EXISTS 'student10';
CREATE TABLE student10 (student_id INT NOT_NULL ,student_name VARCHAR NOT_NULL, email VARCHAR NOT_NULL, phoneNumber VARCHAR, PRIMARY_KEY (student_id), CONSTRAINT fk_monitor FOREIGN_KEY (monitor_Id) REFERENCES monitor(ID));

