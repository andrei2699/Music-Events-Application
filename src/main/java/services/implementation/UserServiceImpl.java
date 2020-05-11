package services.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.UserModel;
import models.other.UserType;
import services.FileSystemManager;
import services.ServiceProvider;
import services.UserService;
import utils.StringEncryptor;

import java.nio.file.Path;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public boolean validateUserCredentials(String email, String password) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(u -> u.getEmail().equals(email) &&
                u.getPassword().equals(StringEncryptor.encrypt(email, password)));
    }

    @Override
    public UserModel getUser(int id) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public UserModel getArtist(String name) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().filter(u -> u.getType() == UserType.Artist && u.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public UserModel getUser(String email) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public void updateUser(UserModel model) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path usersFilePath = fileSystemManager.getUsersFilePath();
        List<UserModel> users = getAllUsers();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (UserModel user : users) {
            if (user.getEmail().equals(model.getEmail())) {
                user.setName(model.getName());
                break;
            }
        }

        String json = gson.toJson(users);
        fileSystemManager.writeToFile(usersFilePath, json);
    }

    @Override
    public UserModel createUser(String email, String password, String userName, UserType userType) throws UserExistsException {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path usersFilePath = fileSystemManager.getUsersFilePath();
        List<UserModel> users = getAllUsers();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

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

        users.add(userModel);

        String json = gson.toJson(users);
        fileSystemManager.writeToFile(usersFilePath, json);

        return userModel;
    }

    @Override
    public boolean existsUser(int id) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(u -> u.getId() == id);
    }

    @Override
    public boolean existsUser(String email) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public List<UserModel> getAllUsers() {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path usersFilePath = fileSystemManager.getUsersFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(usersFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<UserModel>>() {
        }.getType());
    }

}
