package com.mediscreen.central.controller;

import com.mediscreen.central.dto.PatientAssessmentDTO;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.service.DiseaseRiskService;
import com.mediscreen.central.service.util.CalculateAgeFromDob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping ("/assess")
public class DiseaseRiskController {

    Logger logger = LoggerFactory.getLogger(DiseaseRiskController.class);

    @Autowired
    DiseaseRiskService diseaseRiskService;

    @Autowired
    PatientClientProxy patientClientProxy;

    @Autowired
    CalculateAgeFromDob calculateAgeFromDob;

    @GetMapping("/id")
    public ResponseEntity<PatientAssessmentDTO> getAssessById (Long id){
        String assessmentSentence ="diabetes assessment is: ";
        Patient patient = patientClientProxy.getPatientById(id);
        String assessment = diseaseRiskService.getDiseaseRisk(patient);

        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                patient.getGiven(),patient.getFamily(),calculateAgeFromDob.calculateAge(patient.getDob()),assessmentSentence+assessment);

        return new ResponseEntity<>(patientAssessmentDTO, HttpStatus.OK);
    }

    @GetMapping("/familyName")
    public ResponseEntity<List<PatientAssessmentDTO>> getAssessByFamilyName (@RequestParam String familyName){
        List<PatientAssessmentDTO> patientAssessmentDTOLs = new ArrayList<>();

        List<Patient> patientLs = patientClientProxy.getPatientByFamily(familyName);
        logger.info("patientLs size is "+patientLs.size());

        patientLs.forEach(p -> {
            String assessmentSentence ="diabetes assessment is: ";
            String assessment = diseaseRiskService.getDiseaseRisk(p);
            PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                    p.getGiven(),p.getFamily(),calculateAgeFromDob.calculateAge(p.getDob()),assessmentSentence+assessment);
            patientAssessmentDTOLs.add(patientAssessmentDTO);

        });
        logger.info("patientAssessmentDTOls size: "+patientAssessmentDTOLs.size());

        return new ResponseEntity<>(patientAssessmentDTOLs, HttpStatus.OK);
    }
}
