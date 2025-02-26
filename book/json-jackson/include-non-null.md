# Include Non Null
------
* Add below annotation at class level
```
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee { ... }
```
* Field level also accepter
```
import com.fasterxml.jackson.annotation.JsonInclude;

public class Employee {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String course;
}
```
* At ObjectMapper level
```
mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
```