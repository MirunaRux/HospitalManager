package com.miruna.hospitalmanager.application.drug;

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

public class DrugRestCaller {
    private final static Logger logger = Logger.getLogger(DrugRestCaller.class.getName());
    private final static String REST_SERVICE_URI = "http://192.168.0.14:8080/medicalService/api";

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

    public static Drug[] getAllDrugs() {
        try {
            ResponseEntity<Drug[]> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/drug/", Drug[].class);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return new Drug[0];
    }

    public static Drug getDrug(String id) {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", id);
            ResponseEntity<Drug> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/drug/{id}", Drug.class, parameters);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return null;
    }

    public static Drug createDrug(Drug newDrug) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Drug> drugEntity = new HttpEntity<>(newDrug, httpHeaders);
        ResponseEntity<Drug> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/drug/", drugEntity, Drug.class);
        return response.getBody();
    }

    public static Drug updateDrug(Drug drug) {
        if(drug == null || drug.getId() == null || drug.getId().isEmpty()) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", drug.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Drug> drugEntity = new HttpEntity<>(drug, httpHeaders);
        ResponseEntity<Drug> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/drug/{id}", drugEntity, Drug.class, parameters);
        return response.getBody();
    }

    public static void deleteDrug(String id) {
        if(id == null || id.isEmpty()) {
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        getRestTemplate().delete(REST_SERVICE_URI + "/drug/{id}", parameters);
    }
}
