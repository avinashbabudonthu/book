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
                <file>${basedir}/eclipse-formatter.xml</file>
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
------
# References
* https://www.baeldung.com/java-maven-spotless-plugin