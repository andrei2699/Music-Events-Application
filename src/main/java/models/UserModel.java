package models;

import models.other.UserType;

public class UserModel {

    private int id;
    private String email;
    private String password;
    private String name; //for a solo artist it is his/her name, for a band it is band's name | or bar name | or user name
    private UserType type;

    public UserModel(int id, String email, String password, String name, UserType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserModel [" + "id=" + id + ", email=" + email + ", name=" + name + ", type=" + type + ']';
    }
}
