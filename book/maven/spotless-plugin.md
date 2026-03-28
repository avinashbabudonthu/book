# Maven Spotless Plugin
------
* Sample spotless plugin usage
```
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>2.30.0</version>
    <configuration>
        <java>
            <includes>
                <include>src/main/java/**/*.java</include>
                <include>src/test/java/**/*.java</include>
            </includes>
            <eclipse>
                <file>${basedir}/eclipse-formatter.xml</file> <!-- refer eclipse-formatter.xml file -->
                <version>4.7.1</version>
            </eclipse>
            <removeUnusedImports />
        </java>
    </configuration>
    <executions>
        <execution>
        <!-- Window > Preferences > Maven > Errors/Warnings > Plugin
            execution not covered by lifecycle configuration. Select Ignore / Warning / Error as you wish. -->
            <goals>
                <goal>check</goal>
            </goals>
            <phase>compile</phase>
        </execution>
    </executions>
</plugin>
```
* Using google java code formatter
```
<plugin>
	<groupId>com.diffplug.spotless</groupId>
	<artifactId>spotless-maven-plugin</artifactId>
	<version>2.44.2</version>
	<configuration>
		<java>
			<googleJavaFormat>
				<version>1.24.0</version>
			</googleJavaFormat>
			<licenseHeader>
				<!-- <file>${basedir}/license-header.txt</file> -->
                <file>license-header.txt</file>
			</licenseHeader>
			<importOrder/>
			<removeUnusedImports/>
			<formatAnnotations/> <!--ensures that type annotations are positioned on the same line as the fields they describe-->
		</java>
		<formats>
			<format>
				<includes>
					<include>*.md</include>
					<include>.gitignore</include>
				</includes>
				<trimTrailingWhitespace/>
				<endWithNewline/>
			</format>
		</formats>
	</configuration>
</plugin>
```
------
# References
* https://www.baeldung.com/java-maven-spotless-plugin