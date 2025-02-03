# Maven core java project
* Create maven core java project
* Add required dependencies
------
# Create project
```
mvn archetype:generate -DgroupId=com.java -DartifactId=[project-name] -Dversion=1.0.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
* Create `README.md` file at project level
* Add [.gitignore](../.gitignore) file
* Add [Dockerfile](Dockerfile)
* Create `resources` folder under `src/main`
* Add [log4j.xml](log4j.xml) and copy under `src/main/resources` folder
------
# Step 1 - Download files and update some fields
* Copy [pom.xml](core-java-pom.xml) file and update below fields or follw below steps
    * groupId
    * artifactId
    * projectNameSameAsArtifactId
------
# Steps 2 - Add content individually
* Open `pom.xml`
* Add below properties
```
<properties>
	<maven.build.timestamp.format>yyyy-MM-dd-HH-mm</maven.build.timestamp.format>
</properties>
```
* Add below `build` tag after `dependencies`
```
<build>
	<finalName>${project.artifactId}-${project.version}-${maven.build.timestamp}</finalName>
</build>
```
* Set java verion. For 1.8
```
<properties>
    <maven.compiler.target>1.8</maven.compiler.target>
    <maven.compiler.source>1.8</maven.compiler.source>
</properties>

<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>3.8.1</version>
			<configuration>
				<source>1.8</source>
				<target>1.8</target>
			</configuration>
		</plugin>
	</plugins>
</build>
```
* Set java version. For 1.8 above
```
<properties>
    <maven.compiler.release>17</maven.compiler.release>
</properties>

<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>3.12.1</version>
			<configuration>
				<release>17</release>
			</configuration>
		</plugin>
	</plugins>
</build>
```
* Add log4j slf4j dependencies
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
* Create `resources` folder under `src/main`
* Create log4j.xml file and add below content
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration debug="true" xmlns:log4j='http://jakarta.apache.org/log4j/'>

    <!-- console appender to print logs to console -->
    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="[%C{1}.%M] - %m%n" />
        </layout>
    </appender>

    <!-- file appender to print logs to file -->
    <appender name="file" class="org.apache.log4j.RollingFileAppender">
        <param name="append" value="false" />
        <param name="maxFileSize" value="10MB" />
        <param name="maxBackupIndex" value="10" />

        <!-- creates logs in current drive -->
        <!-- <param name="file" value="/logs/core-java.log" />-->

        <!-- creates logs in project folder -->
        <param name="file" value="logs/core-java.log" />

        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss}] [%-5p] [%c{1}:%L] - %m%n" />
        </layout>
    </appender>

    <root>
        <level value="INFO" />
        <appender-ref ref="console" />
        <appender-ref ref="file" />
    </root>

</log4j:configuration>
```
* Add Junit 5 dependencies
```
<dependency>
	<groupId>org.junit.jupiter</groupId>
	<artifactId>junit-jupiter-engine</artifactId>
	<version>5.8.1</version>
	<scope>test</scope>
</dependency>

<dependency>
	<groupId>org.junit.platform</groupId>
	<artifactId>junit-platform-launcher</artifactId>
	<version>1.4.2</version>
</dependency>

or 

<dependency>
	<groupId>org.junit.jupiter</groupId>
	<artifactId>junit-jupiter-api</artifactId>
	<version>5.11.3</version>
	<scope>test</scope>
</dependency>
```
* Add mockito dependencies
```
<dependency>
	<groupId>org.mockito</groupId>
	<artifactId>mockito-core</artifactId>
	<version>2.21.0</version>
	<scope>test</scope>
</dependency>

<dependency>
	<groupId>org.mockito</groupId>
	<artifactId>mockito-junit-jupiter</artifactId>
	<version>2.23.0</version>
	<scope>test</scope>
</dependency>
```
* Add lombok dependencies
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.34</version>
    <scope>provided</scope>
</dependency>
```
* Add `Dockerfile`. Copy below content
```
FROM openjdk:17
COPY target/[application-name]*.jar app.jar
#EXPOSE [application-running-port]
ENTRYPOINT ["java", "-jar", "app.jar"]
```
* Add below plugin to create FAT jar
```
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-assembly-plugin</artifactId>
			<version>2.5.2</version>
			<configuration>
				<descriptorRefs>
					<descriptorRef>jar-with-dependencies</descriptorRef>
				</descriptorRefs>
				<archive>
					<manifest>
						<classpathPrefix>lib/</classpathPrefix>
						<mainClass>com.app.Main</mainClass>
					</manifest>
				</archive>
			</configuration>
			<executions>
				<execution>
					<id>all-assemble</id>
					<phase>package</phase>
					<goals>
						<goal>single</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```
* Add commons dependencies
```
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-collections4</artifactId>
    <version>4.5.0-M2</version>
</dependency>

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.16.0</version>
</dependency>
```
* Add java faker dependencies
```
<dependency>
	<groupId>com.github.javafaker</groupId>
	<artifactId>javafaker</artifactId>
	<version>1.0.2</version>
	<exclusions>
		<exclusion>
			<groupId>org.yaml</groupId>
			<artifactId>snakeyaml</artifactId>
		</exclusion>
	</exclusions>
</dependency>

<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>2.2</version>
</dependency>
```