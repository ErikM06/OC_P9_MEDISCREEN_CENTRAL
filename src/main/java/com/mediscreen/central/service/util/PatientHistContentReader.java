package com.mediscreen.central.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PatientHistContentReader {

    Logger logger = LoggerFactory.getLogger(PatientHistContentReader.class);


    public List<String> ToStringListConvertor (String patHistoryContent){
        List<String> contentToList = new ArrayList<>();
        Pattern p = Pattern.compile("[A-zA-Z]");
        Matcher m = p.matcher(patHistoryContent);
        while (m.find()){
            String matchedString = m.toString();
            contentToList.add(matchedString);

        }
        logger.info("in PatientHistContentReader "+ contentToList);

    return contentToList;
    }
}
