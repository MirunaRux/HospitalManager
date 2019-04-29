package com.miruna.hospitalmanager.application.signUp;

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

public class UserRestCaller {
    private final static Logger logger = Logger.getLogger(UserRestCaller.class.getName());
    private final static String REST_SERVICE_URI = "http://192.168.0.14:8080/medicalService/api";

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

    public static User[] getAllUsers() {
        try {
            ResponseEntity<User[]> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/user/", User[].class);

            User users[] = response.getBody();

            return users;
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
            e.printStackTrace();
        }
        return new User[0];
    }

    public static User getUser(String username) {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("username", username);
            ResponseEntity<User> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/user/{username}", User.class, parameters);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return null;
    }

    public static User createUser(User newUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<User> userEntity = new HttpEntity<>(newUser, httpHeaders);
        ResponseEntity<User> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/user/", userEntity, User.class);
        return response.getBody();
    }

}
