package com.mediscreen.central.controller;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.customExceptions.PatientAlreadyExistException;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.proxy.PatientHistClientProxy;
import com.mediscreen.central.service.util.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final PatientHistClientProxy patientHistClientProxy;

    public PatientController (PatientClientProxy patientClientProxy, PatientHistClientProxy patientHistClientProxy){
        this.patientClientProxy=patientClientProxy;
        this.patientHistClientProxy = patientHistClientProxy;
    }

    @Autowired
    DateParser parser;
    @GetMapping ("/getPatientList")
    public String getPatient(Model model, @RequestParam(value = "error", required = false) String error) {

        model.addAttribute("patientList",  patientClientProxy.getPatientList());
        if (null != error) {
            model.addAttribute("error", error);
        }
        return "patientTemplate/patientList";
    }

    @GetMapping ("/getPatientByFamily")
    public String getPatientByFamily (Model model, @RequestParam String family){
        List<Patient> patientls = patientClientProxy.getPatientByFamily(family);
        model.addAttribute("patientList",patientls);
        return "patientTemplate/patientList";
    }

    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/addPatient")
    public String addPatientView (Model model, @RequestParam(value ="error", required = false) String error){
        model.addAttribute("patient",new Patient());
        model.addAttribute("error", error);
        return "patientTemplate/addPatient";
    }

    /**
     *
     * @param patient
     * @return a redirect to the patient list if success OR addPatientHist.html if fails
     */
    @PostMapping("/validatePatient")
    public String addAPatient (@ModelAttribute (value = "patient") Patient patient){
        try {
            patientClientProxy.addPatient(patient);
        } catch (PatientAlreadyExistException e){
            e.getMessage();
            return "patientTemplate/addPatient";
        }

        return "redirect:/central/getPatientList";
    }

    @GetMapping ("/updatePatient/{id}")
    public String updateAPatient (Model model, @PathVariable (value = "id") Long id) {
        model.addAttribute("patient", patientClientProxy.getPatientById(id));
        return "patientTemplate/updatePatient";
    }

    @PostMapping ("/validateUpdate/{id}")
    public String validatePatientUpdate (@ModelAttribute (value = "patient") Patient patient, @PathVariable (value ="id") Long id) {
        logger.info("in /validateUpdate");
        patientClientProxy.updatePatient(patient);
        return "redirect:/central/getPatientList";
    }

    @GetMapping("/deletePatientById/{id}")
    public String deletePatientById (@PathVariable (value = "id") Long id) throws NotFoundException {
        logger.info("in /deletePatientById");
        try {
            patientClientProxy.deletePatient(id);
        } catch (NotFoundException e){
            return "redirect:/central/getPatientList";
        }
        return "redirect:/central/getPatientList";
    }


}
