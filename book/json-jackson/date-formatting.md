# Date formatting
------
### Solution 1
* Use `JsonFormat` annotation
```
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Integer id;
    private String name;
    private String dept;
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy@HHmmss@SSS@Z", timezone = "EST")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy@HHmmss@SSS@Z", timezone = "IST")
    private Date joiningDate;

}
```
------
### Solution 2
* Add setting to `com.fasterxml.jackson.databind.ObjectMapper` class
```
private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("EST"));
```
------
* Available `timezone` values below. Refer class `java.time.ZoneId`
```
EST - -05:00
HST - -10:00
MST - -07:00
ACT - Australia/Darwin
AET - Australia/Sydney
AGT - America/Argentina/Buenos_Aires
ART - Africa/Cairo
AST - America/Anchorage
BET - America/Sao_Paulo
BST - Asia/Dhaka
CAT - Africa/Harare
CNT - America/St_Johns
CST - America/Chicago
CTT - Asia/Shanghai
EAT - Africa/Addis_Ababa
ECT - Europe/Paris
IET - America/Indiana/Indianapolis
IST - Asia/Kolkata
JST - Asia/Tokyo
MIT - Pacific/Apia
NET - Asia/Yerevan
NST - Pacific/Auckland
PLT - Asia/Karachi
PNT - America/Phoenix
PRT - America/Puerto_Rico
PST - America/Los_Angeles
SST - Pacific/Guadalcanal
VST - Asia/Ho_Chi_Minh
```