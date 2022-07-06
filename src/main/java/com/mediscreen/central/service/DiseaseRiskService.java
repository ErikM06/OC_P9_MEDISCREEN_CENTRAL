package com.mediscreen.central.service;

import com.mediscreen.central.model.Patient;
import com.mediscreen.central.service.util.CalculateAgeFromDob;
import com.mediscreen.central.service.util.FamilyTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
/**
 * This service Calculate the risk for a patient to contract diabetes
 */
@Service
public class DiseaseRiskService {

    Logger logger = LoggerFactory.getLogger(DiseaseRiskService.class);

    @Autowired
    CalculateTriggerService calculateTriggerService;

    @Autowired
    CalculateAgeFromDob calculateAgeFromDob;

    /**
     * The risk/number of trigger words in  medical observations change with the sex and the age of the patient
     * First we get a Patient sex, then the age, finally we evaluate the risk with those 2 parameters
     * and the number of triggers.
     * @param patient the Patient to evaluate
     * @return the familyName of diabetes risk as a String
     */
    public String getDiseaseRisk(Patient patient) {
        String risk = null;
        String choice = null;
        int numberOfTriggers = calculateTriggerService.getTriggerCount(patient.getId());
        int patientAge = calculateAgeFromDob.calculateAge(patient.getDob());
        logger.info("in getDiseaseRisk, number of triggers: " + numberOfTriggers);
        logger.info("patientAge is: " + patientAge);


        switch (patient.getSex()) { // switch the method for Male or Female
            case "F":
                choice = getChoiceForFemale(patientAge, numberOfTriggers);
                break;
            case "M":
                choice = getChoiceForMale(patientAge, numberOfTriggers);
                break;
        }

        switch (Objects.requireNonNull(choice)) { // the end switch which will receive a String indicating the risk
            case "B":
                risk = FamilyTypes.BORDERLINE;
                break;
            case "D":
                risk = FamilyTypes.DANGER;
                break;
            case "EOS":
                risk = FamilyTypes.EARLY_ON_SET;
                break;
            case "N":
                risk = FamilyTypes.NONE;
                break;
        }
        logger.info("end of getDiseaseRisk");
        return risk;
    }

    /**
     *
     * @param patientAge
     * @param numberOfTriggers
     * @return the diabetes risk indication as a String
     */
    private String getChoiceForMale(int patientAge, int numberOfTriggers) {
        String riskLevel = null;
        int choiceByAge = 1;
        if (patientAge >= 30) {
            choiceByAge = 2;
        }
        // 2 case and multiples if statement as risk change with age and number and triggers
        switch (choiceByAge) { // if patient < 30 -> case 1 || if patient >= 30 -> case 2
            case 1:
                int choiceByTriggers = 1;

                if (numberOfTriggers >= 3 && numberOfTriggers < 5) {
                    choiceByTriggers = 2;
                }
                if (numberOfTriggers >= 5) {
                    choiceByTriggers = 3;
                }
                riskLevel = riskLevelChoiceLessThan30(choiceByTriggers);
                break;
            case 2:
                int choiceByTriggers2 = 1;

                if (numberOfTriggers >= 2 && numberOfTriggers < 6) {
                    choiceByTriggers2 = 2;
                }
                if (numberOfTriggers >= 6 && numberOfTriggers < 8) {
                    choiceByTriggers2 = 3;
                }
                if (numberOfTriggers >= 8) {
                    choiceByTriggers2 = 4;
                }
                riskLevel = riskLevelChoiceLessThan30(choiceByTriggers2);
                break;
        }
        return riskLevel;
    }

    private String getChoiceForFemale(int patientAge, int numberOfTriggers) {
        String riskLevel = null;
        int choiceByAgeF = 1;
        if (patientAge >= 30) {
            choiceByAgeF = 2;
        }

        // 2 case and multiples if statement as risk change with age and number and triggers
        switch (choiceByAgeF) { // if patient < 30 -> case 1 || if patient >= 30 -> case 2
            case 1:
                int choiceByTriggersF = 1;

                if (numberOfTriggers >= 4 && numberOfTriggers < 7) {
                    choiceByTriggersF = 2;
                }
                if (numberOfTriggers >= 7) {
                    choiceByTriggersF = 3;
                }
                riskLevel = riskLevelChoiceLessThan30(choiceByTriggersF);
                break;

            case 2:
                int choiceByTriggers2 = 1;
                if (numberOfTriggers >= 2 && numberOfTriggers < 6) {
                    choiceByTriggers2 = 2;
                }
                if (numberOfTriggers >= 6 && numberOfTriggers < 8) {
                    choiceByTriggers2 = 3;
                }
                if (numberOfTriggers >= 8) {
                    choiceByTriggers2 = 4;
                }
                riskLevel = riskLevelChoiceMoreThan30(choiceByTriggers2);
                break;

        }
        return riskLevel;
    }

    private String riskLevelChoiceMoreThan30(int choiceByTriggers2) {
        String riskLevel = null;
        switch (choiceByTriggers2) {
            case 1:
                riskLevel = "N";
                break;
            case 2:
                riskLevel = "B";
                break;
            case 3:
                riskLevel = "D";
                break;
            case 4:
                riskLevel = "EOS";
                break;
        }
        return riskLevel;
    }

    private String riskLevelChoiceLessThan30(int choiceByTriggersF) {
        String riskLevel = null;
        switch (choiceByTriggersF) {
            case 1:
                riskLevel = "N";
                break;
            case 2:
                riskLevel = "D";
                break;
            case 3:
                riskLevel = "EOS";
                break;
        }
        return riskLevel;
    }

}


