package com.library.databse.repository;

import com.library.core.model.book.RentedBook;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.core.repository.user.UserDetailsManager;
import com.library.core.repository.user.UsersManager;
import com.library.databse.model.DatabaseFunctions;
import com.library.databse.model.RentedBooksHandler;

import java.util.ArrayList;
import java.util.Collection;

public class UserDataManager implements UserDetailsManager, UsersManager {
    private final DatabaseFunctions<User> database;
    private final RentedBooksHandler rentedBooksHandler;

    public UserDataManager(DatabaseFunctions<User> database, RentedBooksHandler rentedBooksHandler) {
        this.database = database;
        this.rentedBooksHandler = rentedBooksHandler;
    }

    @Override
    public Collection<User> getAllUsers() {
        return database.getAll();
    }

    @Override
    public User getUser(String phoneNumber) {
        return database.get(phoneNumber);
    }

    @Override
    public boolean checkUserCredentials(String phoneNumber, String password) {
        return database.get(phoneNumber).getPassword().equals(password);
    }

    @Override
    public boolean userExists(String phoneNumber) {
        for (User user : database.getAll()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Member> getRentedUsers() {
        Collection<Member> members = new ArrayList<>();
        for (RentedBook rentedBook : rentedBooksHandler.getAll()) {
            members.add(rentedBook.getMember());
        }
        return members;
    }

}
