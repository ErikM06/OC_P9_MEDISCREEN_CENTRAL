package com.mediscreen.central.service;

import com.mediscreen.central.Model.Patient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientRepoService {

    private String baseUrl = "http://localhost:8081";
    private String patientUrl = "/patient";
    private String getAllPatient = "/getPatientList";
    private String getAllPatientByFamily="/getPatientByFamily";

    private String addAPatient = "/add";

    public List<Patient> getAllPatient (){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Patient>> patientLs = restTemplate.exchange(baseUrl + patientUrl + getAllPatient,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return patientLs.getBody();
    }

    public List<Patient> getAllPatientByFamily (String family) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Patient>> patientByFamilyLs = restTemplate.exchange(baseUrl + patientUrl + getAllPatientByFamily
                        + "?family=" + family,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    return patientByFamilyLs.getBody();
    }

    public Patient addAPatientClient (Patient patient){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Patient> patientResponseEntity = restTemplate.getForEntity(baseUrl + patientUrl + addAPatient
        + "?" + patient, Patient.class, httpHeaders);
        return patientResponseEntity.getBody();
    }
}
