-- show databases;
-- create database if not exists practice;
-- create user 'user1'@'localhost' identified by 'Password22092025';
-- grant all on practice.* to 'user1'@'localhost';
-- select * from mysql.user;
-- drop user 'user1'@'localhost';
-- drop database practice;

USE practice;
DROP TABLE IF EXISTS person;

CREATE TABLE person  (
    person_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

SELECT * FROM person;