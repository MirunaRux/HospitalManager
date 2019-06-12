package com.miruna.hospitalmanager.application.request;

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

public class RequestRestCaller {
    private final static Logger logger = Logger.getLogger(RequestRestCaller.class.getName());
    private final static String REST_SERVICE_URI = "http://192.168.0.103:8080/medicalService/api";

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

    public static Request[] getAllRequests() {
        try {
            ResponseEntity<Request[]> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/request/", Request[].class);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return new Request[0];
    }

   /* public static Request getRequest(String id) {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", id);
            ResponseEntity<Request> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/Request/{id}", Request.class, parameters);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return null;
    }
*/
    public static Request createRequest(Request newRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Request> requestEntity = new HttpEntity<>(newRequest, httpHeaders);
        ResponseEntity<Request> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/request/", requestEntity, Request.class);
        return response.getBody();
    }

    /*public static Request updateRequest(Request request) {
        if(request == null || request.getId() == null || request.getId().isEmpty()) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", request.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Request> requestEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<Request> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/request/{id}", requestEntity, Request.class, parameters);
        return response.getBody();
    }
*/
    public static void deleteRequest(String id) {
        if(id == null || id.isEmpty()) {
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        getRestTemplate().delete(REST_SERVICE_URI + "/request/{id}", parameters);
    }
}
