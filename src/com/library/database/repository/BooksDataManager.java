package com.library.database.repository;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;
import com.library.core.repository.book.BooksManager;
import com.library.core.repository.book.UserBookManager;
import com.library.database.model.BookDatabaseFunctions;
import com.library.database.utils.Utils;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BooksDataManager implements BooksManager, UserBookManager {

    private final BookDatabaseFunctions<Book> handler;

    public BooksDataManager(BookDatabaseFunctions<Book> database) {
        this.handler = database;
    }

    @Override
    public void addBook(String name, String authorName, Year yearReleased, BookCategory category) {

        Book newBook;
        for (Book book : handler.getAll()) {
            if ((book.getName().equals(name)) && book.getAuthorName().equals(authorName)) {
                newBook = new Book(book.id, Utils.getBookSerialNumber(), name, authorName, yearReleased, category, null);
                handler.set(newBook);
                return;
            }
        }
        String serialNumber = Utils.getBookSerialNumber();
        newBook = new Book(Utils.generateID("book",serialNumber), serialNumber, name, authorName, yearReleased,
                category, null);
        handler.set(newBook);
    }

    @Override
    public void removeBook(String id) {
        handler.remove(id);
    }

    @Override
    public Collection<Book> getAllBooks() {
        return handler.getAll();
    }

    @Override
    public Collection<Book> getAllRentedBooks() {
        List<Book> rentedBooks = new ArrayList<>();
        for (Book book : handler.getAllRentedBooks()) {
            if (!book.isAvailable()) {
                rentedBooks.add(book);
            }
        }
        return rentedBooks;    }

    @Override
    public Collection<Book> getRentedBooks(String phoneNumber) {
        List<Book> rentedBooks = new ArrayList<>();
        for (Book book : handler.getRentedBooks(phoneNumber)) {
            if (!book.isAvailable()) {
                rentedBooks.add(book);
            }
        }
        return rentedBooks;
    }

    @Override
    public Collection<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : handler.getAll()) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    public Book rentBook(String id, String phoneNumber, int numberOfDays) {
        Book book = handler.get(id);
        book.changeAvailability();
        LocalDate returnDate = LocalDate.now().plusDays(numberOfDays);
        Book rentedBook = new Book(book.id, book.getSerialNumber(), book.getName(), book.getAuthorName(),
                book.getYearReleased(), book.getCategory(), book.isAvailable(), returnDate, phoneNumber);
        handler.update(rentedBook);
        return rentedBook;
    }

    @Override
    public void returnBook(Book rentedBook) {
        rentedBook.changeAvailability();
        Book returnBook = new Book(rentedBook.id, rentedBook.getSerialNumber(), rentedBook.getName(), rentedBook.getAuthorName(),
                rentedBook.getYearReleased(), rentedBook.getCategory(), null);
        handler.update(returnBook);
    }
}
