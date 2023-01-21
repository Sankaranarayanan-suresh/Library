package com.library.core.model.user;

import com.library.core.model.book.Book;

import java.util.Collection;


public abstract class User {

    public final String id;
    private  String name;
    private final String phoneNumber;
    private String password;

    public User(String id, String name, String phoneNumber, String password) {
        if(!phoneNumber.matches("[6-9]{1}[0-9]{9}")) {
            throw new RuntimeException("Phone number is not valid");
        }
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
    public abstract Collection<Book> getAllBooks();
}
