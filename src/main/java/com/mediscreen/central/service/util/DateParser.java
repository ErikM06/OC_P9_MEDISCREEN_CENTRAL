package com.mediscreen.central.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateParser {

    Logger logger = LoggerFactory.getLogger(DateParser.class);

    public Date stringToDate (String date) throws ParseException{
        Date parsedDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        parsedDate = dateFormat.parse(date);

        return parsedDate;
    }
}
