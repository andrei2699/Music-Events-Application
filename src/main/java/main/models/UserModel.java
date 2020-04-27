package main.models;

public class UserModel implements IConvertToJson {

    private int id;
    private String email;
    private String password;
    private String name;
    private UserType type;

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

    @Override
    public String toJSON() {
        return null;
    }
}
