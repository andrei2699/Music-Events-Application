package services.interfaces;

import models.UserModel;
import models.UserType;
import services.exceptions.UserExistsException;

import java.util.List;

public interface UserService {
    boolean validateUserCredentials(String email, String password);

    UserModel getUser(int id);

    UserModel getUser(String email);

    void updateUser(UserModel model);

    UserModel createUser(String email, String password, String userName, UserType userType) throws UserExistsException;

    boolean existsUser(int id);

    boolean existsUser(String email);

    List<UserModel> getAllUsers();
}
