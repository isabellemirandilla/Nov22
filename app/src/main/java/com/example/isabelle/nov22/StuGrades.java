package com.example.isabelle.nov22;

public class StuGrades {

    String fname, lname;
    Long grade;

    public StuGrades(){

    }

    public StuGrades(String fname, String lname, Long grade){
        this.fname = fname;
        this.lname = lname;
        this.grade = grade;
    }

    public String getFname() {
        return fname;
    }

    public String getLname(){
        return lname;
    }

    public Long getGrade(){
        return grade;
    }
}
