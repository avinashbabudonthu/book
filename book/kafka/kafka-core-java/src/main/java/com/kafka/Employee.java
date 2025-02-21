package com.kafka;

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