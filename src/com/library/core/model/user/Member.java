package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.repository.book.UserBookManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Member extends User {


    private final LocalDate memberShipValidDate;
    private final List<Book> rentedBooks;
    private final UserBookManager manager;

    public Member(String id, String name, String phoneNumber, String password, UserBookManager manager) {
        super(id, name, phoneNumber, password);
        this.memberShipValidDate = java.time.LocalDate.now();
        this.manager = manager;
        this.rentedBooks = new ArrayList<>();
    }

    public LocalDate getMemberShipValidDate() {
        return memberShipValidDate;
    }

    @Override
    public Collection<Book> getAllBooks() {
        return manager.getAvailableBooks();
    }

    public void returnBook(Book book) {
        if (!rentedBooks.contains(book))
            throw new RuntimeException("You cannot return a book that don't you have");
        manager.returnBook(book);
    }

    public Collection<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void rentBook(String id) {
        manager.rentBook(id);
    }
}
