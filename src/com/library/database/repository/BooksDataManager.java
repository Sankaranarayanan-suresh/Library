package com.library.database.repository;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;
import com.library.core.model.book.RentedBook;
import com.library.core.model.user.Member;
import com.library.core.repository.book.BooksManager;
import com.library.core.repository.book.RentedBooksManager;
import com.library.core.repository.book.UserBookManager;
import com.library.database.model.DatabaseFunctions;
import com.library.database.utils.Utils;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BooksDataManager implements BooksManager, UserBookManager {

    private final DatabaseFunctions<Book> database;
    private final RentedBooksManager rentedBookDatabase;

    public BooksDataManager(DatabaseFunctions<Book> database, RentedBooksManager rentedBookDatabase) {
        this.database = database;
        this.rentedBookDatabase = rentedBookDatabase;
    }

    @Override
    public void addBook(String name, String authorName, Year yearReleased, BookCategory category) {
        Book book = new Book(Utils.generateID("Book"), name, authorName, yearReleased, category);
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
        return rentedBookDatabase.getRentedBooks();
    }

    @Override
    public Collection<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : database.getAll()) {
            if (book.getQuantity() > 0) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    public RentedBook rentBook(String id, Member member, int numberOfDays) {
        if (member.getMemberShipValidDate().isAfter(java.time.LocalDate.now()))
            throw new RuntimeException("You membership validity is over.");
        if (!member.getMemberShipValidDate().isAfter(member.getMemberShipValidDate().plusDays(numberOfDays)))
            throw new RuntimeException("Upgrade your plan to rent a book!!");
        Book book = database.get(id);
        book.updateQuantity(-1);
        database.update(book);
        RentedBook rentedBook = new RentedBook(book.id, book.getName(), book.getAuthorName(), book.getYearReleased(), book.getCategory(), member, numberOfDays);
        rentedBookDatabase.updateUserRentedBook(rentedBook);
        return rentedBook;
    }

    @Override
    public void returnBook(Book book) {
        book.updateQuantity(+1);
        database.update(book);
        rentedBookDatabase.removeUserRentedBook(book.id);
    }
}
