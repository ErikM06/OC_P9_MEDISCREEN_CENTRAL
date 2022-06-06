package com.mediscreen.central.controller;

import com.mediscreen.central.Model.Patient;
import com.mediscreen.central.customExceptions.FamilyDoesNotMatchException;
import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.service.RepoCentralClient;
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


@Controller
@RequestMapping("/central")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    RepoCentralClient patientService;

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

    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/addPatient")
    public String addPatientView (Model model){
        model.addAttribute("patient",new Patient());
        return "addPatient";
    }

    /**
     *
     * @param patient
     * @param result
     * @return a redirect to the patient list if success OR addPatient.html if fails
     */
    @PostMapping("/validatePatient")
    public ModelAndView addAPatient (@ModelAttribute (value = "patient") Patient patient, BindingResult result){
        try {
            patientService.addAPatientClient(patient);
        } catch (FamilyDoesNotMatchException e) {
            logger.info("error "+ patient.getFamily()+" does not exist / In validatePatient");
            return new ModelAndView("addPatient", "error", e.getMessage());
        }

        return new ModelAndView("redirect:/central/getPatientList");
    }

    @GetMapping ("/updatePatient/{id}")
    public String updateAPatient (Model model, @PathVariable (value = "id") Long id) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "updatePatient";
    }

    @PostMapping ("/validateUpdate/{id}")
    public ModelAndView validatePatientUpdate (@ModelAttribute (value = "patient") Patient patient, @PathVariable (value ="id") Long id) {
        try {
            logger.info("in /validateUpdate");
            patientService.updatePatientClient(patient, id);
        } catch (FamilyDoesNotMatchException e) {
            return new ModelAndView("updatePatient", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/central/getPatientList");
    }

    @GetMapping("/deletePatientById/{id}")
    public ModelAndView deletePatientById (@PathVariable (value = "id") Long id, PatientClientErrorHandler patientClientErrorHandler) throws NotFoundException {
        logger.info("in /deletePatientById");
        try {
            patientService.deletePatientById(id);
        } catch (RestClientResponseException e){

            return new ModelAndView("redirect:/central/getPatientList", "error", patientClientErrorHandler.mountMessage(e));
        }
        return new ModelAndView("redirect:/central/getPatientList");
    }


}
