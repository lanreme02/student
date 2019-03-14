package com.cognizant.student.controller;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRequest {

    private String studentId;
    private String firstName;
    private String lastName;

    public StudentRequest(){}


    @JsonCreator
    public StudentRequest(@JsonProperty("id") String studentId,
                          @JsonProperty("firstname") String firstName, @JsonProperty("lastname") String lastName){

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
