DROP TABLE IF EXISTS 'student1';
CREATE TABLE student1 (student_id INT NOT_NULL ,student_name VARCHAR NOT_NULL, email VARCHAR NOT_NULL, phoneNumber VARCHAR, PRIMARY_KEY (student_id), CONSTRAINT fk_monitor FOREIGN_KEY (monitor_Id) REFERENCES monitor(ID));

DROP TABLE IF EXISTS 'student2';
CREATE TABLE student2 (student_id INT NOT_NULL ,student_name VARCHAR NOT_NULL, email VARCHAR NOT_NULL, phoneNumber VARCHAR, PRIMARY_KEY (student_id), CONSTRAINT fk_monitor FOREIGN_KEY (monitor_Id) REFERENCES monitor(ID));

Insert into student1(student_id, student_name, email, phoneNumber) values(1,John,john@gmail.com,1234);

