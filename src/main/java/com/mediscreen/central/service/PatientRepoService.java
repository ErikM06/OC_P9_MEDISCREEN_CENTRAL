package com.mediscreen.central.service;

import com.mediscreen.central.Model.Patient;
import com.mediscreen.central.customExceptions.FamilyDoesNotMatchException;
import com.mediscreen.central.service.util.FamilyTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PatientRepoService {

    Logger logger = LoggerFactory.getLogger(PatientRepoService.class);

    private String baseUrl = "http://localhost:8081";
    private String patientUrl = "/patient";
    private String getAllPatient = "/getPatientList";
    private String getAllPatientByFamily="/getPatientByFamily";

    private String addAPatient = "/add";


    public List<String> getAllFamilyType (){
        List<String> allFamilyTypes = new ArrayList<>();
        allFamilyTypes.add(FamilyTypes.BORDERLINE);
        allFamilyTypes.add(FamilyTypes.NONE);
        allFamilyTypes.add(FamilyTypes.DANGER);
        allFamilyTypes.add(FamilyTypes.EARLY_ON_SET);
        return allFamilyTypes;
    }

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

    public Patient addAPatientClient (Patient patient) throws FamilyDoesNotMatchException {
        RestTemplate restTemplate = new RestTemplate();
        if (getAllFamilyType().stream().noneMatch(s -> s.equals(patient.getFamily()))){
            logger.info("Error in patientService getByFamily : FamilyDoesNotMatchException");
            throw new FamilyDoesNotMatchException("family "+ patient.getFamily()+" does not match any family");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> patientRequest = new HttpEntity<>(patient);
        ResponseEntity<Patient> patientResponseEntity = restTemplate.postForEntity(baseUrl + patientUrl + addAPatient
        ,patientRequest, Patient.class);

        return patientResponseEntity.getBody();
    }
}
