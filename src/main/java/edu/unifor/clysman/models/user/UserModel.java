package edu.unifor.clysman.models.user;

import edu.unifor.clysman.models.Model;

public class UserModel implements Model {
    private int id;
    private String name;
    private String email;
    private String password;
    private UserType type;

    public UserModel() {
    }

    public UserModel(int id, String name, String email, String password, UserType type) {
        this(name, email, password, type);
        this.id = id;
    }

    public UserModel(String name, String email, String password, UserType type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public static UserModel builder() {
        return new UserModel();
    }

    public boolean isAdmin() {
        return this.type == UserType.ADMIN;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public UserModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserType getType() {
        return type;
    }

    public UserModel setType(UserType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
