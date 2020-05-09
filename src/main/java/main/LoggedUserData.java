package main;

import models.UserModel;
import models.UserType;

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

    public boolean isRegularUser() {
        return isUserLogged() && userModel.getType() == UserType.RegularUser;
    }

    public boolean isBarManager() {
        return isUserLogged() && userModel.getType() == UserType.Manager;
    }

    public boolean isArtist() {
        return isUserLogged() && userModel.getType() == UserType.Artist;
    }

    public boolean isUserLogged() {
        return userModel != null;
    }
}
