package com.miruna.hospitalmanager.application.drug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DrugService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<Drug> drugs;

    public List<Drug> findAllDrugs() {

        drugs = DrugDao.findAllDrugs();

        return drugs;
    }

    public Drug findById(String id) {
        for (Drug drug : drugs) {
            if (drug.getId().equals(id)) {
                return drug;
            }
        }
        return null;
    }

    public Drug findByName(String name) {
        for (Drug drug : drugs) {
            if (drug.getName().equalsIgnoreCase(name)) {
                return drug;
            }
        }
        return null;
    }

    public Drug createDrug(Drug drug) {
        drugs.add(drug);
        return drug;
    }

    public boolean updateDrug(Drug drug) {
       /* int index = drugs.indexOf(drug);
        drugs.set(index, drug);*/
       try{
           DrugDao.update(drug);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }

    public void deleteDrugById(String id) {
        for (Iterator<Drug> iterator = drugs.iterator(); iterator.hasNext(); ) {
            Drug drug = iterator.next();
            if (drug.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    public boolean isDrugExist(Drug drug) {
        return findByName(drug.getName()) != null;
    }

    public void deleteAllDrugs() {
        drugs.clear();
    }

    private static List<Drug> populateDummyDrugs() {
        List<Drug> drugs = new ArrayList<Drug>();
        for (int i = 1; i <= 9 ; i++) {
            drugs.add(new Drug(i+"", "Drug"+i, i*100));
        }

        return drugs;
    }
}
