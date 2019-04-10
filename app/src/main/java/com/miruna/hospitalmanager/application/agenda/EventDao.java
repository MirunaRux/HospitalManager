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
}
