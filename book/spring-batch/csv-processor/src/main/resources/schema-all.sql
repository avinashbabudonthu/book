-- show databases;
-- create database if not exists practice;
-- create user 'springbatchuser'@'localhost' identified by 'Password22092025';
-- grant all on springbatch.* to 'springbatchuser'@'localhost';
-- select * from mysql.user;
-- drop user 'springbatchuser'@'localhost';
-- drop database springbatch;

USE springbatch;
DROP TABLE IF EXISTS person;

CREATE TABLE person  (
    person_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

SELECT * FROM person;