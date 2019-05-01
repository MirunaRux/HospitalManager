package com.miruna.hospitalmanager.application.agenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventDao {
    public static List<Event> findAllEvents() {
        List<Event> events = new ArrayList<>();
        events.addAll(Arrays.asList(EventRestCaller.getAllEvents()));
        return events;
    }

    public static Event create(Event event){
        return EventRestCaller.createEvent(event);
    }

    public static void delete(String id){
        EventRestCaller.deleteEvent(id);
    }
}
