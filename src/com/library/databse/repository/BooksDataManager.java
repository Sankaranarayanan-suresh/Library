package com.library.databse.repository;

import com.library.core.model.book.Book;
import com.library.core.model.book.RentedBook;
import com.library.core.model.user.Member;
import com.library.core.repository.book.BooksManager;
import com.library.core.repository.book.UserBookManager;
import com.library.databse.model.DatabaseFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BooksDataManager implements BooksManager, UserBookManager {

    private final DatabaseFunctions<Book> database;
    private final DatabaseFunctions<RentedBook> rentedBookDatabase;

    public BooksDataManager(DatabaseFunctions<Book> database,DatabaseFunctions<RentedBook> rentedBookDatabase) {
        this.database = database;
        this.rentedBookDatabase = rentedBookDatabase;
    }

    @Override
    public void addBook(Book book) {
        database.set(book);
    }

    @Override
    public void removeBook(String id) {
        database.remove(id);
    }

    @Override
    public Collection<Book> getAllBooks() {
        return database.getAll();
    }

    @Override
    public Collection<RentedBook> getRentedBooks() {
        return rentedBookDatabase.getAll();
    }

    @Override
    public Collection<Book> getAvailableBooks() {
        List<Book> availableBooks =new ArrayList<>();
        for(Book book : database.getAll()){
            if (book.getQuantity()>0){
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    public RentedBook rentBook(String id, Member member) {
        Book book = database.get(id);
        book.updateQuantity(-1);
        database.update(book);
        RentedBook rentedBook = new RentedBook(book.id,book.getName(),book.getAuthorName(),book.getYearReleased(),book.getCategory(),member);
        rentedBookDatabase.set(rentedBook);
        return rentedBook;
    }

    @Override
    public void returnBook(Book book) {
        book.updateQuantity(+1);
        database.update(book);
        rentedBookDatabase.remove(book.id);
    }
}
