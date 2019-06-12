package com.miruna.hospitalmanager.application.pacient.file;

import android.util.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class FileRestCaller {
    private final static Logger logger = Logger.getLogger(FileRestCaller.class.getName());
            private final static String REST_SERVICE_URI = "http://192.168.0.103:8080/medicalService/api";

            public static RestTemplate getRestTemplate() {
                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setSupportedMediaTypes(
                        Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
                restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
                return restTemplate;
            }

            public static File[] getAllFiles() {
                try {
                    ResponseEntity<File[]> response = getRestTemplate().getForEntity(
                            REST_SERVICE_URI + "/file/", File[].class);
                    File file[] = response.getBody();
                    File[] resultList = new File[file.length];
                    for (int i = 0; i < resultList.length; i++) {
                        File f = file[i];
                        resultList[i] = new File(f.getId(), f.getContent(), f.getPacient_id());
                    }
                    return resultList;
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return new File[0];
    }

    public static File getFile(String id) {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", id);
            ResponseEntity<File> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/file/{id}", File.class, parameters);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return null;
    }

    public static File createFile(File newFile) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<File> fileEntity = new HttpEntity<>(newFile, httpHeaders);
        ResponseEntity<File> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/file/", fileEntity, File.class);
        return response.getBody();
    }

    public static File updateFile(File file) {
        if(file == null || file.getId() == null || file.getId().isEmpty()) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", file.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<File> fileEntity = new HttpEntity<>(file, httpHeaders);
        ResponseEntity<File> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/file/{id}", fileEntity, File.class, parameters);
        return response.getBody();
    }

    public static void deleteFile(String id) {
        if(id == null || id.isEmpty()) {
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        getRestTemplate().delete(REST_SERVICE_URI + "/file/{id}", parameters);
    }
}
