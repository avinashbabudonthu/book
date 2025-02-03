-- use employees database
use employees;

-- delete all records
delete from emp;

-- remove all records from departments table
truncate departments;

-- Remove the department number 10 record from the “departments” table
delete from departments where dept_no = 'd010';
rollback;
commit;