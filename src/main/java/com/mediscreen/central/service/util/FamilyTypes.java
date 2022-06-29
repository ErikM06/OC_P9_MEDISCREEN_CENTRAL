package com.mediscreen.central.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface FamilyTypes {

    String NONE = "TestNone";
    String BORDERLINE = "TestBorderline";
    String DANGER ="TestInDanger";
    String EARLY_ON_SET="TestEarlyOnSet";

    List<String> familyTypeList = new ArrayList<>(Arrays.asList(NONE,BORDERLINE,DANGER,EARLY_ON_SET));
}
