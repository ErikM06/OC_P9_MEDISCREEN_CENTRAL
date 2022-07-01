package com.mediscreen.central.controller;

import com.google.gson.Gson;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.service.DiseaseRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getAssessById (Long id){
       String f = diseaseRiskService.getDiseaseRisk(patientClientProxy.getPatientById(id));
       Gson parser = new Gson();

        return new ResponseEntity<>(f, HttpStatus.OK);
    }
}
