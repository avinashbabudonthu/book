package com.java.date.format;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatTest {

    private static final Logger log = LoggerFactory.getLogger(DateFormatTest.class);

    public static void main(String[] args) {
        StudentEntity studentEntity1 = new StudentEntity();
        studentEntity1.setName("a");
        studentEntity1.setCourse("c1");
        studentEntity1.setJoiningDate1(new Date());
        studentEntity1.setJoiningDate2(new GregorianCalendar());

        StudentModel studentModel = StudentMapper.INSTANCE.entityToModel(studentEntity1);
        String result = String.format("studentModel = %s", studentModel);
        System.out.println(result);

        StudentEntity studentEntity2 = StudentMapper.INSTANCE.modelToEntity(studentModel);
        String result2 = String.format("studentEntity2 = %s", studentEntity2);
        System.out.println(result2);
        log.info("{}", result2);
    }

}
