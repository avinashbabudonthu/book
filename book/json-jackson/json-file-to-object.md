# JSON file to object
* Employee json
```
{
  "name": "jack",
  "designation": "SE"
}
```
* Employee class
```
package com.java;

public class Employee {

    private String name;
    private String designation;
    
    // getters & setters

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
```
* Main method
```
package com.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.net.URL;

public class JSONToObjects {

    @Test
    public void jsonFileToObject(){
        final ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = null;
        try {
            URL url = getClass().getClassLoader().getResource("employee.json");
            employee = objectMapper.readValue(url, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(employee);
    }
}
```
* Output
```
Employee{name='jack', designation='SE'}
```
