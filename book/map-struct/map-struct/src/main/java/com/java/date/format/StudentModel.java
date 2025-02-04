package com.java.date.format;

public class StudentModel {

    private String name;
    private String course;
    private String joiningDate1;
    private String joiningDate2;

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

    public String getJoiningDate1() {
        return joiningDate1;
    }

    public void setJoiningDate1(String joiningDate1) {
        this.joiningDate1 = joiningDate1;
    }

    public String getJoiningDate2() {
        return joiningDate2;
    }

    public void setJoiningDate2(String joiningDate2) {
        this.joiningDate2 = joiningDate2;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", joiningDate1=" + joiningDate1 +
                ", joiningDate2=" + joiningDate2 +
                '}';
    }
}
