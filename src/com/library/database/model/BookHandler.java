package com.library.database.model;

import com.library.core.model.book.Book;

import java.util.Collection;
import java.util.HashMap;


public class BookHandler implements DatabaseFunctions<Book> {

    private static BookHandler databaseInstance = null;
    private final HashMap<String, Book> books = new HashMap<>();

    private BookHandler() {
    }

    public static BookHandler getInstance() {
        if (databaseInstance == null)
            databaseInstance = new BookHandler();
        return databaseInstance;
    }

    @Override
    public Collection<Book> getAll() {
        return books.values();
    }

    @Override
    public void set(Book data) {
        books.put(data.id, data);
    }

    @Override
    public Book get(String key) {
        if (!books.containsKey(key))
            throw new RuntimeException("Cannot find that book in library");
        return books.get(key);
    }

    @Override
    public void update(Book data) {
        if (!books.containsKey(data.id))
            throw new RuntimeException("Cannot find that book in library");
        books.put(data.id, data);
    }

    @Override
    public void remove(String key) {
        if (!books.containsKey(key))
            throw new RuntimeException("Cannot find that book in library");
        books.remove(key);
    }
}

