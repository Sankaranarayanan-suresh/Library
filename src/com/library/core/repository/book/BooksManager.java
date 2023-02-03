package com.library.core.repository.book;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;

import java.time.Year;
import java.util.Collection;

public interface BooksManager {

    void addBook(String name, String authorName, Year yearReleased, BookCategory category);

    void removeBook(String id);

    Collection<Book> getAllBooks();
    Collection<Book> getRentedBooks();

}
