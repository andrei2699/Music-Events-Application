package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.models.UserModel;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {

    UserService() {
    }

    public boolean validateUserCredentials(String email, String password) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(u -> u.getEmail().equals(email) && u.getPassword().equals(encryptString(email, password)));
    }

    public UserModel getUser(int id) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public UserModel getUser(String email) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void updateUserName(UserModel model, String newName) {
        FileSystemManager fileSystemManager = ServiceInjector.getInstance().getFileSystemManager();
        Path usersFilePath = fileSystemManager.getUsersFilePath();
        List<UserModel> users = getAllUsers();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (UserModel user : users) {
            if (user.getEmail().equals(model.getEmail())) {
                user.setName(newName);
                break;
            }
        }

        String json = gson.toJson(users);
        fileSystemManager.writeToFile(usersFilePath, json);
    }

    public void createUser(UserModel userModel) throws UserExistsException {
        FileSystemManager fileSystemManager = ServiceInjector.getInstance().getFileSystemManager();
        Path usersFilePath = fileSystemManager.getUsersFilePath();
        List<UserModel> users = getAllUsers();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        int biggestId = -1;
        for (UserModel user : users) {
            if (user.getEmail().equals(userModel.getEmail())) {
                throw new UserExistsException();
            }
            if (user.getId() > biggestId) {
                biggestId = user.getId();
            }
        }

        userModel.setPassword(encryptString(userModel.getEmail(), userModel.getPassword()));
        userModel.setId(biggestId + 1);

        users.add(userModel);

        String json = gson.toJson(users);
        fileSystemManager.writeToFile(usersFilePath, json);
    }

    public boolean existsUser(int id) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(u -> u.getId() == id);
    }

    public boolean existsUser(String email) {
        List<UserModel> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public List<UserModel> getAllUsers() {
        FileSystemManager fileSystemManager = ServiceInjector.getInstance().getFileSystemManager();
        Path usersFilePath = fileSystemManager.getUsersFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(usersFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<UserModel>>() {
        }.getType());
    }

    private String encryptString(String salt, String value) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(value.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8).replace("\"", "");
    }

    private MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}