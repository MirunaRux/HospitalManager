package com.miruna.hospitalmanager.application.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<Request> requests;

    static {
        requests = populateDummyRequests();
    }

    public List<Request> findAllRequests() {
        return requests;
    }

    public Request findById(String id) {
        for (Request request : requests) {
            if (request.getId().equals(id)) {
                return request;
            }
        }
        return null;
    }

    public Request createRequest(Request request) {
        requests.add(request);
        return request;
    }

    public void updateRequest(Request request) {
        int index = requests.indexOf(request);
        requests.set(index, request);
    }

    public void deleteRequestById(String id) {
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request request = iterator.next();
            if (request.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    public boolean isRequestExist(Request request) {
        return findById(request.getId()) != null;
    }

    public void deleteAllRequests() {
        requests.clear();
    }

    private static List<Request> populateDummyRequests() {
        List<Request> requests = new ArrayList<Request>();
        for (int i = 1; i <= 9 ; i++) {
            requests.add(new Request(i + "", "Drug" + i, i));
        }

        return requests;
    }

}
