package services.implementation;

import models.UserModel;
import models.other.UserType;
import repository.IRepository;
import services.IUserService;
import utils.StringEncryptor;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private final IRepository<UserModel> userRepository;

    public UserServiceImpl(IRepository<UserModel> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean validateUserCredentials(String email, String password) {
        List<UserModel> allUsers = getAllUsers();
        if (allUsers == null)
            return false;
       // userRepository.setDestinationFileName("UserModel/validare.php");
        return allUsers.stream().anyMatch(u -> u.getEmail().equals(email) &&
                u.getPassword().equals(StringEncryptor.encrypt(email, password)));
    }

    @Override
    public UserModel getUser(int id) {
        List<UserModel> allUsers = getAllUsers();
        if (allUsers == null)
            return null;
        return allUsers.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public UserModel getArtist(String name) {
        List<UserModel> allUsers = getAllUsers();
        if (allUsers == null)
            return null;
        return allUsers.stream().filter(u -> u.getType() == UserType.Artist && u.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public UserModel getUser(String email) {
        List<UserModel> allUsers = getAllUsers();
        if (allUsers == null)
            return null;
        return allUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public void updateUser(UserModel model) {
        userRepository.update(model);
    }

    @Override
    public UserModel createUser(String email, String password, String userName, UserType userType) throws UserExistsException {

       // UserModel userModel = new UserModel(0, email, StringEncryptor.encrypt(email, password), userName, userType);
        UserModel userModel = new UserModel(0, email, password, userName, userType);
        userRepository.setDestinationFileName("UserModel/createUser.php");
        UserModel userCreated = userRepository.create(userModel);
        if (userCreated == null)
            throw new UserExistsException();
        return getUser(email);
    }

    @Override
    public boolean existsUser(int id) {
        List<UserModel> allUsers = getAllUsers();
        if (allUsers == null)
            return false;
        return allUsers.stream().anyMatch(u -> u.getId() == id);
    }

    @Override
    public boolean existsUser(String email) {
        List<UserModel> allUsers = getAllUsers();
        if (allUsers == null)
            return false;
        return allUsers.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public List<UserModel> getAllUsers() {
        userRepository.setDestinationFileName("UserModel/index.php");
        return userRepository.getAll();
    }
}
