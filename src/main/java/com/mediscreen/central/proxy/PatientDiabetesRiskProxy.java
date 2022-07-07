package com.mediscreen.central.proxy;


import com.mediscreen.central.dto.PatientAssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mediscreen-diabetesRiskService", url = "http://localhost:8080/assess/")
public interface PatientDiabetesRiskProxy {

    @GetMapping("/id")
    ResponseEntity<PatientAssessmentDTO> getAssessById (Long id);

    @GetMapping("/familyName")
    ResponseEntity<List<PatientAssessmentDTO>> getAssessByFamilyName (@RequestParam String familyName);
}
