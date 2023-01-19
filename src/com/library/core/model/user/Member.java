package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.model.book.RentedBook;
import com.library.core.repository.book.UserBookManager;
import com.library.databse.model.RentedBooksHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Member extends User {


    private final LocalDate memberShipValidDate;
    private final List<RentedBook> rentedBooks;
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
    public void returnBook(RentedBook book) {
        if (!rentedBooks.contains(book))
            throw new RuntimeException("You cannot return a book that don't you have");
        RentedBook rentedBook = null;
        for (RentedBook books : rentedBooks){
            if (books.id.equals(book.id))
                rentedBook = books;
        }
        rentedBooks.remove(rentedBook);
        manager.returnBook(book);
    }

    public Collection<RentedBook> getRentedBooks() {
        return rentedBooks;
    }

    public void rentBook(String id) {
       RentedBook rentedBook =  manager.rentBook(id,this);
       if (rentedBook!=null)
           rentedBooks.add(rentedBook);
    }
}
