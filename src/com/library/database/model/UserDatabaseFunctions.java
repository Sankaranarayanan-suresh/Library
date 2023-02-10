package com.library.database.model;

import com.library.core.model.book.Book;

import java.util.Collection;

public interface UserDatabaseFunctions<T> {

    Collection<T> getAll();

    void set(T data);

    T get(String key);

    void update(T data);

    void remove(String key);

}