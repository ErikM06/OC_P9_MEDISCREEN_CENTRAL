package com.mediscreen.central.proxy;

import com.mediscreen.central.Model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (name = "mediscreen-patient")
public interface PatientClientProxy {

    @GetMapping (value = "/getById")
    Patient getPatientById (@RequestParam Long id);

    @GetMapping(value = "/getPatientList")
    List<Patient> getPatientList ();

    @PostMapping(value = "/add")
    ResponseEntity<?> addPatient (@RequestBody Patient patient);

    @GetMapping ("/getPatientByFamily")
    ResponseEntity<List<Patient>>getPatientByFamily (@RequestParam String family);

    @PostMapping ("/update")
    ResponseEntity<?> updatePatient (@RequestBody Patient patient);

    @DeleteMapping("/deleteById")
    void deletePatient (@RequestParam Long id);

}
