package com.library.core.repository.book;

import com.library.core.model.book.Book;
import com.library.core.model.book.RentedBook;
import com.library.core.model.user.Member;

import java.util.Collection;

public interface UserBookManager {

    Collection<Book> getAvailableBooks();

    RentedBook rentBook(String id, Member member,int numberOfDays);

    void returnBook(Book book);
    Collection<RentedBook> getRentedBooks();
}
