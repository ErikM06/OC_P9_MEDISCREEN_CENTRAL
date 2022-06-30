package com.mediscreen.central.service;

import com.mediscreen.central.model.PatientHist;
import com.mediscreen.central.proxy.PatientHistClientProxy;
import com.mediscreen.central.service.util.PatientHistContentReader;
import com.mediscreen.central.service.util.WordsTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CalculateTriggerService {

    Logger logger = LoggerFactory.getLogger(CalculateTriggerService.class);
    @Autowired
    PatientHistClientProxy patientHistClientProxy;

    @Autowired
    PatientHistContentReader patientHistContentReader;

    public Integer getTriggerCount (Long id){
        AtomicReference<Integer> triggerCount = new AtomicReference<>(0);
        List<PatientHist> patientHistLs = patientHistClientProxy.getPatientHistByPatId(id);
        List<String> triggers = WordsTrigger.listOfWordTriggers;

        patientHistLs.forEach (patientHist -> {
            List<String> contentWords = patientHistContentReader.ToStringListConvertor(patientHist.getContent());
            contentWords.forEach(cw -> {
                if (triggers.contains(cw)) {
                   triggerCount.getAndSet(triggerCount.get() + 1);
                }
            });
        });
        logger.info("in getTriggerCount "+ triggerCount.get());
        return triggerCount.get();
    }


}
