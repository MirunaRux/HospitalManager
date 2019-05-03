package com.miruna.hospitalmanager.application.pacient.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FileService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<File> files;

    public List<File> findAllFiles() {

        files = FileDao.findAllFiles();

        return files;
    }
    public List<File> findAllPacientFiles(String pacientId) {

        List<File> filteredFiles = new ArrayList<>();
        for(File file: files){
            if(file.getPacient_id().equals(pacientId)){
                filteredFiles.add(file);
            }
        }

        return filteredFiles;
    }

    public File findById(String id) {
        for (File file : files) {
            if (file.getId().equals(id)) {
                return file;
            }
        }
        return null;
    }

    public File createFile(File file) {
        files.add(file);
        return file;
    }

    public void updateFile(File file) {
        int index = files.indexOf(file);
        files.set(index, file);
    }

    public void deleteFileById(String id) {
        for (Iterator<File> iterator = files.iterator(); iterator.hasNext(); ) {
            File file = iterator.next();
            if (file.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    public boolean isFileExist(File file) {
        return findById(file.getId()) != null;
    }

    public void deleteAllFiles() {
        files.clear();
    }

    private static List<File> populateDummyFiles() {
        List<File> files = new ArrayList<File>();
        for (int i = 1; i <= 9 ; i++) {
            files.add(new File(i + "", "File" + i, "Pacient" + i));
        }

        return files;
    }
}
