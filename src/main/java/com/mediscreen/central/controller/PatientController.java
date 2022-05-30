package com.mediscreen.central.controller;

import com.mediscreen.central.Model.Patient;
import com.mediscreen.central.customExceptions.FamilyDoesNotMatchException;
import com.mediscreen.central.service.PatientRepoService;
import com.mediscreen.central.service.util.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/central")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepoService patientService;

    @Autowired
    DateParser parser;



    @GetMapping ("/getPatientList")
    public String getPatient(Model model,@RequestParam(value = "error", required = false) String error) {

        model.addAttribute("patientList",patientService.getAllPatient());
        if (null != error) {
            model.addAttribute("error", error);
        }
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

    @PostMapping("/validatePatient")
    public ModelAndView addAPatient (@ModelAttribute (value = "patient") Patient patient, BindingResult result){
        try {
            patientService.addAPatientClient(patient);
        } catch (FamilyDoesNotMatchException e) {
            logger.info("error "+ patient.getFamily()+" does not exist / In validatePatient");
            return new ModelAndView("addPatient", "error", e.getMessage());
        }

        return new ModelAndView("redirect:patientList");
    }

}
