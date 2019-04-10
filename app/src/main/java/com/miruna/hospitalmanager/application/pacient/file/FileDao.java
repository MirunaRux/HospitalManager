package com.miruna.hospitalmanager.application.pacient.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDao {
    public static List<File> findAllFiles() {
        List<File> files = new ArrayList<>();
        files.addAll(Arrays.asList(FileRestCaller.getAllFiles()));
        return files;
    }
}
