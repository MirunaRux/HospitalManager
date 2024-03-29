package com.miruna.hospitalmanager.application.signUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {

    public static List<User> findAllUsers() throws Exception{
        List<User> users = new ArrayList<>();
        users.addAll(Arrays.asList(UserRestCaller.getAllUsers()));
        return users;
    }

    public static User create(User user) throws Exception{
        return UserRestCaller.createUser(user);
    }
}
