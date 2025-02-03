-- Add column to table
-- alter table [table-name] add column [column-name] [data-type];
alter table emp add column designation varchar(200);

-- drop column
alter table departments_dup drop column dept_manager;

-- Add enum column
ALTER TABLE customers ADD COLUMN gender ENUM('M', 'F') AFTER last_name;

-- Add foreign constraint to column with on delete cascade
-- alter table [fk-table-name] add foreign key([fk-column-name]) references [pk-table-name]([pk-column-name]) on delete cascade;
alter table emp add foreign key(dept_no_fk) references dept(dept_no_pk) on delete cascade;

-- Add foreign constraint to column with on delete set null
-- alter table [fk-table-name] add foreign key([fk-column-name]) references [pk-table-name]([pk-column-name]) on delete set null;
alter table emp add foreign key(dept_no_fk) references dept(dept_no_pk) on delete set null;

-- Add datetime column with default value
alter table emp add column create_date datetime default current_timestamp;

-- add multiple columns
alter table employee
    add column created_date datetime not null default CURRENT_TIMESTAMP,
    add column created_by varchar(50) not null default 'db',
    add column updated_date datetime not null default CURRENT_TIMESTAMP,
    add column updated_by varchar(50) not null default 'db'

-- Drop foreign key
-- alter table [table-name] drop foreign key [foreign_key_name];
alter table emp drop foreign key foreign_key_name;

-- Add unique constraint
alter table person add unique key(email_id);

-- Drop index
alter table person drop index unique_key_field;
alter table person drop index email_id;

-- Add default constraint
alter table emp change column bonus bonus int default 0;
alter table departments_dup change column dept_no dept_no char(4) null;
alter table departments_dup change column dept_name dept_name varchar(40) null;

-- Drop default constraint
alter table emp alter column bonus drop default;

-- Not Null constraint to existing column
-- Follow below steps
--  Check the current values of the column if there is any NULL
-- Update the NULL to non-NULL if NULLs exist
-- Modify the column with a NOT NULL constraint
-- ALTER TABLE table_name CHANGE old_column_name new_column_name column_definition;
ALTER TABLE emp CHANGE end_date end_date DATE NOT NULL;

-- Drop Not Null constraint. Use `Alter Table.. modify` statement
-- ALTER TABLE table_name MODIFY column_name column_definition;
alter table emp modify email_id varchar(255) null;

-- Add composite primary key
ALTER table t_student add primary key(id, name);

-- rename table
-- alter table table_name rename to new_table_name;
alter table employee rename to t_employee;

-- rename column name
-- alter table table_name change column old_name new_name column_definition [FIRST | AFTER column_name]
-- FIRST | AFTER column_name : Optional. It tells MySQL where in the table to position the column, if you wish to change its position
-- column_definition : The datatype and definition of the column (NULL or NOT NULL, etc). You must specify the column definition when renaming the column, even if it does not change
alter table t_employee change column mobile_number contact_number bigint not null;

-- add unique constraint
alter table emp add unique(contact_number);
alter table emp add constraint contact_number_uq unique(contact_number);
alter table emp add constraint contact_number_email_uq unique(contact_number, email);

-- drop unique constraint
alter table emp drop index contact_number_uq;
alter table emp drop constraint contact_number_uq;

-- change column size
-- use CHANGE, but that means you have to give the column name twice (because CHANGE allows you to change the name of the column too)
ALTER TABLE emp MODIFY COLUMN name VARCHAR(100);
ALTER TABLE emp CHANGE COLUMN name name VARCHAR(100);