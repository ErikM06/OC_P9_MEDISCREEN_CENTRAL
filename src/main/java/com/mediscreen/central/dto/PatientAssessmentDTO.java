package com.mediscreen.central.dto;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class PatientAssessmentDTO {

    private String givenTest;

    private String familyTypeTest;

    private int age;

    private String diabetesAssessment;

    public PatientAssessmentDTO(String givenTest, String familyTypeTest, int age, String diabetesAssessment){
        this.givenTest = givenTest;
        this.familyTypeTest = familyTypeTest;
        this.age = age;
        this.diabetesAssessment = diabetesAssessment;
    }
    public PatientAssessmentDTO(){

    }

}
