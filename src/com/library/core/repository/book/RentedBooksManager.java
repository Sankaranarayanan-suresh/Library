package com.library.core.repository.book;

import com.library.core.model.book.RentedBook;

import java.util.Collection;

public interface RentedBooksManager {

    void updateUserRentedBook(RentedBook rentedBook);
    void removeUserRentedBook(String id);
    Collection<RentedBook> getRentedBooks();

}
