package com.library.core.repository.book;

import com.library.core.model.book.RentedBook;

import java.util.Collection;

public interface UserRentedBookManager {
    Collection<RentedBook> getRentedBooks();
}
