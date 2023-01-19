package com.library.core.repository.book;

import com.library.core.model.book.Book;
import com.library.core.model.book.RentedBook;

import java.util.Collection;

public interface BooksManager {

    void addBook(Book book);

    void removeBook(String id);

    Collection<Book> getAllBooks();
    Collection<RentedBook> getRentedBooks();

}
