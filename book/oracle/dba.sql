-- start listener
lsnrctl start

-- check listener stats
lsnrctl status

-- all_tables
select * from all_tables;
select * from all_tables where upper(owner) = 'PRACTICE';

-- dba_tables
select * from dba_tables;
select distinct owner from dba_tables order by owner asc;
select * from dba_tables where upper(owner) = upper('practice');

-- get all user tables
SELECT table_name FROM user_tables;

-- get columns of table
SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, CHAR_LENGTH from USER_TAB_COLUMNS where table_name = 'table-name';
SELECT * from USER_TAB_COLUMNS where table_name = 'table-name';;