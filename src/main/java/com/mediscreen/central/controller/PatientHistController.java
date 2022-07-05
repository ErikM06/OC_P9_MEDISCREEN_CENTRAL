package com.mediscreen.central.controller;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.model.PatientHist;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.proxy.PatientHistClientProxy;
import com.mediscreen.central.service.util.DateParser;
import com.mediscreen.central.service.util.FamilyTypes;
import org.bson.types.ObjectId;
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
@RequestMapping ("/central")
public class PatientHistController {

    Logger logger = LoggerFactory.getLogger(PatientHistController.class);

    private final PatientHistClientProxy patientHistClientProxy;

    private final PatientClientProxy patientClientProxy;

    private final List<String> familyTypesLs = FamilyTypes.familyTypeList;

    @Autowired
    DateParser parser;

    public PatientHistController(PatientHistClientProxy patientHistClientProxy, PatientClientProxy patientClientProxy) {
        this.patientHistClientProxy = patientHistClientProxy;
        this.patientClientProxy = patientClientProxy;
    }

    @GetMapping("/getPatientHistList/{patId}")
    public String getPatient(Model model,@PathVariable (value = "patId") Long id, @RequestParam(value = "error", required = false) String error) {
        try {
            model.addAttribute("patHistList", patientHistClientProxy.getPatientHistByPatId(id));
        } catch (NotFoundException e){
            return "redirect:/central/addPatientHist/{patId}";
        }
        if (null != error) {
            model.addAttribute("error", error);
        }
        return "patientHistTemplate/patientHistList";
    }


    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/addPatientHist/{patId}")
    public String addPatientView (Model model, @PathVariable (value = "patId") Long id){
        Patient patient = patientClientProxy.getPatientById(id);
        PatientHist patientHist = new PatientHist(ObjectId.get().toString(), id, patient.getFamily());
        model.addAttribute("patientHist", patientHist);
        return "patientHistTemplate/addPatientHist";
    }

    /**
     *
     * @param patientHist
     * @return a redirect to the patient list if success OR addPatientHist.html if fails
     */
    @PostMapping("/validatePatientHist/{id}")
    public String addAPatient (@ModelAttribute(value = "patientHist") PatientHist patientHist, @PathVariable (value = "id") String id){

        patientHistClientProxy.addPatientHistory(patientHist);

        return "redirect:/central/getPatientHistList/"+patientHist.getPatId();
    }

    @GetMapping ("/updatePatientHist/{id}")
    public String updateAPatient (Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("patientHist", patientHistClientProxy.getPatientHistById(id));
        model.addAttribute("familyList", familyTypesLs);
        return "patientHistTemplate/updatePatientHist";
    }

    @PostMapping ("/validatePatientHistUpdate/{id}")
    public String validatePatientUpdate (@ModelAttribute (value = "patientHist") PatientHist patientHist, @PathVariable (value ="id") String id) {
        logger.info("in /validatePatientHistUpdate");
        patientHistClientProxy.updatePatientHistory(patientHist);
        return "redirect:/central/getPatientList";
    }

    @GetMapping("/deletePatientHistById/{id}")
    public String deletePatientById ( @PathVariable (value = "id") String id){
        logger.info("in /deletePatientHistById");
        Long patId = null;
        try {
            patId = patientHistClientProxy.getPatientHistById(id).getPatId();
            patientHistClientProxy.deletePatHistoryById(id);
        } catch (NotFoundException e){

            return "redirect:/central/getPatientHistList/"+patId;
        }
        return "redirect:/central/getPatientHistList/"+patId;
    }


}
