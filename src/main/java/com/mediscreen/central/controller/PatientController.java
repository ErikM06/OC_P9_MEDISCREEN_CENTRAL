package com.mediscreen.central.controller;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.customExceptions.PatientAlreadyExistException;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.proxy.PatientClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/central")
public class PatientController {
    Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final PatientClientProxy patientClientProxy;



    public PatientController (PatientClientProxy patientClientProxy){
        this.patientClientProxy=patientClientProxy;

    }


    @GetMapping ("/get-patient-list")
    public String getPatient(Model model, @RequestParam(value = "error", required = false) String error) {

        model.addAttribute("patientList",  patientClientProxy.getPatientList());
        if (null != error) {
            model.addAttribute("error", error);
        }
        return "/patientTemplates/patientList";
    }

    @GetMapping ("/get-patient-by-family")
    public String getPatientByFamily (Model model, @RequestParam String family){
        List<Patient> patientls = patientClientProxy.getPatientByFamily(family);
        model.addAttribute("patientList",patientls);
        return "patientTemplates/patientList";
    }

    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/add-patient")
    public String addPatientView (Model model){
        model.addAttribute("patient",new Patient());
        return "patientTemplates/addPatient";
    }

    /**
     *
     * @param patient
     * @return a redirect to the patient list if success OR addPatientHist.html if fails
     */
    @PostMapping("/validate-patient")
    public String addAPatient (@ModelAttribute (value = "patient") Patient patient, Model model){
        try {
            patientClientProxy.addPatient(patient);
        } catch (PatientAlreadyExistException e){
            model.addAttribute("error", e.getMessage());
            return "patientTemplates/addPatient";
        }

        return "redirect:/central/get-patient-list";
    }

    @GetMapping ("/update-patient/{id}")
    public String updateAPatient (Model model, @PathVariable (value = "id") Long id) {
        model.addAttribute("patient", patientClientProxy.getPatientById(id));
        return "patientTemplates/updatePatient";
    }

    @PostMapping ("/validate-update/{id}")
    public String validatePatientUpdate (@ModelAttribute (value = "patient") Patient patient, @PathVariable (value ="id") Long id) {
        logger.info("in /validateUpdate");
        patientClientProxy.updatePatient(patient);
        return "redirect:/central/get-patient-list";
    }

    @GetMapping("/delete-patient-by-id/{id}")
    public String deletePatientById (@PathVariable (value = "id") Long id) throws NotFoundException {
        logger.info("in /deletePatientById");
        try {
            patientClientProxy.deletePatient(id);
        } catch (NotFoundException e){
            return "redirect:/central/get-patient-list";
        }
        return "redirect:/central/get-patient-list";
    }


}
