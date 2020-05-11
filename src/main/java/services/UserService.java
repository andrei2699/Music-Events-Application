package services;

import models.UserModel;
import models.other.UserType;
import services.implementation.UserExistsException;

import java.util.List;

public interface UserService {
    boolean validateUserCredentials(String email, String password);

    UserModel getUser(int id);

    UserModel getArtist(String name);

    UserModel getUser(String email);

    void updateUser(UserModel model);

    UserModel createUser(String email, String password, String userName, UserType userType) throws UserExistsException;

    boolean existsUser(int id);

    boolean existsUser(String email);

    List<UserModel> getAllUsers();
}
