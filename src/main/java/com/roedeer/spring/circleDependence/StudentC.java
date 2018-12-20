package com.roedeer.spring.circleDependence;

/**
 * Created by Roedeer on 12/15/2018.
 */
public class StudentC {

    private StudentA studentA;

    public void setStudentA(StudentA studentA) {
        this.studentA = studentA;
    }

    public StudentC() {
    }

    public StudentC(StudentA studentA) {
        this.studentA = studentA;
    }
}
