# Log4J Slf4J dependencies
* Add following dependencies
```
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>2.0.13</version>
</dependency>

<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
	<version>2.0.13</version>
</dependency>
```
* Add `log4j.xml` or `log4j.properties` file in classpath. For maven project add to `src/main/resources` folder