### Insert data on start of application
------
* Add this file in `src/main/resources`
```
data.sql
```
* Add this property in application.properties file otherwise table data will be deleted on creating entities
```
spring.jpa.hibernate.ddl-auto=none
```