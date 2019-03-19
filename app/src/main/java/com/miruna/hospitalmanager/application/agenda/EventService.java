package com.miruna.hospitalmanager.application.agenda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EventService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<Event> events;

    static {
        events = populateDummyEvents();
    }

    public List<Event> findAllEvents() {
        return events;
    }

    public Event findById(int id) {
        for (Event event : events) {
            if (event.getId()== id) {
                return event;
            }
        }
        return null;
    }

    public Event findByName(String name) {
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(name)) {
                return event;
            }
        }
        return null;
    }

    public Event createEvent(Event event) {
        events.add(event);
        return event;
    }

    public void updateEvent(Event event) {
        int index = events.indexOf(event);
        events.set(index, event);
    }

    public void deleteEventById(int id) {
        for (Iterator<Event> iterator = events.iterator(); iterator.hasNext(); ) {
            Event event = iterator.next();
            if (event.getId() == id) {
                iterator.remove();
            }
        }
    }

    public boolean isEventExist(Event event) {
        return findByName(event.getName()) != null;
    }

    public void deleteAllEvents() {
        events.clear();
    }

    private static List<Event> populateDummyEvents() {
        List<Event> events = new ArrayList<Event>();
        for (int i = 1; i <= 9 ; i++) {
            events.add(new Event(i, "Eveniment" + i, "Locatie" + i, "Pacient" + i, "Doctor"+i));
        }

        return events;
    }

}
