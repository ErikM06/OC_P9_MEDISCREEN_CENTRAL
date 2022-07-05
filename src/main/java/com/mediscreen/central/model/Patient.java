package com.mediscreen.central.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@ToString
@EqualsAndHashCode
@Component
public class Patient {

    private Long id;

    private String firstname;

    private String lastname;

    private String family;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String given;
    private String sex;
    private String address;
    private String phone;



    Patient (String firstname, String lastname, Date dob, String sex, String address, String phone){
        this.firstname =firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Patient() {

    }


}

