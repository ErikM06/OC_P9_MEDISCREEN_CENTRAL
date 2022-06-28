package com.mediscreen.central.proxy;

import com.mediscreen.central.Model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (name = "mediscreen-patient", url = "http://localhost:8081/patient/")
public interface PatientClientProxy {

    @GetMapping (value = "/getById")
    Patient getPatientById (@RequestParam Long id);

    @GetMapping(value = "/getPatientList", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Patient> getPatientList ();

    @PostMapping(value = "/add")
    Patient addPatient (@RequestBody Patient patient);

    @GetMapping ("/getPatientByFamily")
    List<Patient> getPatientByFamily (@RequestParam String family);

    @PostMapping ("/update")
    Patient updatePatient (@RequestBody Patient patient);

    @DeleteMapping("/deleteById")
    void deletePatient (@RequestParam Long id);

}
