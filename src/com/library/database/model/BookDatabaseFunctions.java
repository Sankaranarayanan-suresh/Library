package com.library.database.model;

import com.library.core.model.book.Book;

import java.util.Collection;

public interface BookDatabaseFunctions<T>{
    Collection<T> getAll();

    void set(T data);

    T get(String key);

    void update(T data);

    void remove(String key);
    Collection<Book> getRentedBooks(String phoneNumber);

    Collection<Book> getAllRentedBooks();

}
