package com.mediscreen.central.controller;

import com.mediscreen.central.Model.Patient;
import com.mediscreen.central.service.PatientRepoService;
import com.mediscreen.central.service.util.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;


@Controller
@RequestMapping("/central")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepoService patientService;

    @Autowired
    DateParser parser;

    @GetMapping ("/getPatientList")
    public String getPatient(Model model) {

        model.addAttribute("patientList",patientService.getAllPatient());

        return "patientList";
    }

    @GetMapping ("/getPatientByFamily")
    public String getPatientByFamily (Model model, @RequestParam String family){

        model.addAttribute("patientList",patientService.getAllPatientByFamily(family));

        return "patientList";
    }

    @GetMapping("/addPatient")
    public String addPatientView (Model model){
        model.addAttribute("patient",new Patient());
        return "addPatient";
    }

    @PostMapping("/addPatient")
    public ResponseEntity<Patient> addAPatient (@RequestParam String family, String given, String dob, String sex, String address, String phone){
        Date dobToDate = null;
        try {
            dobToDate = parser.stringToDate(dob);
        } catch (ParseException e) {
            logger.info("in post method /addPatient");
            e.getMessage();
        }
        Patient patient = new Patient(family,given,dobToDate,sex,address,phone);
        patientService.addAPatientClient(patient);

    return  new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
    }

}
