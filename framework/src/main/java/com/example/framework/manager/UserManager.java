package com.example.framework.manager;

public class UserManager {

    private static UserManager userManager;

    public static UserManager getInstance() {
        if (userManager == null) {
            return userManager = new UserManager();
        }
        return userManager;
    }

    public void savaUser() {

    }
}
