# Materialized views
------
# Differences between a regular view and a materialized view in database systems
## Regular View:
* Definition: A virtual table based on the result-set of an SQL query.
* Storage: Does not store data physically; it only stores the query.
* Update: Data is fetched from the base tables each time the view is queried.
* Performance: Slower performance for complex queries as data is computed on the fly.
* Use Case: Suitable for scenarios where data needs to be current every time the view is queried.

## Materialized View:
* Definition: A physical copy of the result of a query.
* Storage: Stores data physically, like a table.
* Update: Data is periodically refreshed, either automatically or manually.
* Performance: Faster performance for complex queries as data is precomputed and stored.
* Use Case: Suitable for scenarios where query performance is critical and slightly stale data is acceptable.
------
# Syntax
* Basic syntax
```
CREATE MATERIALIZED VIEW view_name
BUILD IMMEDIATE
REFRESH [FAST | COMPLETE | FORCE]
ON [COMMIT | DEMAND]
AS
SELECT ...;
```
## Explanation
* view_name: The name you want to give to your materialized view.
* BUILD IMMEDIATE: Creates the materialized view and immediately populates it with data. You can also use BUILD DEFERRED to delay the data population.
* REFRESH: Specifies how the materialized view will be refreshed:
* FAST: Refreshes only the changes made since the last refresh.
* COMPLETE: Recomputes the entire materialized view.
* FORCE: Tries a fast refresh, but does a complete refresh if a fast refresh is not possible.
* ON COMMIT: Refreshes the materialized view whenever a transaction on one of the base tables is committed.
* ON DEMAND: Refreshes the materialized view only when explicitly requested.
* Example: In this example, a materialized view named `sales_mv` is created to store the total quantity sold for each product
```
CREATE MATERIALIZED VIEW sales_mv
BUILD IMMEDIATE
REFRESH FAST
ON COMMIT
AS
SELECT product_id, SUM(quantity_sold) AS total_quantity
FROM sales
GROUP BY product_id;
```