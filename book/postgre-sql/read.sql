-- read queries
SELECT * FROM emp;
SELECT * FROM emp WHERE sal > 2000;
SELECT * FROM emp WHERE sal BETWEEN 2000 AND 4000;
SELECT * FROM emp WHERE sal IN (2850, 2450);
SELECT * FROM emp WHERE sal NOT IN (2850, 2450);
SELECT * FROM dept;
SELECT * FROM salgrade;
SELECT * FROM cities;

-- DUAL not needed
SELECT NULL = NULL;
SELECT 1 + 1;

SELECT name, country FROM cities;

SELECT name, country, population, area, population/area as density FROM cities;

-- string concatination
SELECT name || ', ' || country AS name_country FROM cities;
SELECT CONCAT(name, ', ', country) AS name_country FROM cities;

-- convert to lower case
SELECT LOWER(name) AS name_lower_case FROM cities;

-- convert to upper case
SELECT UPPER(name) AS name_upper_case FROM cities;
SELECT CONCAT(UPPER(name), ', ', UPPER(country)) AS name_country FROM cities;
SELECT UPPER(CONCAT(name, ', ', country)) AS name_country FROM cities;

-- length
SELECT LENGTH(name) AS name_length FROM cities;

SELECT * FROM cities WHERE area > 2;

-- Describe
select column_name from information_schema.columns where table_name = 'emp';

-- Date Difference in Years
-- Difference between Oct 02, 2011 and Jan 01, 2012 in years
SELECT DATE_PART('year', '2012-01-01'::date) - DATE_PART('year', '2011-10-02'::date);
-- Result: 1

-- Date Difference in Months
-- Difference between Oct 02, 2011 and Jan 01, 2012 in months
SELECT (DATE_PART('year', '2012-01-01'::date) - DATE_PART('year', '2011-10-02'::date)) * 12 +
            (DATE_PART('month', '2012-01-01'::date) - DATE_PART('month', '2011-10-02'::date));
-- Result: 3

-- Date Difference in Days
-- Difference between Dec 29, 2011 23:00 and Dec 31, 2011 01:00 in days
SELECT DATE_PART('day', '2011-12-31 01:00:00'::timestamp - '2011-12-29 23:00:00'::timestamp);
-- Result: 1

-- Date Difference in Weeks
-- Difference between Dec 22, 2011 and Dec 31, 2011 in weeks
SELECT TRUNC(DATE_PART('day', '2011-12-31'::timestamp - '2011-12-22'::timestamp)/7);
-- Result: 1

-- Datetime Difference in Hours
-- Difference between Dec 30, 2011 08:55 and Dec 30, 2011 9:05 in weeks
SELECT DATE_PART('day', '2011-12-30 08:55'::timestamp - '2011-12-30 09:05'::timestamp) * 24 + 
          DATE_PART('hour', '2011-12-30 08:55'::timestamp - '2011-12-30 09:05'::timestamp);
-- Result: 0

-- Datetime Difference in Minutes
-- Difference between Dec 30, 2011 08:54:55 and  Dec 30, 2011 08:56:10 in minutes
SELECT (DATE_PART('day', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp) * 24 + 
           DATE_PART('hour', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp)) * 60 +
           DATE_PART('minute', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp);
-- Result: 1
 
-- Time only
SELECT DATE_PART('hour', '08:56:10'::time - '08:54:55'::time) * 60 +
            DATE_PART('minute', '08:56:10'::time - '08:54:55'::time);
-- Result: 1

-- Datetime Difference in Seconds
-- Difference between Dec 30, 2011 08:54:55 and  Dec 30, 2011 08:56:10 in seconds
SELECT ((DATE_PART('day', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp) * 24 + 
            DATE_PART('hour', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp)) * 60 +
            DATE_PART('minute', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp)) * 60 +
            DATE_PART('second', '2011-12-30 08:56:10'::timestamp - '2011-12-30 08:54:55'::timestamp);
-- Result: 75
 
-- Time only
SELECT (DATE_PART('hour', '08:56:10'::time - '08:54:55'::time) * 60 +
             DATE_PART('minute', '08:56:10'::time - '08:54:55'::time)) * 60 +
             DATE_PART('second', '08:56:10'::time - '08:54:55'::time);
-- Result: 75

-- current date
-- The PostgreSQL CURRENT_DATE function returns the current date (the system date on the machine running PostgreSQL) as a value in the 'YYYY-MM-DD' format. 
-- In this format, ‘YYYY’ is a 4-digit year, ‘MM’ is a 2-digit month, and ‘DD’ is a 2-digit day. The returned value is a date data type.
select current_date;

-- current date with timestamp
select current_timestamp;

-- format date - current date in YYYY_MM_DD format
SELECT TO_CHAR(CURRENT_TIMESTAMP, 'YYYY_MM_DD') as VALUE;

-- count number of partitions on table - tab
SELECT count(*) AS partitions FROM pg_catalog.pg_inherits WHERE inhparent = 'tab'::regclass;
SELECT * FROM pg_catalog.pg_inherits WHERE inhparent = 'tab'::regclass;
SELECT
    nmsp_parent.nspname AS parent_schema,
    parent.relname      AS parent,
    nmsp_child.nspname  AS child_schema,
    child.relname       AS child
FROM pg_inherits
    JOIN pg_class parent            ON pg_inherits.inhparent = parent.oid
    JOIN pg_class child             ON pg_inherits.inhrelid   = child.oid
    JOIN pg_namespace nmsp_parent   ON nmsp_parent.oid  = parent.relnamespace
    JOIN pg_namespace nmsp_child    ON nmsp_child.oid   = child.relnamespace
WHERE parent.relname='parent_table_name';

-- partition details
select * from pg_class where relispartition is true;
select * from pg_class where relispartition is FALSE;

-- partitions of table - tab
SELECT * FROM PG_CLASS WHERE RELNAME LIKE 'tab%';

-- rownum or limit number of rows
SELECT * FROM EMP LIMIT 10;

-- last n days - below example is last 90 days
-- both formats of below queries executes fine
select * from employee where joining_date_time_stamp < current_date - interval 'n days';
select * from employee where joining_date_time_stamp < current_date - interval '90 days';
select * from employee where joining_date_time_stamp < current_date - interval 'n' days;
select * from employee where joining_date_time_stamp < current_date - interval '90' days;

-- select queries
select * from film;
SELECT title FROM film;
SELECT film_id, title FROM film;
SELECT film_id, title FROM film ORDER BY film_id ASC;
SELECT * FROM actor;
SELECT first_name FROM actor ORDER BY first_name DESC;
SELECT * FROM customer;
SELECT first_name, last_name, email FROM customer;

-- Describe
SELECT * FROM information_schema.columns;
SELECT table_name, column_name FROM information_schema.columns WHERE table_name = 'customer';

-- get list of tables
SELECT * FROM information_schema.tables;
SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE';
SELECT * FROM information_schema.tables WHERE table_schema = 'pg_catalog' AND table_type = 'VIEW';
SELECT * FROM pg_catalog.pg_tables;

-- get list of users
SELECT * FROM pg_catalog.pg_user;

-- count queries
select count(*) from film;
select count(*) from actor;

-- last n days - below example is last 90 days
-- both formats of below queries executes fine
select * from employee where joining_date_time_stamp < current_date - interval 'n days';
select * from employee where joining_date_time_stamp < current_date - interval '90 days';
select * from employee where joining_date_time_stamp < current_date - interval 'n' days;
select * from employee where joining_date_time_stamp < current_date - interval '90' days;

-- count number of partitions on table - tab
SELECT count(*) AS partitions FROM pg_catalog.pg_inherits WHERE inhparent = 'emp'::regclass;

SELECT * FROM pg_catalog.pg_inherits WHERE inhparent = 'emp'::regclass;

SELECT
    nmsp_parent.nspname AS parent_schema,
    parent.relname      AS parent,
    nmsp_child.nspname  AS child_schema,
    child.relname       AS child
FROM pg_inherits
    JOIN pg_class parent            ON pg_inherits.inhparent = parent.oid
    JOIN pg_class child             ON pg_inherits.inhrelid   = child.oid
    JOIN pg_namespace nmsp_parent   ON nmsp_parent.oid  = parent.relnamespace
    JOIN pg_namespace nmsp_child    ON nmsp_child.oid   = child.relnamespace
WHERE parent.relname='parent_table_name';

-- partition details
select * from pg_class where relispartition is true;
select * from pg_class where relispartition is FALSE;

-- partitions of table - tab
SELECT * FROM PG_CLASS WHERE RELNAME LIKE 'emp%';

-- user one-to-many photos - refer create.sql
SELECT username, url FROM users JOIN photos ON users.id = photos.user_id

---------------------------------------------------------------------------------
-------------- Refer practice-queries.sql for below queries ---------------------
---------------------------------------------------------------------------------
SELECT * FROM users;
SELECT * FROM photos;
SELECT * FROM comments;
SELECT * FROM comments ORDER BY user_id;

SELECT 
    c.contents, 
    c.photo_id,
    u.username,
    u.id
FROM comments c 
JOIN users u ON u.id = c.user_id;

SELECT
    c.contents,
    p.url
FROM comments c
JOIN photos p ON p.id = c.photo_id;

SELECT
    c.id,
    p.id
FROM comments c
JOIN photos p ON p.id = c.photo_id;

-- 
SELECT
 c.contents,
 p.url,
 u.username
FROM comments c
JOIN photos p ON p.id = c.photo_id
JOIN users u ON u.id = p.user_id
WHERE c.user_id = p.user_id;

SELECT
 c.contents,
 p.url,
 u.username
FROM comments c
JOIN photos p ON p.id = c.photo_id
JOIN users u ON u.id = c.user_id AND u.id = p.user_id;

-- 
SELECT
 c.contents,
 p.url,
 u.username
FROM comments c
JOIN photos p ON p.id = c.photo_id
JOIN users u ON u.id = p.user_id
WHERE c.user_id <> p.user_id;

--
SELECT
 a.name,
 b.title,
 r.rating
FROM reviews r
JOIN books b ON b.id = r.book_id 
JOIN authors a ON a.id = b.author_id
WHERE r.reviewer_id = b.author_id;

-- GROUP BY
SELECT user_id FROM comments GROUP BY user_id;
SELECT user_id FROM comments GROUP BY user_id ORDER BY user_id;
SELECT user_id, COUNT(user_id) FROM comments GROUP BY user_id ORDER BY user_id;
SELECT user_id, COUNT(id) FROM comments GROUP BY user_id ORDER BY user_id;
SELECT user_id, MAX(id) FROM comments GROUP BY user_id ORDER BY user_id;
SELECT photo_id, COUNT(id) FROM comments GROUP BY photo_id;
SELECT photo_id, COUNT(id) FROM comments GROUP BY photo_id HAVING COUNT(id) > 20;
SELECT photo_id, COUNT(id) FROM comments GROUP BY photo_id HAVING COUNT(id) >= 20;
SELECT photo_id, COUNT(id) FROM comments WHERE photo_id < 3 GROUP BY photo_id HAVING COUNT(id) > 2;
SELECT author_id, COUNT(id) FROM books GROUP BY author_id;
SELECT 
a.name, COUNT(*) 
FROM books b
JOIN authors a ON a.id = b.author_id
GROUP BY a.name;

SELECT 
    manufacturer,
    SUM(price * units_sold) AS total_revenue
FROM phones
GROUP BY manufacturer 
HAVING SUM(price * units_sold) > 2000000;

-- AGGREGATES
SELECT COUNT(*) FROM comments;
SELECT MAX(user_id) FROM comments;