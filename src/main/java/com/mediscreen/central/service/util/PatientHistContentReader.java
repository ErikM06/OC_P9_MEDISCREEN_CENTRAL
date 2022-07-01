package com.mediscreen.central.service.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientHistContentReader {

    Logger logger = LoggerFactory.getLogger(PatientHistContentReader.class);


    public List<String> ToStringListConvertor (String patHistoryContent){
        List<String> contentToList = new ArrayList<>();
        // replace all "," by " "

        String formatContent = patHistoryContent.replace(",","");
        // all words to an array
        String[] split = StringUtils.split(formatContent);
        contentToList = List.of(split);
        // assert every word triggered in content
       // contentToList.forEach(w -> logger.info("in PatientHistContentReader "+ w));


        return contentToList;
    }
}
