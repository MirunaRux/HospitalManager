package com.miruna.hospitalmanager.application.signUp;

import android.util.Log;
import com.miruna.hospitalmanager.application.crypto.CryptoFactory;
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
    private final static String REST_SERVICE_URI = "http://192.168.1.6:8080/medicalService/api";

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

    public static User[] getAllUsers() throws Exception {
        try {
            ResponseEntity<User[]> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/user/", User[].class);

            User users[] = response.getBody();

            if (users != null) {
                for(User u : users) {
                    u = decryptUserData(u);
                }
            }

            return users;
        } catch (Exception e) {
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

    public static User createUser(User newUser) throws Exception {
        logger.info(newUser.getUsername());
        logger.info(newUser.getPassword());
        logger.info(newUser.getRole());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        newUser = encryptUserData(newUser);
        HttpEntity<User> userEntity = new HttpEntity<>(newUser, httpHeaders);
        logger.info("User entity stuff: " + userEntity.getBody().getUsername());
        ResponseEntity<User> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/user/", userEntity, User.class);
        logger.info("Aoleo gucci");
        logger.info("Response: " + response.getStatusCode());
        logger.info("Response body: " + response.getBody().getUsername());
        return response.getBody();
    }

    public static User encryptUserData(User user) throws Exception {
        user.setUsername(CryptoFactory.encrypt(user.getUsername()));
        user.setPassword(CryptoFactory.encrypt(user.getPassword()));
        user.setRole(CryptoFactory.encrypt(user.getRole()));
        return user;
    }

    public static User decryptUserData(User user) throws Exception {
        user.setUsername(CryptoFactory.decrypt(user.getUsername()));
        user.setPassword(CryptoFactory.decrypt(user.getPassword()));
        user.setRole(CryptoFactory.decrypt(user.getRole()));
        return user;
    }

}


