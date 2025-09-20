-- Create database
CREATE DATABASE "001-practice"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE "001-practice" IS 'Postgres practice database';

-- create cities table
CREATE TABLE cities
(
    name VARCHAR(100),
    country VARCHAR(100),
    population INTEGER,
    area INTEGER
);

-- insert 1 row in cities table
INSERT INTO cities(name, country, population, area)
VALUES ('One', 'One', 1, 1);

-- insert more rows in cities table
INSERT INTO cities(name, country, population, area)
VALUES 
('Two', 'Two', 2, 2),
('Three', 'Three', 3, 3),
('Four', 'Four', 4, 4);

-- create table with partition
CREATE TABLE MY_TAB (
   ID bigint GENERATED ALWAYS AS IDENTITY,
   CREATE_DATE timestamp NOT NULL,
   DATA text
) PARTITION BY LIST ((CREATE_DATE::DATE));
 
CREATE TABLE MY_TAB_DEF PARTITION OF MY_TAB DEFAULT;

-- insert timestamp
INSERT INTO MY_TAB (CREATE_DATE, DATA) VALUES (CURRENT_TIMESTAMP, 'ONE');

-- create table partitions
CREATE TABLE MEASUREMENT (
    CITY_ID INT NOT NULL,
    LOGDATE DATE NOT NULL,
    PEAKTEMP INT,
    UNITSALES INT
) PARTITION BY RANGE (LOGDATE);

CREATE INDEX ON MEASUREMENT(LOGDATE);

CREATE TABLE MEASUREMENT_Y2006M02 PARTITION OF MEASUREMENT
FOR VALUES FROM ('2006-02-01') TO ('2006-03-01');

CREATE TABLE MEASUREMENT_Y2006M03 PARTITION OF MEASUREMENT
FOR VALUES FROM ('2006-03-01') TO ('2006-04-01');

SELECT * FROM MEASUREMENT;
SELECT * FROM MEASUREMENT_Y2006M02;
SELECT * FROM MEASUREMENT_Y2006M03;

INSERT INTO MEASUREMENT (CITY_ID, LOGDATE, PEAKTEMP, UNITSALES) VALUES (1, '2006-02-02', 1, 1);
INSERT INTO MEASUREMENT (CITY_ID, LOGDATE, PEAKTEMP, UNITSALES) VALUES (2, '2006-02-03', 2, 2);
INSERT INTO MEASUREMENT (CITY_ID, LOGDATE, PEAKTEMP, UNITSALES) VALUES (3, '2006-03-03', 3, 3);

-- partitions on demand - refer - partitions-on-demand.sql

-- primary key with SERIAL type
-- user one-to-many photos
CREATE TABLE users
(
	id SERIAL PRIMARY KEY,
	username VARCHAR(100)
);

INSERT INTO users(username) VALUES ('one');
INSERT INTO users(username)
VALUES
('two'),
('three'),
('four');

SELECT * FROM users;

DROP TABLE photos IF EXISTS;

CREATE TABLE photos
(
	id SERIAL PRIMARY KEY,
	url VARCHAR(100),
	user_id INTEGER REFERENCES users(id)
);

INSERT INTO photos(url, user_id)
VALUES
('http://one.jpg', 1),
('http://two.jpg', 2),
('http://three.jpg', 3),
('http://four.jpg', 4);
commit;

SELECT * FROM photos;
SELECT * FROM photos WHERE user_id = 4;