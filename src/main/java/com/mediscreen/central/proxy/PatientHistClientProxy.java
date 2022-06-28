package com.mediscreen.central.proxy;

import com.mediscreen.central.Model.PatientHist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (value = "mediscreen-hist", url ="http://localhost:8082/patHistory/")
public interface PatientHistClientProxy {

    @PostMapping("/add")
    PatientHist addPatientHistory (@RequestBody PatientHist patient);

    @GetMapping ("/getById")
    PatientHist getPatientHistById (@RequestParam String id);

    @GetMapping ("/getAllPatientHistory")
    List<PatientHist> getAllPatientsHist();

    @PostMapping ("/updatePatientHistory")
    PatientHist updatePatientHistory(@RequestParam PatientHist patient);


    @DeleteMapping("/deleteById")
    void deletePatHistoryById (@RequestParam String id);


}
