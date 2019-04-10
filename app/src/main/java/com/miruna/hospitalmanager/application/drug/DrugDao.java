package com.miruna.hospitalmanager.application.drug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrugDao {
    public static List<Drug> findAllDrugs() {
        List<Drug> drugs = new ArrayList<>();
        drugs.addAll(Arrays.asList(DrugRestCaller.getAllDrugs()));
        return drugs;
    }
}
