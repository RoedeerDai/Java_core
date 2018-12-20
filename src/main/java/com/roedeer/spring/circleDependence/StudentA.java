package com.roedeer.spring.circleDependence;

/**
 * Created by Roedeer on 12/15/2018.
 */
public class StudentA {

    private StudentB studentB;

    public void setStudentB(StudentB studentB) {
        this.studentB = studentB;
    }

    public StudentA() {
    }

    public StudentA(StudentB studentB) {
        this.studentB = studentB;
    }
}
