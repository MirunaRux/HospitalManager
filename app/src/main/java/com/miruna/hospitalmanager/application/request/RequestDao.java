package com.miruna.hospitalmanager.application.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestDao {
    public static List<Request> findAllRequests() {
        List<Request> requests = new ArrayList<>();
        requests.addAll(Arrays.asList(RequestRestCaller.getAllRequests()));
        return requests;
    }

    public static Request create(Request request){ return RequestRestCaller.createRequest(request);}

    public static void delete(String id){ RequestRestCaller.deleteRequest(id);}
}
