DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;
USE FinalProject;

CREATE TABLE Queue (
  idQueue INT NOT NULL AUTO_INCREMENT,
  queueStatus VARCHAR(45) NULL,
  zoomLink VARCHAR(150) NULL,
  announcement VARCHAR(45) NULL,
  PRIMARY KEY (idQueue));

CREATE TABLE StudentInQueue (
  OrderinQueue INT NOT NULL AUTO_INCREMENT,
  idStudent VARCHAR(45) NULL,
  studentName VARCHAR(45) NULL,
  topic VARCHAR(45) NULL,
  description VARCHAR(45) NULL,
  PRIMARY KEY (OrderinQueue));

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
    email varchar(50) not null
);
INSERT INTO Staff(username,email) VALUES ('Mike','limike@usc.edu');
INSERT INTO Staff(username,email) VALUES ('Fiona','mengfeiz@usc.edu');
INSERT INTO Queue(idQueue,queueStatus,zoomLink,announcement) VALUES (1,'Running','https://usc.zoom.us/j/93359954220?pwd=M2lVdUZzOFdyRGdISWE1MkZ6dzJ6Zz09','Office Hour begins! Let us coding!');
INSERT INTO StudentInQueue(OrderinQueue,idStudent,studentName,topic,description) VALUES (1,1234,"Jessi","question","What's a websocket?");
INSERT INTO StudentInQueue(OrderinQueue,idStudent,studentName,topic,description) VALUES (2,12345,"Chengxi","Checkoff","Lab12 checkOff");
INSERT INTO Users(idUser,UserName,UserPassword,Fname,Lname,isFaculty) 
VALUES (123,'Mike',"123456","Mike","Li",1);
INSERT INTO Users(idUser,UserName,UserPassword,Fname,Lname,isFaculty) 
VALUES (701,'Fiona',"123456","Fiona","Zhang",1);
INSERT INTO Users(idUser,UserName,UserPassword,Fname,Lname,isFaculty) 
VALUES (1234,'Jessie',"123456","Jessie","Yang",0);
INSERT INTO Users(idUser,UserName,UserPassword,Fname,Lname,isFaculty) 
VALUES (12345,'Chengxi',"123456","Chengxi","Xu",0);
