package com.library.core.repository.book;

import com.library.core.model.book.Book;

import java.util.Collection;

public interface UserBookManager {

    Collection<Book> getAvailableBooks();

    Collection<Book> getRentedBooks(String phoneNumber);

    void rentBook(String id);

    void returnBook(Book book);
}
