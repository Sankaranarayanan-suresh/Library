package com.library.core.model.user;

import com.library.core.model.book.Book;

import java.util.Collection;


public abstract class User {

    private String id;
    String name;
    String phoneNumber;
    String password;

    public User(String id, String name, String phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public abstract Collection<Book> getAllBooks();
}
