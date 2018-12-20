package com.roedeer.spring.circleDependence;

/**
 * Created by Roedeer on 12/15/2018.
 */
public class StudentB {

    private StudentC studentC;

    public void setStudentC(StudentC studentC) {
        this.studentC = studentC;
    }

    public StudentB() {
    }

    public StudentB(StudentC studentC) {
        this.studentC = studentC;
    }
}
