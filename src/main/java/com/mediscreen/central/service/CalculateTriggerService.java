package com.mediscreen.central.service;

import com.mediscreen.central.model.PatientHist;
import com.mediscreen.central.proxy.PatientHistClientProxy;
import com.mediscreen.central.service.util.PatientHistContentReader;
import com.mediscreen.central.service.util.WordsTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculateTriggerService {

    Logger logger = LoggerFactory.getLogger(CalculateTriggerService.class);
    @Autowired
    PatientHistClientProxy patientHistClientProxy;

    @Autowired
    PatientHistContentReader patientHistContentReader;

    public Integer getTriggerCount (Long id){
        List<String> triggerCountLs = new ArrayList<>();
        List<PatientHist> patientHistLs = patientHistClientProxy.getPatientHistByPatId(id);
        List<String> triggers = WordsTrigger.listOfWordTriggers;

        patientHistLs.forEach (patientHist -> {
            List<String> contentWords = patientHistContentReader.ToStringListConvertor(patientHist.getContent());

            contentWords.forEach(cw -> {
                   triggerCountLs.addAll(triggers.stream().filter(t-> t.equals(cw)).collect(Collectors.toList()));

            });
        });
        logger.info("in getTriggerCount "+ triggerCountLs.size());
        return triggerCountLs.size();
    }


}
