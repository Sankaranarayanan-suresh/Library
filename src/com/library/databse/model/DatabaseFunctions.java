package com.library.databse.model;

import java.util.Collection;

public interface DatabaseFunctions<T> {

    Collection<T> getAll();

    void set(T data);

    T get(String key);

    void update(T data);

    void remove(String key);

}