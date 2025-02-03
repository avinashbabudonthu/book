-- Create Alter Modify Rename Drop  Table And Column Scripts
-- Create Table Scripts
-- Table creation with BLOB column and specify column size
create table pictures(name varchar(32) not null primary key, pic blob(16M));

-- Create table with BLOB column. Do not specify the column size
CREATE TABLE a_table (blob_col BLOB);

-- Create table with CLOB column
CREATE TABLE a_table (blob_col CLOB);

-- Constraint along with column
CREATE TABLE Persons (
    ID int NOT NULL,
    LastName varchar(255) NOT NULL,
    FirstName varchar(255),
    Age int CHECK (Age&gt;=18)
);

-- Check Constraint with IN clause along with column
CREATE TABLE Persons (
    ID int NOT NULL,
    LastName varchar(255) NOT NULL,
    FirstName varchar(255),
	Active number(1,0) CHECK (Active in (1,0))    
);

-- Declare constraint separately
CREATE TABLE Persons (
    ID int NOT NULL,
    LastName varchar(255) NOT NULL,
    FirstName varchar(255),
    Age int,
    City varchar(255),
    CONSTRAINT CHK_Person CHECK (Age&gt;=18 AND City='Hyderabad')
);

CREATE TABLE suppliers
(
  supplier_id numeric(4),
  supplier_name varchar2(50),
  CONSTRAINT check_supplier_name
  CHECK (supplier_name = upper(supplier_name))
);

-- Add Foreign Key along with column
CREATE TABLE EMP
(
 EMPNO NUMBER(32)
,ENAME VARCHAR2(50)
,DEPTNO NUMBER(32) CONSTRAINT DEPTNO_FK REFERENCES DEPT(DEPTNO) ON DELETE CASCADE
,JOB VARCHAR2(50)
,SAL NUMBER(32)
,COMM NUMBER(32)
,MGR NUMBER(32) CONSTRAINT MGR_FK REFERENCES EMP(EMPNO)
,HIREDATE DATE
,ACTIVE BOOLEAN
,CONSTRAINT EMPNO_PK PRIMARY KEY(EMPNO)
);