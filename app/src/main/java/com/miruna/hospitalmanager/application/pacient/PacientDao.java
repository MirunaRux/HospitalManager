package com.miruna.hospitalmanager.application.pacient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacientDao {

    public static List<Pacient> findAllPacients() {
        List<Pacient> pacients = new ArrayList<>();
        pacients.addAll(Arrays.asList(PacientRestCaller.getAllPacients()));
        return pacients;
    }

    public static Pacient create(Pacient pacient) throws Exception{
        System.out.println("Dao create pacient");
        return PacientRestCaller.createPacient(pacient);
    }

    public static Pacient update(Pacient pacient) throws  Exception {
        return PacientRestCaller.updatePacient(pacient);
    }
}
