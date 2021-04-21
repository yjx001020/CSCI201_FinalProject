DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;
USE FinalProject;

CREATE TABLE Queue (
  idQueue INT NOT NULL AUTO_INCREMENT,
  queueStatus VARCHAR(45) NULL,
  zoomLink VARCHAR(45) NULL,
  announcement VARCHAR(45) NULL,
  PRIMARY KEY (idQueue));

CREATE TABLE StudentInQueue (
  OrderinQueue INT NOT NULL AUTO_INCREMENT,
  idStudent VARCHAR(45) NULL,
  studentName VARCHAR(45) NULL,
  topic VARCHAR(45) NULL,
  description VARCHAR(45) NULL,
  PRIMARY KEY (OrderinQueueQueue));

CREATE TABLE Users(
	idUser INT PRIMARY KEY NOT NULL,
    UserName VARCHAR(50) NOT NULL,
    UserPassword VARCHAR(50) NOT NULL,
    Fname VARCHAR(50) NOT NULL,
    Lname VARCHAR(50) NOT NULL,
    isFaculty TINYINT
);
Create Table Staff(
	ID int(11) primary key not null auto_increment,
    username varchar(50) not null,
);
Insert into Staff(username,email) values ('Mike','limike@usc.edu');
Insert into Staff(username,email) values ('Fiona','mengfeiz@usc.edu');