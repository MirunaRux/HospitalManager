package com.miruna.hospitalmanager.application.agenda;

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

public class EventRestCaller {
    private final static Logger logger = Logger.getLogger(EventRestCaller.class.getName());
    private final static String REST_SERVICE_URI = "http://192.168.0.103:8080/medicalService/api";

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

    public static Event[] getAllEvents() {
        try {
            ResponseEntity<Event[]> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/event/", Event[].class);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return new Event[0];
    }

    public static Event getEvent(String id) {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", id);
            ResponseEntity<Event> response = getRestTemplate().getForEntity(
                    REST_SERVICE_URI + "/event/{id}", Event.class, parameters);
            return response.getBody();
        } catch (Exception e) {
            logger.severe("Error calling medical service." + e);
        }
        return null;
    }

    public static Event createEvent(Event newEvent) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Event> eventEntity = new HttpEntity<>(newEvent, httpHeaders);
        ResponseEntity<Event> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/event/", eventEntity, Event.class);
       logger.info("check event rest caller");
        return response.getBody();
    }

    public static Event updateEvent(Event event) {
        if(event == null || event.getId() == null || event.getId().isEmpty()) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", event.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        HttpEntity<Event> eventEntity = new HttpEntity<>(event, httpHeaders);
        ResponseEntity<Event> response = getRestTemplate().postForEntity(REST_SERVICE_URI + "/event/{id}", eventEntity, Event.class, parameters);
        return response.getBody();
    }

    public static void deleteEvent(String id) {
        if(id == null || id.isEmpty()) {
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        getRestTemplate().delete(REST_SERVICE_URI + "/event/{id}", parameters);
    }
}
