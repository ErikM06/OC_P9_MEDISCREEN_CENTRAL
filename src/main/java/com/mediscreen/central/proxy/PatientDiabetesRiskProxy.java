package com.mediscreen.central.proxy;


import com.mediscreen.central.dto.PatientAssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mediscreen-diabetesRiskService", url = "http://localhost:8080/assess/")
public interface PatientDiabetesRiskProxy {

    @GetMapping("/id")
    PatientAssessmentDTO getAssessById (@RequestParam Long id);

    @GetMapping("/familyName")
    List<PatientAssessmentDTO> getAssessByFamilyName (@RequestParam String familyName);
}
