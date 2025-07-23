# Mock static classes
* Add below code
```
MockedStatic<Utils> utilsMock = Mockito.mockStatic(Utils.class);
```
* Add below dependency to support mock static
```
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-inline</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
```