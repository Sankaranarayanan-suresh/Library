package com.library.database.repository;

import com.library.core.model.book.Book;
import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.core.repository.library.LibraryManager;
import com.library.core.repository.user.UserAccountManager;
import com.library.core.repository.user.UserDetailsManager;
import com.library.core.repository.user.UsersManager;
import com.library.database.ManagerProvider;
import com.library.database.model.DatabaseFunctions;
import com.library.database.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;

public class UserDataManager implements UserDetailsManager, UsersManager, UserAccountManager {
    private final DatabaseFunctions<User> handler;
    private final BooksDataManager manager;
    private final LibraryManager libraryManager;

    public UserDataManager(DatabaseFunctions<User> database, BooksDataManager manager, LibraryManager libraryManager) {
        this.handler = database;
        this.manager = manager;
        this.libraryManager = libraryManager;
    }

    @Override
    public Collection<User> getAllUsers() {
        return handler.getAll();
    }

    public Member addMember(String name, String phoneNumber, String password) {
        Member member = new Member(Utils.generateID("Member"), name, phoneNumber, password, manager, this);
        handler.set(member);
        return member;
    }

    public Librarian addLibrarian(String name, String phoneNumber, String password) {
        Librarian librarian = new Librarian(Utils.generateID("librarian"), name, phoneNumber, password, this, manager, libraryManager);
        handler.set(librarian);
        return librarian;
    }

    public void removeMember(String id) {
        handler.remove(id);
    }

    @Override
    public User getUser(String phoneNumber) {
        return handler.get(phoneNumber);
    }

    @Override
    public boolean checkUserCredentials(String phoneNumber, String password) {
        return handler.get(phoneNumber).getPassword().equals(password);
    }

    @Override
    public boolean userExists(String phoneNumber) {
        for (User user : handler.getAll()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<User> getRentedUsers() {
        Collection<User> members = new ArrayList<>();
        for (Book rentedBook : manager.getRentedBooks()) {
            members.add(handler.get(rentedBook.getMember()));
        }
        return members;
    }

}
