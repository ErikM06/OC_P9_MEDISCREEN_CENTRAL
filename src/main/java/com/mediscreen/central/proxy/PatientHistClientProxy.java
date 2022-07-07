package com.mediscreen.central.proxy;

import com.mediscreen.central.model.PatientHist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (value = "mediscreen-hist", url ="http://localhost:8082/pat-history/")
public interface PatientHistClientProxy {

    @PostMapping("/add")
    PatientHist addPatientHistory (@RequestBody PatientHist patientHist);

    @GetMapping ("/get-by-id")
    PatientHist getPatientHistById (@RequestParam String id);

    @GetMapping ("/get-by-pat-id")
    List<PatientHist> getPatientHistByPatId (@RequestParam Long id);

    @GetMapping ("/get-all-patient-history")
    List<PatientHist> getAllPatientsHist();

    @PostMapping ("/update-patient-history")
    PatientHist updatePatientHistory(@RequestBody PatientHist patientHist);


    @GetMapping("/delete-by-id")
    void deletePatHistoryById (@RequestParam String id);


}
