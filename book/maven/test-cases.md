# Run Skip Test Cases using Maven
------
* Skip Test cases
```
mvn install -Dmaven.test.skip=true
```
* Run all test cases
```
mvn test
```
* Run all methods in specific test class
```
mvn test -Dtest=[fully-packaged-class-name]
mvn test -Dtest=com.test.EmployeeService
```
* Run one test method in specific test class
```
mvn test -Dtest=[fully-packaged-class-name]#methodName
mvn test -Dtest=com.test.EmployeeService#getEmployees
```
* Run full package
```
mvn test -Dtest="com.tests.**"
```
* Compile test cases
```
mvn test-compile
```
------
### More About the test Parameter
* So far, we’ve shown how to execute a single test class or test method by providing the test parameter value accordingly.
* Actually, the Maven surefire plugin allows us to set the value of the test parameter in different formats to execute tests flexibly.
* some commonly used formats:
    * Execute multiple test classes by name: -Dtest=”TestClassName1, TestClassName2, TestClassName3…”
    * Execute multiple test classes by name pattern: -Dtest=”*ServiceUnitTest” or -Dtest=”The*UnitTest, Controller*Test”
    * Specify multiple test methods by name: -Dtest=”ClassName#method1+method2″
    * Specify multiple method names by name pattern: -Dtest=”ClassName#whenSomethingHappens_*”
------