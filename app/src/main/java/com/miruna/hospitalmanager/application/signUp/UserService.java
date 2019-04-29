package com.miruna.hospitalmanager.application.signUp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
    private static final AtomicInteger counter = new AtomicInteger();

    private static List<User> users;

    public List<User> findAllUsers() {

        users = UserDao.findAllUsers();

        return users;
    }

    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User createUser(User user) {
        //Users.add(User);
        users.add(UserDao.create(user));
        return user;
    }

    public boolean isUserExist(User user) {
        return findByUsername(user.getUsername()) != null;
    }

}
