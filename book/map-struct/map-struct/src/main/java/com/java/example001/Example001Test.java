package com.java.example001;

import org.junit.jupiter.api.Test;

public class Example001Test {

    @Test
    void test1() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("a");
        studentEntity.setCourse("c1");
        StudentModel studentModel = StudentMapper.INSTANCE.entityToModel(studentEntity);

        String result1 = String.format("studentEntity = %s, studentModel = %s", studentEntity, studentModel);
        System.out.println(result1);
    }
}
