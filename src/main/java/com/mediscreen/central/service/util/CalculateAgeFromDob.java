package com.mediscreen.central.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class CalculateAgeFromDob {

    public int calculateAge (Date dob){
        Date currentDate = new Date(System.currentTimeMillis());
        LocalDate currentDateForConvertion = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dobForConvertion = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return currentDateForConvertion.compareTo(dobForConvertion);
    }


}
