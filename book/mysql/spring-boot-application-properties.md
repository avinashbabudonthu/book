# Spring boot application properties to connect to mysql database
------
* Application and MysQL are running in localhost
```
spring.datasource.url=jdbc:mysql://${DATABASE_HOST:localhost}:${DATABASE_PORT:3307}/${DATABASE_NAME:db1}
spring.datasource.username=${DATABASE_USERNAME:root}
spring.datasource.password=${DATABASE_PASSWORD:root}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=${JPA_SHOW_SQL:true}
#This is actually a shortcut for the "hibernate.hbm2ddl.auto" property. Default to "create-drop" when using an embedded database, "none" otherwise
spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=${HIBERNATE_FORMAT_SQL:true}
```
------
* Application running in docker container and MysQL are running in localhost
```
spring.datasource.url=jdbc:mysql://${DATABASE_HOST:host.docker.internal}:${DATABASE_PORT:3307}/${DATABASE_NAME:db1}
spring.datasource.username=${DATABASE_USERNAME:root}
spring.datasource.password=${DATABASE_PASSWORD:root}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=${JPA_SHOW_SQL:true}
#This is actually a shortcut for the "hibernate.hbm2ddl.auto" property. Default to "create-drop" when using an embedded database, "none" otherwise
spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=${HIBERNATE_FORMAT_SQL:true}
```