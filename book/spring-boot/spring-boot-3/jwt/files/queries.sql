create database practice;
drop database practice;
create user 'practiceUser1'@'localhost' identified by 'Password15042025';
grant all on practice.* to 'practiceUser1'@'localhost';

use practice;

drop table if exists users_roles;
drop table if exists users;
drop table if exists roles;

create table users
(
	id int,
    name varchar(30),
    username varchar(30),
    email varchar(50),
    password varchar(250),
    primary key(id)
);

create table roles
(
	id int,
    name varchar(30),
    primary key(id)
);

create table users_roles
(
	id int,
    user_id int,
    role_id int,
    foreign key(user_id) references users(id),
    foreign key(role_id) references roles(id)
);

INSERT INTO users(id, email, username, password, name) VALUES
(1,'ramesh@gmail.com','ramesh','$2a$10$5PiyN0MsG0y886d8xWXtwuLXK0Y7zZwcN5xm82b4oDSVr7yF0O6em','ramesh'),
(2,'admin@gmail.com','admin','$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu','admin');
commit;

INSERT INTO roles(id, name) VALUES
(1,'ROLE_ADMIN'),(2,'ROLE_USER');
commit;

INSERT INTO users_roles VALUES
(1, 1,2),
(2, 2,1);
commit;