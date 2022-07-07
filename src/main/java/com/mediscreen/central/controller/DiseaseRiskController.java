package com.mediscreen.central.controller;

import com.mediscreen.central.dto.PatientAssessmentDTO;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.proxy.PatientDiabetesRiskProxy;
import com.mediscreen.central.service.util.CalculateAgeFromDob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping ("/central")
public class DiseaseRiskController {

    Logger logger = LoggerFactory.getLogger(DiseaseRiskController.class);

    @Autowired
    PatientClientProxy patientClientProxy;

    @Autowired
    PatientDiabetesRiskProxy diabetesRiskProxy;

    @Autowired
    CalculateAgeFromDob calculateAgeFromDob;

    @GetMapping("/calculate-disease-risk-by-id/{patId}")
    public String calculateDiseaseRickByIdView (Model model, @PathVariable (value = "patId") Long id){
        Patient patient;
        int age = 0;
        try {
            patient = patientClientProxy.getPatientById(id);
            PatientAssessmentDTO patientAssessmentDTO = diabetesRiskProxy.getAssessById(id).getBody();
            patient.setFamily(patientAssessmentDTO.getDiabetesAssessment());
            age = calculateAgeFromDob.calculateAge(patient.getDob());
            patientClientProxy.updatePatient(patient);
        } catch (Exception e){
            return "/central/getPatientHistList/{patId}";
        }
        model.addAttribute("patient",patient);
        model.addAttribute("age", age);

        return "diseaseRiskTemplates/diseaseRiskView";
    }



}
