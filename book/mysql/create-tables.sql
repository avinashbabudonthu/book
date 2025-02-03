-- Simple create table
create table emp
(
	id varchar(50),
	name varchar(50)
);

-- Table with primary key
create table emp
(
	id varchar(50),
	name varchar(50),
	primary key(id)
);

-- Table with primary key auto increment
create table emp
(
	id bigint auto_increment,
	name varchar(50),
	primary key(id)
);

-- Table with date column
create table student
(
	id bigint auto_increment,
	name varchar(50),
	course varchar(50),
	joining_date datetime,
	primary key(id)
);

-- Table with datetime column
create table student
(
	id bigint auto_increment,
	name varchar(50),
	course varchar(50),
	joining_date datetime,
	primary key(id)
);

-- Table with foreign key
create table sales
(
	purchase_number int auto_increment,
    date_of_pusrchase date,
    customer_id int,
    item_code varchar(50),
    primary key(purchase_number),
    foreign key(customer_id) references customers(customer_id)
);

-- Table with unique key
create table person
(
	id int auto_increment,
	first_name varchar(50),
	email_id varchar(50),
	primary key(id),
	unique key(email_id)
);

-- Table with default constraint
create table emp
(
	id varchar(50),
	name varchar(50),
	bonus int default 0,
	primary key(id)
);

-- Table with Non Null constraint
CREATE TABLE emp (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE
);