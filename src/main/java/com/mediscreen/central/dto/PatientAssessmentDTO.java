package com.mediscreen.central.dto;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class PatientAssessmentDTO {

    private String firstName;

    private String lastName;

    private int age;

    private String assessment;

    public PatientAssessmentDTO(String firstName, String lastName, int age, String assessment){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.assessment = assessment;
    }
    public PatientAssessmentDTO(){

    }





}
