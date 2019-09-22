create database shool;
USE school;

CREATE TABLE class (
	id INT(10) PRIMARY KEY,
    name NVARCHAR(50),
    description VARCHAR(200)
);

CREATE TABLE student (
	id INT(10) PRIMARY KEY,
    name VARCHAR(50),
    class_id INT(10),
    FOREIGN KEY (id) REFERENCES class (id)
);