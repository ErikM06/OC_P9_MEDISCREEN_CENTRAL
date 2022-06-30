package com.mediscreen.central.controller;

import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.service.DiseaseRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping ("/assess")
public class DiseaseRiskController {

    @Autowired
    DiseaseRiskService diseaseRiskService;

    @Autowired
    PatientClientProxy patientClientProxy;

    @GetMapping("/id")
    public String getAssessById (Long id){
      String result =  diseaseRiskService.getDiseaseRisk(patientClientProxy.getPatientById(id));

    return result;
    }
}
