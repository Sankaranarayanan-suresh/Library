package com.library.database.model;

import com.library.core.model.user.User;

import java.util.Collection;
import java.util.HashMap;

public class UsersHandler implements DatabaseFunctions<User> {

    private static UsersHandler databaseInstance = null;

    public static UsersHandler getInstance(){
        if (databaseInstance == null)
            databaseInstance = new UsersHandler();
        return databaseInstance;
    }

    private UsersHandler() {
    }

    private final HashMap<String, User> users = new HashMap<>();
    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public void set(User data) {
        users.put(data.getPhoneNumber(),data);
    }

    @Override
    public User get(String key) {
        return users.get(key);
    }

    @Override
    public void update(User data) {
        if (!users.containsKey(data.id))
            throw new RuntimeException("Data you are trying to Update does not exist!");
        users.put(data.id,data);
    }

    @Override
    public void remove(String key) {
        if (!users.containsKey(key))
            throw new RuntimeException("No such User Exist!");
        users.remove(key);
    }
}
