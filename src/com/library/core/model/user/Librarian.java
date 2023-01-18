package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.repository.book.BooksManager;
import com.library.core.repository.user.UserDetailsManager;

import java.util.Collection;

public class Librarian extends User {

    private final UserDetailsManager detailsManager;
    private final BooksManager manager;

    public Librarian(String id, String name, String phoneNumber, String password, UserDetailsManager detailsManager, BooksManager manager) {
        super(id, name, phoneNumber, password);
        this.detailsManager = detailsManager;
        this.manager = manager;
    }

    public Collection<User> getRentedMembers() {
        return detailsManager.getRentedUsers();
    }
    public Collection<User> getAllMember(){
        return detailsManager.getAllUsers();
    }

    public void addBook(Book book) {
        manager.addBook(book);
    }

    public void removeBook(String id) {
        manager.removeBook(id);
    }

    @Override
    public Collection<Book> getAllBooks() {
        return manager.getAllBooks();
    }
}
