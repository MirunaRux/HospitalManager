package com.miruna.hospitalmanager.application.pacient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PacientService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<Pacient> pacients;

    public List<Pacient> findAllPacients() {

        pacients = PacientDao.findAllPacients();

        return pacients;
    }

    public Pacient findById(String id) {
        for (Pacient pacient : pacients) {
            if (pacient.getId().equals(id)) {
                return pacient;
            }
        }
        return null;
    }

    public List<Pacient> findByName(List<Pacient> pacientsAux, String name) {
        List<Pacient> result = new ArrayList<>();
        for (Pacient pacient : pacientsAux) {
            if (pacient.getName().equalsIgnoreCase(name)) {
                result.add(pacient);
            }
        }
        return result;
    }

    public Pacient createPacient(Pacient pacient) {
        //pacients.add(pacient);
        pacients.add(PacientDao.create(pacient));
        return pacient;
    }

    public Boolean updatePacient(Pacient pacient) {
        try{
            PacientDao.update(pacient);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void deletePacientById(String id) {
        for (Iterator<Pacient> iterator = pacients.iterator(); iterator.hasNext(); ) {
            Pacient pacient = iterator.next();
            if (pacient.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    public boolean isPacientExist(Pacient pacient) {
        return findByName(pacients, pacient.getName()) != null;
    }

    public void deleteAllPacients() {
        pacients.clear();
    }

    private static List<Pacient> populateDummyPacients() {
        List<Pacient> pacients = new ArrayList<Pacient>();
        for (int i = 1; i <= 9 ; i++) {
            pacients.add(new Pacient(Integer.toString(i), "Pacient" + i, "Pacient" + i, "" + i,
                    "2971209152479", "0" + i + ".0" + i + ".2018", "0" + i + ".0" + i + ".2018"));
        }

        return pacients;
    }

}
