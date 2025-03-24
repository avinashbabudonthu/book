-- Create index
-- CREATE INDEX index_name ON table_name (column1, column2, ...);
CREATE INDEX idx_lastname ON Persons (LastName);
-- If you want to create an index on a combination of columns, you can list the column names within the parentheses, separated by commas
CREATE INDEX my-index ON employees (first_name, email, phone_number);
CREATE INDEX idx_pname ON Persons (LastName, FirstName);

-- CREATE UNIQUE INDEX Syntax
-- Creates a unique index on a table. Duplicate values are not allowed
CREATE UNIQUE INDEX index_name ON table_name (column1, column2, ...);

-- DROP INDEX Statement
DROP INDEX index_name;