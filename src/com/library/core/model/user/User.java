package com.library.core.model.user;

import com.library.core.model.book.Book;

import java.util.Collection;


public abstract class User {

    public final String id;
    private final String name;
    private final String phoneNumber;
    private final String password;

    public User(String id, String name, String phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
    public abstract Collection<Book> getAllBooks();
}
