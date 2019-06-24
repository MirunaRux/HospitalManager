package com.miruna.hospitalmanager.application.pacient;

import com.miruna.hospitalmanager.application.crypto.CryptoFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.crypto.IllegalBlockSizeException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PacientRestCaller {
    private final static Logger logger = Logger.getLogger(PacientRestCaller.class.getName());
    private final static String REST_SERVICE_URI = "http://192.168.1.6:8080/medicalService/api";

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

            if (pacients != null) {
                for (Pacient p : pacients) {
                    try {
                        logger.info("From server pacient name: " + p.getName());
                        p = decryptPacientData(p);
                        logger.info("Decrypted pacient name: " + p.getName());
                    } catch (IllegalBlockSizeException e) {
                        // pass, we do nothing
                    } catch (Exception e2) {
                        throw e2;
                    }
                }
            }

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

    public static Pacient createPacient(Pacient newPacient) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        logger.info(newPacient.getName());
        newPacient = encryptPacientData(newPacient);
        logger.info(newPacient.getName());
        HttpEntity<Pacient> pacientEntity = new HttpEntity<>(newPacient, httpHeaders);
        ResponseEntity<Pacient> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/pacient/", pacientEntity, Pacient.class);
        return response.getBody();
    }

    public static Pacient updatePacient(Pacient pacient) throws Exception {
        if(pacient == null || pacient.getId() == null || pacient.getId().isEmpty()) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", pacient.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        pacient = encryptPacientData(pacient);
        HttpEntity<Pacient> pacientEntity = new HttpEntity<>(pacient, httpHeaders);
        ResponseEntity<Pacient> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/pacient/{id}", pacientEntity, Pacient.class, parameters);
        return response.getBody();
    }

    public static Pacient encryptPacientData(Pacient pacient) throws Exception {
        pacient.setBirthday(CryptoFactory.encrypt(pacient.getBirthday()));
        pacient.setCnp(CryptoFactory.encrypt(pacient.getCnp()));
        pacient.setDateEx(CryptoFactory.encrypt(pacient.getDateEx()));
        pacient.setDateIn(CryptoFactory.encrypt(pacient.getDateIn()));
        pacient.setName(CryptoFactory.encrypt(pacient.getName()));
        pacient.setSurname(CryptoFactory.encrypt(pacient.getSurname()));
        return pacient;
    }

    public static Pacient decryptPacientData(Pacient pacient) throws Exception {
        pacient.setBirthday(CryptoFactory.decrypt(pacient.getBirthday()));
        pacient.setCnp(CryptoFactory.decrypt(pacient.getCnp()));
        pacient.setDateEx(CryptoFactory.decrypt(pacient.getDateEx()));
        pacient.setDateIn(CryptoFactory.decrypt(pacient.getDateIn()));
        pacient.setName(CryptoFactory.decrypt(pacient.getName()));
        pacient.setSurname(CryptoFactory.decrypt(pacient.getSurname()));
        return pacient;
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
