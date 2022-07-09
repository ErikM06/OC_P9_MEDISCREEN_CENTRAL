package com.mediscreen.central.proxy;

import com.mediscreen.central.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (name = "mediscreen-patient", url = "http://localhost:8081/patient/")
public interface PatientClientProxy {

    @GetMapping (value = "/get-by-id")
    Patient getPatientById (@RequestParam Long id);

    @GetMapping(value = "/get-patient-list")
    List<Patient> getPatientList ();

    @PostMapping(value = "/add")
    Patient addPatient (@RequestBody Patient patient);

    @GetMapping ("/get-patient-by-family")
    List<Patient> getPatientByFamily (@RequestParam String family);

    @PostMapping ("/update")
    Patient updatePatient (@RequestBody Patient patient);

    @DeleteMapping("/delete-by-id")
    void deletePatient (@RequestParam Long id);

}
