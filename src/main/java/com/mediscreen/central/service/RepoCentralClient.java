package com.mediscreen.central.service;

import com.mediscreen.central.Model.Patient;
import com.mediscreen.central.customExceptions.FamilyDoesNotMatchException;
import com.mediscreen.central.service.util.FamilyTypes;
import com.mediscreen.central.service.util.PatientClientErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

/**
 * Service calling RepoCentral api
 * on http://localhost:8081/patient
 * All crud methods for Patient
 */
@Service
public class RepoCentralClient {

    Logger logger = LoggerFactory.getLogger(RepoCentralClient.class);

    private RestTemplate restTemplate;

    @Autowired
    public RepoCentralClient (RestTemplateBuilder restTemplateBuilder){
        restTemplate = restTemplateBuilder
                .errorHandler(new PatientClientErrorHandler())
                .build();
    };


    private final String baseUrl = "http://localhost:8081";
    private final String patientUrl = "/patient";
    private String getAllPatient = "/getPatientList";
    private String getAllPatientByFamily="/getPatientByFamily";

    private String addAPatient = "/add";

    private String updateAPatient ="/update";

    private String getById ="/getById";

    private String deleteById ="/deleteById";

    /**
     * @return all family type as list
     */
    public List<String> getAllFamilyType (){
        List<String> allFamilyTypes = new ArrayList<>();
        allFamilyTypes.add(FamilyTypes.BORDERLINE);
        allFamilyTypes.add(FamilyTypes.NONE);
        allFamilyTypes.add(FamilyTypes.DANGER);
        allFamilyTypes.add(FamilyTypes.EARLY_ON_SET);
        return allFamilyTypes;
    }

    /**
     * Read method
     * @return all patient as a list
     */
    public List<?> getAllPatient (){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Patient>> patientLs = null;


        patientLs = restTemplate.exchange(baseUrl + patientUrl + getAllPatient,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }

        );

        return patientLs.getBody();
    }

    /**
     * read method
     * @param family
     * @return all patient with a given family type as a list
     */
    public List<Patient> getAllPatientByFamily (String family) {
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

    /**
     * create method
     * post a Patient entity on the given url
     * @param patient
     * @throws FamilyDoesNotMatchException
     */
    public void addAPatientClient (Patient patient) throws FamilyDoesNotMatchException {
        if (getAllFamilyType().stream().noneMatch(s -> s.equals(patient.getFamily()))){
            logger.info("Error in patientService getByFamily : FamilyDoesNotMatchException");
            throw new FamilyDoesNotMatchException("family "+ patient.getFamily()+" does not match any family");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> patientRequest = new HttpEntity<>(patient);

        restTemplate.postForEntity(baseUrl + patientUrl + addAPatient
                , patientRequest, Patient.class);

    }

    /**
     * update method
     * post a Patient entity on the given url with the given ID
     *
     * @param patient new patient info
     * @param id      update the patient at the given id
     * @throws FamilyDoesNotMatchException
     */
    public void updatePatientClient (Patient patient, Long id) throws FamilyDoesNotMatchException {
        logger.info("in updatePatientClient, id is: "+patient.getId());
        if (getAllFamilyType().stream().noneMatch(s -> s.equals(patient.getFamily()))){
            logger.info("Error in patientService updatePatientClient : FamilyDoesNotMatchException");
            throw new FamilyDoesNotMatchException("family "+ patient.getFamily()+" does not match any family");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        patient.setId(id);
        HttpEntity<Patient> patientRequest = new HttpEntity<>(patient);

        restTemplate.postForEntity(baseUrl + patientUrl + updateAPatient
                , patientRequest
                , Patient.class);

    }

    /**
     * read
     * @param id
     * @return Patient with the given id
     */
    public Patient getPatientById(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Patient> patientResponseEntity = restTemplate.getForEntity(baseUrl + patientUrl + getById +
                        "?id=" + id,
                Patient.class);

        return patientResponseEntity.getBody();
    }

    public void deletePatientById(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.delete(baseUrl + patientUrl + deleteById +
                "?id=" +id);
    }

}
