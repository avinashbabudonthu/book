# Spring Batch Notes
---
## Important components
* Job Launcher
* Job
* Step
* Jon Repository
* Item Reader
* Item Processor
* Item Writer
* [High level architecture](img/high-level-architecture.jpg)
---
## Queries
* Job related information is saved in table db tables. Following are queries to get job details
```SQL
SELECT * FROM BATCH_JOB_EXECUTION;
SELECT * FROM BATCH_JOB_EXECUTION_CONTEXT ;
SELECT * FROM BATCH_JOB_EXECUTION_PARAMS ;
SELECT * FROM BATCH_JOB_INSTANCE ;
SELECT * FROM BATCH_STEP_EXECUTION ;
SELECT * FROM BATCH_STEP_EXECUTION_CONTEXT ;
```