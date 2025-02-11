# Maven Commands
* Maven version
```
mvn -version
```
* Compile java classes
```
mvn compile
```
* Package - create jar/war/ear
```
mvn package
```
* Delete target directory
```
mvn clean
```
* Specify pom file name to execute
```
mvn -f pom-file-path/pom-file-name-with-extension targets-to-execute

mvn -f E:/core-java/my-pom.xml clean compile package
```
* Copy archive to local maven repository
```
mvn install
```
* Upload archive to remote repository like Nexus
```
mvn deploy
```
* Project dependencies in detail in tree structure
```
mvn dependency:tree
```
* Project dependencies in detail in tree structure to file
```
mvn dependency:tree > C:\poms\dependencies.txt
```
* Project dependencies in detail in tree structure - specific dependency
```
mvn dependency:tree -Dincludes=[groupId]:[artifactId]
```
* Install local jars to maven local repository
```
mvn install:install-file -Dfile=path-to-jar-file/ojdbc.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```
* Maven build timestamp - `${maven.build.timestamp}`
```
<properties>
	<maven.build.timestamp.format>yyyy-MM-dd HH:mm</maven.build.timestamp.format>
</properties>

<build>
		<finalName>${project.artifactId}-${project.version}-${maven.build.timestamp}</finalName>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
```
* Project dependencies are the dependencies declared in your pom. To copy them with their transitive dependencies
```
mvn dependency:copy-dependencies
```
* how to make maven force download dependencies from remote repository
```
mvn -U [goal]
mvn -U clean package
mvn --update-snapshots [goal]
mvn --update-snapshots clean package
```
* Maven eclipse plugin
```
<plugin>
 <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-eclipse-plugin</artifactId>
  <configuration>
     <downloadSources>true</downloadSources>
     <downloadJavadocs>true</downloadJavadocs>
  </configuration>
</plugin>
```
* Creating java project using maven archetype plugin
```
mvn archetype:generate -DgroupId=com.test -DartifactId=maven-hello-world -DarchetypArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
* Create web project using maven archetype plugin
```
mvn archetype:generate -DgroupId=com.test -DartifactId=test-web-app -Dpackage=com.example -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```
* Create jersey implemented web application using maven archetype plug in
```
mvn archetype:generate -DgroupId=com.example -DartifactId=simple-service-webapp -DarchetypeGroupId=org.glassfish.jersey.archetypes -DarchetypeArtifactId=jersey-quickstart-webapp -DarchetypeVersion=2.22.2 -Dpackage=com.example -DinteractiveMode=false
```
* creating project documentation\
```
mvn site
```
![picture](img/creating_pjt_documentation.jpg)
* find outdated dependency versions from your modules
```
mvn versions:display-dependency-updates
```
* Execute `build` target on running `package` target
```
<executions>
	<execution>
		<phase>package</phase>
		<goals>
			<goal>build</goal>
		</goals>
	</execution>
</executions>
```
* Execute main class
```
mvn exec:java -Dexec.mainClass=com.camel.spring.CamelSpringHelloWorld
```
* Effective POM
```
mvn help:effective-pom
```
* Effective pom to text file
```
mvn help:effective-pom > c:\poms\test.txt
```