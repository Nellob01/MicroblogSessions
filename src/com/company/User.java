package com.company;

import java.util.ArrayList;

/**
 * Created by noelaniekan on 12/6/16.
 */
public class User {
    String name;
    ArrayList<Message> user1 = new ArrayList<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;

    }

    String password;

}
