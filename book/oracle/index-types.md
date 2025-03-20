# Oracle Index Types
------
* Oracle provides several types of indexes, each designed to improve query performance under specific scenarios. Here's a breakdown of the most common types:
------
1. B-Tree Index
Description: The default and most common index type in Oracle. It uses a balanced tree structure to organize data for quick lookups.
Use Case: Best for queries that use equality or range conditions, like = or BETWEEN.
Example: Index on a column like EMP_ID.
------
2. Bitmap Index
Description: Stores rows as bitmaps, which are efficient for columns with a low cardinality (i.e., few distinct values).
Use Case: Best for columns like GENDER or STATUS. Often used in decision support systems or data warehouses.
Example: Index on a column like MARRIED with values YES or NO.
------
3. Unique Index
Description: Automatically created when a UNIQUE or PRIMARY KEY constraint is defined on a table. Ensures that all values in the indexed column(s) are unique.
Use Case: Enforcing uniqueness and improving performance for lookups based on unique columns.
Example: Index on a USERNAME column.
------
4. Composite Index
Description: An index on two or more columns.
Use Case: Best when queries frequently use multiple columns in the WHERE clause.
Example: Index on columns like (FIRST_NAME, LAST_NAME).
------
5. Function-Based Index
Description: Indexes the result of a function or expression applied to a column.
Use Case: Useful for queries with expressions or functions in the WHERE clause.
Example: Index on UPPER(EMP_NAME) to support case-insensitive searches.
------
6. Reverse Key Index
Description: Reverses the byte order of the column's value before creating the index.
Use Case: Optimized for high insert rates into sequential values like primary keys.
Example: Index on a column like ORDER_ID.
------
7. Domain Index
Description: Custom indexes defined by the user for specific data types or use cases.
Use Case: Best for applications like spatial data, text searches, or complex data types.
Example: Index for geographical location data.
------
8. Clustered Index
Description: Tied to table clustering, where data in the table is physically stored in a certain order.
Use Case: Used with clustered tables to improve performance.
Example: Data clustering based on DEPT_ID.
------
9. Global and Local Partitioned Indexes
Global Partitioned Index: Spans multiple partitions of a table.
Local Partitioned Index: Maps directly to the partitions of a table.
Use Case: Best for large, partitioned tables to improve query performance.
Example: Index on a partitioned sales table, such as SALES_BY_REGION.
------
Each index type serves a unique purpose. Choosing the right one depends on the table structure, data distribution, and query patterns