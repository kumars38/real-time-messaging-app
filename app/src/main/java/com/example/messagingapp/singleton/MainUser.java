package com.example.messagingapp.singleton;

import com.example.messagingapp.models.User;

public class MainUser {
    private static MainUser instance = null;
    private static User userData;
    protected MainUser() {}
    public static MainUser getInstance() {
        if (instance == null) {
            instance = new MainUser();
        }
        return instance;
    }

    public static void setUserData(User u) {
        userData = u;
    }

    public static User getUserData() {
        return userData;
    }
}
