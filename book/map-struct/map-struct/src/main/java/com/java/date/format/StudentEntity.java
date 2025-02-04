package com.java.date.format;

import java.util.Date;
import java.util.GregorianCalendar;

public class StudentEntity {

    private String name;
    private String course;
    private Date joiningDate1;
    private GregorianCalendar joiningDate2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getJoiningDate1() {
        return joiningDate1;
    }

    public void setJoiningDate1(Date joiningDate1) {
        this.joiningDate1 = joiningDate1;
    }

    public GregorianCalendar getJoiningDate2() {
        return joiningDate2;
    }

    public void setJoiningDate2(GregorianCalendar joiningDate2) {
        this.joiningDate2 = joiningDate2;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", joiningDate1=" + joiningDate1 +
                ", joiningDate2=" + joiningDate2 +
                '}';
    }
}
