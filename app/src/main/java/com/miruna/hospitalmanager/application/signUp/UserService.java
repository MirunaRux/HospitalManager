package com.miruna.hospitalmanager.application.signUp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<User> users;

    public List<User> findAllUsers() throws Exception{

        Log.i("gigel", "cucu");
        users = UserDao.findAllUsers();

        return users;
    }

    public boolean findByUsername(String username, String password) throws Exception {
        users = findAllUsers();
        Log.i("gigel", username);
        Log.i("gigel", password);
        Log.i("gigel", "" + users.size());
        for (User user : users) {
            Log.i("gigel", "U:" + user.getUsername());
            Log.i("gigel", "P:" + user.getPassword());
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    Log.i("gigel", "parola ok");
                    return true;
                }
            }
        }
        return false;
    }

    public User createUser(User user) throws Exception {
        users.add(UserDao.create(user));
        return user;
    }

    public boolean isUserExist(User user) throws Exception{

        return findByUsername(user.getUsername(), user.getPassword());
    }

}
