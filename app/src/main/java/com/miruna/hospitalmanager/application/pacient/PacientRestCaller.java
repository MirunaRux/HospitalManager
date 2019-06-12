package com.miruna.hospitalmanager.application.pacient;

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

public class PacientRestCaller {
    private final static Logger logger = Logger.getLogger(PacientRestCaller.class.getName());
    private final static String REST_SERVICE_URI = "http://192.168.0.103" +
            ":8080/medicalService/api";

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

    public static Pacient[] getAllPacients() {
        try {
            ResponseEntity<Pacient[]> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/pacient/", Pacient[].class);

            Pacient pacients[] = response.getBody();

            return pacients;
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
           e.printStackTrace();
        }
        return new Pacient[0];
    }

    public static Pacient getPacient(String id) {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", id);
            ResponseEntity<Pacient> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/pacient/{id}", Pacient.class, parameters);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return null;
    }

    public static Pacient createPacient(Pacient newPacient) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Pacient> pacientEntity = new HttpEntity<>(newPacient, httpHeaders);
        ResponseEntity<Pacient> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/pacient/", pacientEntity, Pacient.class);
        return response.getBody();
    }

    public static Pacient updatePacient(Pacient pacient) {
        if(pacient == null || pacient.getId() == null || pacient.getId().isEmpty()) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", pacient.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Pacient> pacientEntity = new HttpEntity<>(pacient, httpHeaders);
        ResponseEntity<Pacient> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/pacient/{id}", pacientEntity, Pacient.class, parameters);
        return response.getBody();
    }
/*
    public static void deletePacient(String id) {
        if(id == null || id.isEmpty()) {
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        getRestTemplate().delete(REST_SERVICE_URI + "/pacient/{id}", parameters);
    }*/
}
