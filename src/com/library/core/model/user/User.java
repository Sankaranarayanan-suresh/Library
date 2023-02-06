package com.library.core.model.user;

public abstract class User {

    public final String id;
    private  String name;
    private final String phoneNumber;
    private String password;

    @Override
    public String toString() {
        return  "id        =  " + id + "\n" +
                "name        =  " + name + "\n" +
                "phoneNumber =  " + phoneNumber;
    }

    public User(String id, String name, String phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
