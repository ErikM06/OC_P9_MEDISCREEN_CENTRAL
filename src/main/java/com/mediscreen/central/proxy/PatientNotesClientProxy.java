package com.mediscreen.central.proxy;

import com.mediscreen.central.model.PatientNotes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (value = "mediscreen-notes",url ="http://localhost:8082/pat-notes/")
public interface PatientNotesClientProxy {



    @PostMapping("/add")
    PatientNotes addPatientNotes(@RequestBody PatientNotes patientNotes);



    @GetMapping ("/get-by-id")
    PatientNotes getPatientNotesById(@RequestParam String id);

    @GetMapping ("/get-by-pat-id")
    List<PatientNotes> getPatientNotesByPatId(@RequestParam Long id);

    @GetMapping ("/get-all-patient-notes")
    List<PatientNotes> getAllPatientsNotes();

    @PostMapping ("/update-patient-notes")
    PatientNotes updatePatientNotes(@RequestBody PatientNotes patientNotes);


    @GetMapping("/delete-by-id")
    void deletePatNotesById(@RequestParam String id);


}
