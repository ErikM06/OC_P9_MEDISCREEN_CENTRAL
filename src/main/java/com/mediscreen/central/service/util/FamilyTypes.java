package com.mediscreen.central.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface FamilyTypes {

    String NONE = "None";
    String BORDERLINE = "Borderline";
    String DANGER ="InDanger";
    String EARLY_ON_SET="EarlyOnSet";

    List<String> familyTypeList = new ArrayList<>(Arrays.asList(NONE,BORDERLINE,DANGER,EARLY_ON_SET));
}
