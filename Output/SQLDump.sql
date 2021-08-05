DROP TABLE IF EXISTS 'monitor1';
CREATE TABLE monitor1 (monitor_id INT , monitor_name VARCHAR, email VARCHAR, phoneNumber VARCHAR, PRIMARY_KEY (monitor_id));

DROP TABLE IF EXISTS 'student1';
CREATE TABLE student1(student_id INT NOT_NULL ,student_name VARCHAR NOT_NULL, email VARCHAR NOT_NULL, phoneNumber VARCHAR, PRIMARY_KEY (student_id), CONSTRAINT fk_monitor FOREIGN_KEY (monitor_Id) REFERENCES monitor1(ID));

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(1,Aditya,aditya@gmail.com,4567);

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(2,Aditya,aditya@gmail.com,4567);

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(3,Aditya,aditya@gmail.com,4567);

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(4,Aditya,aditya@gmail.com,4567);

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(5,Aditya,aditya@gmail.com,4567);

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(6,Aditya,aditya@gmail.com,4567);

Insert into student1(student_id, student_name, email, phoneNumber) values(1,Aditya,aditya@gmail.com,4567);

Insert into monitor1(monitor_id, monitor_name, email, phoneNumber) values(23,Aditya,aditya@gmail.com,4567);

CREATE TABLE student2 (student_id INT NOT_NULL ,student_name VARCHAR NOT_NULL, email VARCHAR NOT_NULL, phoneNumber VARCHAR, PRIMARY_KEY (student_id), CONSTRAINT fk_monitor FOREIGN_KEY (monitor_Id) REFERENCES monitor2(ID))

DROP TABLE IF EXISTS 'student2';
CREATE TABLE student2 (student_id INT NOT_NULL ,student_name VARCHAR NOT_NULL, email VARCHAR NOT_NULL, phoneNumber VARCHAR, PRIMARY_KEY (student_id), CONSTRAINT fk_monitor FOREIGN_KEY (monitor_Id) REFERENCES monitor2(ID));

Insert into student2(student_id, student_name, email, phoneNumber) values(10,Aditya,aditya@gmail.com,4567);

Insert into student2(student_id, student_name, email, phoneNumber) values(101,Aditya,aditya@gmail.com,4567);

