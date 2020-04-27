package services;

import main.models.UserModel;

public class UserService {
    protected UserService() {
    }

    public UserModel getUser(int id) {
        return null;
    }

    public UserModel getUser(String email) {
        return null;
    }

    public void createUser(UserModel userModel) {

    }

    public boolean existsUser(int id) {
        return false;
    }

    public boolean existsUser(String email) {
        return false;
    }
}
