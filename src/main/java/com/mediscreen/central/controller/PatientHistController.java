package com.mediscreen.central.controller;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.model.PatientHist;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.proxy.PatientHistClientProxy;
import com.mediscreen.central.service.util.FamilyTypes;
import org.bson.types.ObjectId;
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
@RequestMapping ("/central")
public class PatientHistController {

    Logger logger = LoggerFactory.getLogger(PatientHistController.class);

    private final PatientHistClientProxy patientHistClientProxy;

    private final PatientClientProxy patientClientProxy;

    private final List<String> familyTypesLs = FamilyTypes.familyTypeList;


    public PatientHistController(PatientHistClientProxy patientHistClientProxy, PatientClientProxy patientClientProxy) {
        this.patientHistClientProxy = patientHistClientProxy;
        this.patientClientProxy = patientClientProxy;
    }

    @GetMapping("/get-patient-hist-list/{patId}")
    public String getPatient(Model model,@PathVariable (value = "patId") Long id, @RequestParam(value = "error", required = false) String error) {
        try {
            model.addAttribute("patHistList", patientHistClientProxy.getPatientHistByPatId(id));
        } catch (NotFoundException e){
            return "redirect:/central/add-patient-hist/{patId}";
        }
        if (null != error) {
            model.addAttribute("error", error);
        }
        return "patientHistTemplates/patientHistList";
    }


    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/add-patient-hist/{patId}")
    public String addPatientView (Model model, @PathVariable (value = "patId") Long id){
        Patient patient = patientClientProxy.getPatientById(id);
        PatientHist patientHist = new PatientHist(ObjectId.get().toString(), id, patient.getFamily());
        model.addAttribute("patientHist", patientHist);
        return "patientHistTemplates/addPatientHist";
    }

    /**
     *
     * @param patientHist
     * @return a redirect to the patient list if success OR addPatientHist.html if fails
     */
    @PostMapping("/validate-patient-hist/{id}")
    public String addAPatient (@ModelAttribute(value = "patientHist") PatientHist patientHist, @PathVariable (value = "id") String id){

        patientHistClientProxy.addPatientHistory(patientHist);

        return "redirect:/central/get-patient-hist-list/"+patientHist.getPatId();
    }

    @GetMapping ("/update-patient-hist/{id}")
    public String updateAPatient (Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("patientHist", patientHistClientProxy.getPatientHistById(id));
        model.addAttribute("familyList", familyTypesLs);
        return "patientHistTemplates/updatePatientHist";
    }

    @PostMapping ("/validate-patient-hist-update/{id}")
    public String validatePatientUpdate (@ModelAttribute (value = "patientHist") PatientHist patientHist, @PathVariable (value ="id") String id) {
        logger.info("in /validatePatientHistUpdate");
        patientHistClientProxy.updatePatientHistory(patientHist);
        return "redirect:/central/get-patient-list";
    }

    @GetMapping("/delete-patient-hist-by-id/{id}")
    public String deletePatientById ( @PathVariable (value = "id") String id){
        logger.info("in /deletePatientHistById");
        Long patId = null;
        try {
            patId = patientHistClientProxy.getPatientHistById(id).getPatId();
            patientHistClientProxy.deletePatHistoryById(id);
        } catch (NotFoundException e){

            return "redirect:/central/get-patient-hist-list/"+patId;
        }
        return "redirect:/central/get-patient-hist-list/"+patId;
    }


}
