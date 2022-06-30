package com.mediscreen.central.controller;

import com.mediscreen.central.model.Patient;
import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.service.util.DateParser;
import com.mediscreen.central.service.util.PatientClientErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/central")
public class PatientController {
    Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final PatientClientProxy patientClientProxy;

    public PatientController (PatientClientProxy patientClientProxy){
        this.patientClientProxy=patientClientProxy;
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
    public String addPatientView (Model model){
        model.addAttribute("patient",new Patient());
        return "patientTemplate/addPatient";
    }

    /**
     *
     * @param patient
     * @param result
     * @return a redirect to the patient list if success OR addPatientHist.html if fails
     */
    @PostMapping("/validatePatient")
    public ModelAndView addAPatient (@ModelAttribute (value = "patient") Patient patient, BindingResult result){
        patientClientProxy.addPatient(patient);

        return new ModelAndView("redirect:/central/getPatientList");
    }

    @GetMapping ("/updatePatient/{id}")
    public String updateAPatient (Model model, @PathVariable (value = "id") Long id) {
        model.addAttribute("patient", patientClientProxy.getPatientById(id));
        return "patientTemplate/updatePatient";
    }

    @PostMapping ("/validateUpdate/{id}")
    public ModelAndView validatePatientUpdate (@ModelAttribute (value = "patient") Patient patient, @PathVariable (value ="id") Long id) {
        logger.info("in /validateUpdate");
        patientClientProxy.updatePatient(patient);
        return new ModelAndView("redirect:/central/getPatientList");
    }

    @GetMapping("/deletePatientById/{id}")
    public ModelAndView deletePatientById (@PathVariable (value = "id") Long id, PatientClientErrorHandler patientClientErrorHandler) throws NotFoundException {
        logger.info("in /deletePatientById");
        try {
            patientClientProxy.deletePatient(id);
        } catch (RestClientResponseException e){

            return new ModelAndView("redirect:/central/getPatientList", "error", patientClientErrorHandler.mountMessage(e));
        }
        return new ModelAndView("redirect:/central/getPatientList");
    }


}
