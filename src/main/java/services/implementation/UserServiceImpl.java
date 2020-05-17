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
        List<UserModel> users = getAllUsers();

        if (users == null) {
            users = new ArrayList<>();
        }

        int biggestId = -1;
        for (UserModel user : users) {
            if (user.getEmail().equals(email)) {
                throw new UserExistsException();
            }
            if (user.getId() > biggestId) {
                biggestId = user.getId();
            }
        }
        UserModel userModel = new UserModel(biggestId + 1, email, StringEncryptor.encrypt(email, password), userName, userType);

        userRepository.create(userModel);

        return userModel;
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
        return userRepository.getAll();
    }
}
