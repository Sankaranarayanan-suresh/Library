package com.library.core.repository.book;

import com.library.core.model.book.Book;
import java.util.Collection;

public interface UserBookManager {

    Collection<Book> getAvailableBooks();

    Book rentBook(String id, String phoneNumber,int numberOfDays);

    void returnBook(Book book);
    Collection<Book> getRentedBooks();
}
