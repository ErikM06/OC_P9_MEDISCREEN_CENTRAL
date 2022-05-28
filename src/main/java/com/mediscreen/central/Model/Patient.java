package com.mediscreen.central.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient {

    private Long id;

    private String name;
    private String family;

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

    // Used to assert if a patient already exist
    public Patient(Date dob, String sex, String address, String phone) {
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Patient() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
