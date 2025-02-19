# Ignore Unknown Properties
* Use `com.fasterxml.jackson.annotation.JsonIgnoreProperties` at class level
* Sample code
```
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeModel {
	
	private String name;
	private String designation;
	
}
```