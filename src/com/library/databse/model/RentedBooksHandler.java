package com.library.databse.model;

import com.library.core.model.book.RentedBook;

import java.util.Collection;
import java.util.HashMap;

public class RentedBooksHandler implements DatabaseFunctions<RentedBook> {

    private static RentedBooksHandler databaseInstance = null;
    private final HashMap<String, RentedBook> rentedBooks = new HashMap<>();

    private RentedBooksHandler() {

    }

    public static RentedBooksHandler getInstance() {
        if (databaseInstance == null)
            databaseInstance = new RentedBooksHandler();
        return databaseInstance;
    }

    @Override
    public Collection<RentedBook> getAll() {
        return rentedBooks.values();
    }

    @Override
    public void set(RentedBook data) {
        rentedBooks.put(data.id, data);
    }

    @Override
    public RentedBook get(String key) {
        if (!rentedBooks.containsKey(key))
            throw new RuntimeException("Cannot find that book in library");
        return rentedBooks.get(key);
    }

    @Override
    public void update(RentedBook data) {
        if (!rentedBooks.containsKey(data.id))
            throw new RuntimeException("Cannot find that book in library");
        rentedBooks.put(data.id, data);
    }

    @Override
    public void remove(String key) {
        if (!rentedBooks.containsKey(key))
            throw new RuntimeException("Cannot find that book in library");
        rentedBooks.remove(key);
    }
}
