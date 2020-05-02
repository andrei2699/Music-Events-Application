package main;

import models.UserModel;

public class LoggedUserData {
    private static final LoggedUserData instance = new LoggedUserData();
    private UserModel userModel;

    public static LoggedUserData getInstance() {
        return instance;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public boolean isUserLogged() {
        return userModel != null;
    }
}
