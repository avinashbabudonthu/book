-- Alter Table Scripts
-- Add Columns to Table
ALTER TABLE table_name ADD column_name column-definition;
ALTER TABLE customers ADD customer_name varchar2(45);

-- Add column with default value
ALTER TABLE customers ADD city varchar2(40) DEFAULT 'Hyderabad';

-- Add Multiple columns in a table
ALTER TABLE table_name
  ADD (column_1 column-definition,
       column_2 column-definition,
       ...
       column_n column_definition);
   
ALTER TABLE customers
  ADD (customer_name varchar2(45),
       city varchar2(40) DEFAULT 'Hyderabad');

-- Modify Scripts
-- Modify Column
ALTER TABLE table_name MODIFY column_name column_type;
ALTER TABLE table_name MODIFY COLUMN column_name datatype; // prior version 10G
ALTER TABLE table_name MODIFY column_name datatype; // oracle 10G and later
ALTER TABLE customers MODIFY customer_name varchar2(100) NOT NULL;
ALTER TABLE customers MODIFY city varchar2(75) DEFAULT 'Hyderabad' NOT NULL;

-- Modify Multiple columns
ALTER TABLE table_name
  MODIFY (column_1 column_type,
          column_2 column_type,
          ...
          column_n column_type);
		  
ALTER TABLE customers
  MODIFY (customer_name varchar2(100) NOT NULL,
          city varchar2(75) DEFAULT 'Hyderabad' NOT NULL);

-- Drop Column
ALTER TABLE table_name DROP COLUMN column_name;
ALTER TABLE customers DROP COLUMN customer_name;

-- Rename column
ALTER TABLE table_name RENAME COLUMN old_name TO new_name;
ALTER TABLE customers RENAME COLUMN customer_name TO cname;

-- Rename Table
ALTER TABLE table_name RENAME TO new_table_name;
ALTER TABLE customers RENAME TO contacts;

-- Alter table add check constraint
ALTER TABLE Persons ADD CHECK (Age&gt;=18);
ALTER TABLE Persons ADD CONSTRAINT CHK_PersonAge CHECK (Age>=18 AND City='Hyderabad');
ALTER TABLE suppliers ADD CONSTRAINT check_supplier_name CHECK (supplier_name IN ('IBM', 'Microsoft', 'NVIDIA'));

-- Enable Check constraint
ALTER TABLE table_name ENABLE CONSTRAINT constraint_name;
ALTER TABLE suppliers ENABLE CONSTRAINT check_supplier_id;

-- Disable check constraint
ALTER TABLE table_name DISABLE CONSTRAINT constraint_name;
ALTER TABLE suppliers DISABLE CONSTRAINT check_supplier_id;

-- Drop check constraint
ALTER TABLE Persons DROP CONSTRAINT CHK_PersonAge;

-- Alter table - add foreign key
ALTER TABLE SCHEMA_NAME.EMP ADD FOREIGN KEY (DEPTNO) REFERENCES SCHEMA_NAME.DEPT(DEPTNO)