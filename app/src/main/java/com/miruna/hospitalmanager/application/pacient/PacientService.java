package com.miruna.hospitalmanager.application.pacient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PacientService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<Pacient> pacients;

    static {
        pacients = populateDummyPacients();
    }

    public List<Pacient> findAllPacients() {
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

    public Pacient findByName(String name) {
        for (Pacient pacient : pacients) {
            if (pacient.getName().equalsIgnoreCase(name)) {
                return pacient;
            }
        }
        return null;
    }

    public Pacient createPacient(Pacient pacient) {
        pacients.add(pacient);
        return pacient;
    }

    public void updatePacient(Pacient pacient) {
        int index = pacients.indexOf(pacient);
        pacients.set(index, pacient);
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
        return findByName(pacient.getName()) != null;
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