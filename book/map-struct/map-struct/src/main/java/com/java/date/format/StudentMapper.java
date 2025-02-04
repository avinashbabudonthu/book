package com.java.date.format;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "joiningDate1", target = "joiningDate1", dateFormat = "dd-MM-yyyy")
    @Mapping(source = "joiningDate2", target = "joiningDate2", dateFormat = "dd-MM-yyyy")
    StudentModel entityToModel(StudentEntity studentEntity);

    @Mapping(source = "joiningDate1", target = "joiningDate1", dateFormat = "dd-MM-yyyy")
    @Mapping(source = "joiningDate2", target = "joiningDate2", dateFormat = "dd-MM-yyyy")
    StudentEntity modelToEntity(StudentModel studentModel);

    default String gregorianCalendarToString(GregorianCalendar gregorianCalendar) {
        String result = null;
        if(null != gregorianCalendar) {
            result = new SimpleDateFormat("dd-MM-yyyy").format(gregorianCalendar.getTime());
        }
        return result;
    }

    default GregorianCalendar stringToGregorianCalendar(String date) throws ParseException {
        GregorianCalendar result = null;

        if(null != date) {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            result = new GregorianCalendar(date1.getYear(), date1.getMonth(), date1.getDate());
        }
        return  result;
    }

}