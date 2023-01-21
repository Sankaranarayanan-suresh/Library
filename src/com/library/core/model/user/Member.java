package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.model.book.RentedBook;
import com.library.core.repository.book.UserBookManager;
import com.library.core.repository.user.UserAccountManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Member extends User {


    private final List<RentedBook> rentedBooks;
    private final UserBookManager bookManager;
    private final UserAccountManager accountManager;
    private LocalDate memberShipValidDate;

    public Member(String id, String name, String phoneNumber, String password, UserBookManager manager, UserAccountManager accountManager) {
        super(id, name, phoneNumber, password);
        this.memberShipValidDate = java.time.LocalDate.now().plusDays(30);
        this.bookManager = manager;
        this.accountManager = accountManager;
        this.rentedBooks = new ArrayList<>();
    }

    public LocalDate getMemberShipValidDate() {
        return memberShipValidDate;
    }

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookManager.getAvailableBooks();
    }

    public void returnBook(RentedBook book) {
        if (!rentedBooks.contains(book))
            throw new RuntimeException("You cannot return a book that don't you have");
        RentedBook rentedBook = null;
        for (RentedBook books : rentedBooks) {
            if (books.id.equals(book.id))
                rentedBook = books;
        }
        rentedBooks.remove(rentedBook);
        bookManager.returnBook(book);
    }

    public List<RentedBook> getRentedBooks() {
        return rentedBooks;
    }

    public void upgradeSubscription(int days) {
        this.memberShipValidDate = this.memberShipValidDate.plusDays(days);
    }

    public void rentBook(String id, int numberOfDays) {

        RentedBook rentedBook = bookManager.rentBook(id, this, numberOfDays);
        if (rentedBook != null)
            rentedBooks.add(rentedBook);
    }
    public void deleteAccount(){
        accountManager.removeMember(this.id);
    }
}
