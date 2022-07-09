package com.mediscreen.central.controller;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.model.Patient;
import com.mediscreen.central.model.PatientNotes;
import com.mediscreen.central.proxy.PatientClientProxy;
import com.mediscreen.central.proxy.PatientNotesClientProxy;
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
public class PatientNotesController {

    Logger logger = LoggerFactory.getLogger(PatientNotesController.class);
    @Autowired
    private  PatientNotesClientProxy patientNotesClientProxy;

    @Autowired
    private  PatientClientProxy patientClientProxy;


    private final List<String> familyTypesLs = FamilyTypes.familyTypeList;


    @GetMapping("/get-patient-notes-list/{patId}")
    public String getPatient(Model model,@PathVariable (value = "patId") Long id, @RequestParam(value = "error", required = false) String error) {
        try {
            model.addAttribute("patNotesList", patientNotesClientProxy.getPatientNotesByPatId(id));
            model.addAttribute("patient", patientClientProxy.getPatientById(id));
        } catch (NotFoundException e){
            model.addAttribute("error", error);
            return "redirect:/central/add-patient-notes/{patId}";
        }
        return "patientNotesTemplates/patientNotesList";
    }


    /**
     *
     * @param model
     * @return UI for 'addPatient'
     */
    @GetMapping("/add-patient-notes/{patId}")
    public String addPatientView (Model model, @PathVariable (value = "patId") Long id){
        Patient patient = patientClientProxy.getPatientById(id);
        PatientNotes patientNotes = new PatientNotes(ObjectId.get().toString(), id, patient.getLastname());
        model.addAttribute("patientNotes", patientNotes);
        return "patientNotesTemplates/addPatientNotes";
    }

    /**
     *
     * @param patientNotes
     * @return a redirect to the patient list if success OR addPatientNotes.html if fails
     */
    @PostMapping("/validate-patient-notes/{patId}")
    public String addAPatient (@ModelAttribute(value = "patientNotes") PatientNotes patientNotes, @PathVariable (value = "patId") Long id){

        patientNotes = patientNotesClientProxy.addPatientNotes(patientNotes);


        return "redirect:/central/get-patient-notes-list/"+ patientNotes.getPatId();
    }

    @GetMapping ("/update-patient-notes/{id}")
    public String updateAPatient (Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("patientNotes", patientNotesClientProxy.getPatientNotesById(id));
        model.addAttribute("familyList", familyTypesLs);
        return "patientNotesTemplates/updatePatientNotes";
    }

    @PostMapping ("/validate-patient-notes-update/{id}")
    public String validatePatientUpdate (@ModelAttribute (value = "patientNotes") PatientNotes patientNotes, @PathVariable (value ="id") String id) {
        logger.info("in /validatePatientNotesUpdate");
        patientNotesClientProxy.updatePatientNotes(patientNotes);
        return "redirect:/central/get-patient-list";
    }

    @GetMapping("/delete-patient-notes-by-id/{id}")
    public String deletePatientById ( @PathVariable (value = "id") String id){
        logger.info("in /deletePatientNotesById");
        Long patId = null;
        try {
            patId = patientNotesClientProxy.getPatientNotesById(id).getPatId();
            patientNotesClientProxy.deletePatNotesById(id);
        } catch (NotFoundException e){

            return "redirect:/central/get-patient-notes-list/"+patId;
        }
        return "redirect:/central/get-patient-notes-list/"+patId;
    }


}
