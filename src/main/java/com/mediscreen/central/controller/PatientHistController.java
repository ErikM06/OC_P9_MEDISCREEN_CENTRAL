package com.mediscreen.central.controller;

import com.mediscreen.central.Model.PatientHist;
import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.proxy.PatientHistClientProxy;
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
@RequestMapping ("/central")
public class PatientHistController {

    Logger logger = LoggerFactory.getLogger(PatientHistController.class);

    private final PatientHistClientProxy patientHistClientProxy;

    private final PatientClientProxy patientClientProxy;

    @Autowired
    DateParser parser;

    public PatientHistController(PatientHistClientProxy patientHistClientProxy, PatientClientProxy patientClientProxy) {
        this.patientHistClientProxy = patientHistClientProxy;
        this.patientClientProxy = patientClientProxy;
    }

    @GetMapping("/getPatientHistList")
    public String getPatient(Model model, @RequestParam(value = "error", required = false) String error) {

        model.addAttribute("patHistList",  patientHistClientProxy.getAllPatientsHist());
        if (null != error) {
            model.addAttribute("error", error);
        }
        return "PatientHistTemplate/patientHistList";
    }


    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/addPatientHist")
    public String addPatientView (Model model){
        model.addAttribute("patientLs", patientClientProxy.getPatientList());
        model.addAttribute("patientHist",new PatientHist());
        return "PatientHistTemplate/addPatientHist";
    }

    /**
     *
     * @param patientHist
     * @param result
     * @return a redirect to the patient list if success OR addPatientHist.html if fails
     */
    @PostMapping("/validatePatientHist")
    public ModelAndView addAPatient (@ModelAttribute(value = "patientHist") PatientHist patientHist, BindingResult result){
        patientHistClientProxy.addPatientHistory(patientHist);

        return new ModelAndView("redirect:/central/getPatientHistList");
    }

    @GetMapping ("/updatePatientHist/{id}")
    public String updateAPatient (Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("patientHist", patientHistClientProxy.getPatientHistById(id));
        return "PatientHistTemplate/updatePatientHist";
    }

    @PostMapping ("/validatePatientHistUpdate/{id}")
    public ModelAndView validatePatientUpdate (@ModelAttribute (value = "patientHist") PatientHist patientHist, @PathVariable (value ="id") String id) {
        logger.info("in /validatePatientHistUpdate");
        patientHistClientProxy.updatePatientHistory(patientHist);
        return new ModelAndView("redirect:/central/getPatientHistList");
    }

    @GetMapping("/deletePatientHistById/{id}")
    public ModelAndView deletePatientById (@PathVariable (value = "id") String id, PatientClientErrorHandler patientClientErrorHandler) throws NotFoundException {
        logger.info("in /deletePatientHistById");
        try {
            patientHistClientProxy.deletePatHistoryById(id);
        } catch (RestClientResponseException e){

            return new ModelAndView("redirect:/central/getPatientHistList", "error", patientClientErrorHandler.mountMessage(e));
        }
        return new ModelAndView("redirect:/central/getPatientHistList");
    }


}
