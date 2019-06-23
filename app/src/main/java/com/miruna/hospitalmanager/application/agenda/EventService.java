package com.miruna.hospitalmanager.application.agenda;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EventService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<Event> events;

    public List<Event> findAllEvents() {

        events = EventDao.findAllEvents();

        return events;
    }

    public Event findById(String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public List<Event> findByDate(List<Event> eventsAux, String date) {
        List<Event> aux = new ArrayList<>();
        for (Event event : events) {
            if (event.getStartDate().equalsIgnoreCase(date)) {
                aux.add(event);
            }
        }
        return aux;
    }

    public List<Event> findByDoctorUsername(List<Event> eventsAux, String username){
        List<Event> aux = new ArrayList<>();
        for(Event event: events){
            if(event.getDoctorUsername().equals(username)){
                aux.add(event);
            }
        }
        return aux;
    }

    public Event createEvent(Event event) {
        //events.add(event);
        events.add(EventDao.create(event));
        return event;
    }

    public void updateEvent(Event event) {
        int index = events.indexOf(event);
        events.set(index, event);
    }

    public boolean deleteEventById(String id) {
        try{
            EventDao.delete(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean isEventExist(Event event) {
        return findById(event.getId()) != null;
    }

    public void deleteAllEvents() {
        events.clear();
    }

    private static List<Event> populateDummyEvents() {
        List<Event> events = new ArrayList<Event>();
        for (int i = 1; i <= 9; i++) {
            events.add(new Event(i + "", "Eveniment" + i, "Locatie" + i, "Start Date" + i, "Start Time" + i,
                    "Pacient" + i, "Doctor" + i));
        }

        return events;
    }

}
