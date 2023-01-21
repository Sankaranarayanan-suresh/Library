package com.library.database.repository;

import com.library.core.model.book.RentedBook;
import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.core.repository.book.UserRentedBookManager;
import com.library.core.repository.library.LibraryManager;
import com.library.core.repository.user.UserAccountManager;
import com.library.core.repository.user.UserDetailsManager;
import com.library.core.repository.user.UsersManager;
import com.library.database.model.DatabaseFunctions;
import com.library.database.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;

public class UserDataManager implements UserDetailsManager, UsersManager, UserAccountManager {
    private final DatabaseFunctions<User> database;
    private final UserRentedBookManager rentedBooks;
    private final BooksDataManager manager;
    private final LibraryManager libraryManager;

    public UserDataManager(DatabaseFunctions<User> database, UserRentedBookManager rentedBooks, BooksDataManager manager,LibraryManager libraryManager) {
        this.database = database;
        this.rentedBooks = rentedBooks;
        this.manager = manager;
        this.libraryManager = libraryManager;
    }

    @Override
    public Collection<User> getAllUsers() {
        return database.getAll();
    }

    public Member addMember(String name, String phoneNumber, String password) {
        Member member = new Member(Utils.generateID("Member"), name, phoneNumber, password, manager, this);
        database.set(member);
        return member;
    }
    public Librarian addLibrarian(String name, String phoneNumber, String password) {
        Librarian librarian = new Librarian(Utils.generateID("librarian"), name, phoneNumber, password,this, manager, libraryManager);
        database.set(librarian);
        return librarian;
    }

    public void removeMember(String id) {
        database.remove(id);
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
        for (RentedBook rentedBook : rentedBooks.getRentedBooks()) {
            members.add(rentedBook.getMember());
        }
        return members;
    }

}
