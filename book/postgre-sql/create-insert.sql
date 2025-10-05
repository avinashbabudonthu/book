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
	-- user_id INTEGER REFERENCES users(id)
	-- user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
	
	user_id INTEGER,
	CONSTRAINT photos_user_id_fk FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
	-- Following are on delete options
	-- ON DELETE RESTRICT
	-- ON DELETE NO ACTION
	-- ON DELETE CASCADE
	-- ON DELETE SET NULL
	-- ON DELETE SET DEFAULT
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

-- Database: 001-practice
-- DROP DATABASE IF EXISTS "001-practice";

CREATE DATABASE "001-practice"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE "001-practice" IS 'Postgres practice database';

-- SCHEMA: public

-- DROP SCHEMA IF EXISTS public ;

CREATE SCHEMA IF NOT EXISTS public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;

-- Step 1: Create the user
CREATE USER your_username WITH PASSWORD 'your_password';

-- Step 2: Grant all privileges on the schema
GRANT ALL PRIVILEGES ON SCHEMA public TO your_username;

-- Step 3: Grant all privileges on all tables in the schema
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO your_username;

-- Step 4: Grant all privileges on all sequences in the schema
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO your_username;

-- Step 5: Grant all privileges on all functions in the schema (optional)
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO your_username;

-- Step 6: Allow future objects to be accessible
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO your_username;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO your_username;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON FUNCTIONS TO your_username;

-- ADD NOT NULL CONSRAINT
CREATE TABLE person
(
 id SERIAL PRIMARY KEY,
 first_name VARCHAR(100) NOT NULL,
 last_name VARCHAR(100)
);

-- ADD NOT NULL TO EXISTING TABLE
ALTER TABLE person ALTER COLUMN last_name SET NOT NULL;

-- DEFAULT VALUE
CREATE TABLE products_2
(
  name VARCHAR(100),
  price INTEGER DEFAULT 999,
  weight INTEGER
);
-- To add default value to existing column
ALTER TABLE products ALTER COLUMN weight SET DEFAULT 1;
SELECT * FROM information_schema.columns WHERE table_name = 'products_2';
SELECT * FROM information_schema.tables WHERE table_name = 'products_2';
SELECT * FROM pg_catalog.pg_tables WHERE tablename = 'products_2';

-- add unique constraint
ALTER TABLE products_2 ADD UNIQUE(name);
ALTER TABLE products_2 DROP CONSTRAINT products_2_name_key;


-- CHECK VALUE
CREATE TABLE products_3
(
  name VARCHAR(100),
  price INTEGER,
  weight INTEGER
);
-- To add default value to existing column
ALTER TABLE products_3 ADD CHECK (price > 0);
ALTER TABLE products_3 ADD CHECK (price < 1000);