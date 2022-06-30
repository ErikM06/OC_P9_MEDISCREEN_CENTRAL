package com.mediscreen.central.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Patient {

    private Long id;

    private String name;
    private String family;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String given;
    private String sex;
    private String address;
    private String phone;


    public Patient(String family, String given, Date dob, String sex, String address, String phone) {

        this.family = family;
        this.dob = dob;
        this.given = given;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Patient() {

    }
}

