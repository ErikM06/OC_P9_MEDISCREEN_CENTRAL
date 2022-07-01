package com.mediscreen.central.service;

import com.mediscreen.central.model.Patient;
import com.mediscreen.central.service.util.FamilyTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DiseaseRiskService {

    Logger logger = LoggerFactory.getLogger(DiseaseRiskService.class);

    @Autowired
    CalculateTriggerService calculateTriggerService;

    private final List<String> familyType = FamilyTypes.familyTypeList;

    /**
     *
     * @param patient
     * @return String the familyName
     */
    public String getDiseaseRisk (Patient patient){
        String risk = null;
        String choice = null;
        int numberOfTriggers = calculateTriggerService.getTriggerCount(patient.getId());
        Boolean moreThan30AndHaveTriggers = calculateIfMore30Years(patient.getDob());
        logger.info("in getDiseaseRisk, number of triggers: "+numberOfTriggers);

        if (numberOfTriggers > 8){
            choice = "EOS";
        } else if (moreThan30AndHaveTriggers && numberOfTriggers>1){

            choice = getChoiceForOlderThan30(numberOfTriggers);
        } else {
            switch (patient.getSex()){
                case "F" : choice = getChoiceForYoungerThan30Female(numberOfTriggers);
                    break;
                case "M" : choice = getChoiceForYoungerThan30Male(numberOfTriggers);
                    break;
            }
        }

        switch (Objects.requireNonNull(choice)){ //autocorrect Object.requireNonNull
            case "B" :
                risk = FamilyTypes.BORDERLINE;
                break;
            case "D" :
                risk = FamilyTypes.DANGER;
                break;
            case "EOS" :
                risk = FamilyTypes.EARLY_ON_SET;
                break;
            case "N":
                risk = FamilyTypes.NONE;
                break;
        }
        logger.info("in DiseaseRiskService");
        return risk;
    }

    private String getChoiceForOlderThan30(int numberOfTriggers) {
        String choice;
        switch (numberOfTriggers){
            case 2: choice = "B";
                break;
            case 6: choice = "D";
                break;
            case 8: choice = "EOS";
                break;
            default: choice ="N";
                break;

        }
        return choice;
    }
    private String getChoiceForYoungerThan30Male(int numberOfTriggers) {
        String choice;
        switch (numberOfTriggers){
            case 3: choice = "D";
                break;
            case 5: choice = "EOS";
                break;
            default: choice ="N";
                break;
        }
        return choice;
    }
    private String getChoiceForYoungerThan30Female(int numberOfTriggers) {
        String choice;
        switch (numberOfTriggers){
            case 4: choice = "D";
                break;
            case 7: choice = "EOS";
                break;
            default: choice ="N";
                break;
        }
        return choice;
    }

    /**
     * 1 years = 31556926
     * if currentDate - 30years is < than dob (birthdate) : Patient is younger than 30 Years
     * @param dob
     * @return
     */
    public Boolean calculateIfMore30Years (Date dob){
        boolean patientIsOlderThan30;
        Date currentDate = new Date(System.currentTimeMillis());
        LocalDate currentDate2 = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dob2 = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        patientIsOlderThan30 = !currentDate2.minusYears(30).isBefore(dob2);
        return patientIsOlderThan30;
    }
}
