package com.mediscreen.central.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;


@Setter
@Getter
@ToString
@Component
public class Patient {

    private Long id;
    private String firstName;
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String sex;
    private String address;
    private String phone;

    private String assessment;



    public Patient (String firstName, String lastName, Date dob, String sex, String address, String phone, String assessment){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        this.assessment = assessment;
    }

    public Patient() {

    }


}

