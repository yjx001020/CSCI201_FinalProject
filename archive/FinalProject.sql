DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;
USE FinalProject;

CREATE TABLE Assignments (
	idAssignment INT PRIMARY KEY NOT NULL,
    Assignment VARCHAR(50) NOT NULL
);

CREATE TABLE StudentInQueue(
	idStudent INT PRIMARY KEY NOT NULL,
    idQueue INT NOT NULL, /* foreign key */
    AssignmentType INT NOT NULL,
    OrderinQueue INT NOT NULL
);

CREATE TABLE Queue(
	idQueue INT PRIMARY KEY NOT NULL,
    Faculty VARCHAR(50) NOT NULL,
    BeginningTime VARCHAR(50) NOT NULL,
    EndingTime VARCHAR(50) NOT NULL,
    NumCurrentStudents INT(11)
);

CREATE TABLE Users(
	idUser INT PRIMARY KEY NOT NULL,
    UserName VARCHAR(50) NOT NULL,
    UserPassword VARCHAR(50) NOT NULL,
    Fname VARCHAR(50) NOT NULL,
    Lname VARCHAR(50) NOT NULL,
    isFaculty TINYINT
);

/*
SELECT * FROM Assignments;
SELECT * FROM StudentInQueue;
SELECT * FROM Queue;
SELECT * FROM Users;
*/

