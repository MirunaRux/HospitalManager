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
}
