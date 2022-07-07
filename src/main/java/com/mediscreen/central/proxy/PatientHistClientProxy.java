package com.mediscreen.central.proxy;

import com.mediscreen.central.model.PatientHist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (value = "mediscreen-hist", url ="http://localhost:8082/patHistory/")
public interface PatientHistClientProxy {

    @PostMapping("/add")
    PatientHist addPatientHistory (@RequestBody PatientHist patientHist);

    @GetMapping ("/getById")
    PatientHist getPatientHistById (@RequestParam String id);

    @GetMapping ("/getByPatId")
    List<PatientHist> getPatientHistByPatId (@RequestParam Long id);

    @GetMapping ("/getAllPatientHistory")
    List<PatientHist> getAllPatientsHist();

    @PostMapping ("/update-patient-history")
    PatientHist updatePatientHistory(@RequestBody PatientHist patientHist);


    @GetMapping("/deleteById")
    void deletePatHistoryById (@RequestParam String id);


}
