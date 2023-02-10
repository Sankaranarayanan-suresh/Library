package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.repository.book.UserBookManager;
import com.library.core.repository.user.UserAccountManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Member extends User {
    private final List<Book> rentedBooks;
    private int noOfBooksRented;
    private final UserBookManager bookManager;
    private final UserAccountManager accountManager;
    private LocalDate memberShipValidDate;

    public Member(String id, String name, String phoneNumber, String password,LocalDate memberShipValidDate ,int noOfBooksRented,
                  UserBookManager manager, UserAccountManager accountManager) {
        super(id, name, phoneNumber, password);
        this.memberShipValidDate = memberShipValidDate;
        this.bookManager = manager;
        this.accountManager = accountManager;
        this.noOfBooksRented = noOfBooksRented;
        this.rentedBooks = new ArrayList<>();
    }

    public LocalDate getMemberShipValidDate() {
        return memberShipValidDate;
    }
    public int getNoOfBooksRented() {
        return noOfBooksRented;
    }
    public List<Book> getAllBooks() {
        return (List<Book>) bookManager.getAvailableBooks();
    }

    public void returnBook(Book book) {
        if (!rentedBooks.contains(book))
            throw new RuntimeException("You cannot return a book that you don't have");
        rentedBooks.removeIf(books -> books.serialNumber.equals(book.serialNumber));
        bookManager.returnBook(book);
        noOfBooksRented--;
    }

    public Collection<Book> getRentedBooks() {
        if (noOfBooksRented < bookManager.getRentedBooks(this.getPhoneNumber()).size()){
            rentedBooks.addAll(bookManager.getRentedBooks(this.getPhoneNumber()));
            noOfBooksRented = rentedBooks.size();
        }
        return rentedBooks;
    }

    public void upgradeSubscription(int days) {
        this.memberShipValidDate = this.memberShipValidDate.plusDays(days);
    }

    public boolean rentBook(String serialNumber, int numberOfDays) {
        if (this.getMemberShipValidDate().isBefore(java.time.LocalDate.now()))
            throw new RuntimeException("You membership validity is over.");
        if (this.getMemberShipValidDate().isBefore(java.time.LocalDate.now().plusDays(numberOfDays)))
            throw new RuntimeException("Upgrade your plan to rent a book!!");
        Book book = bookManager.rentBook(serialNumber, this.getPhoneNumber(), numberOfDays);
        if (book == null){
            return false;
        }
        rentedBooks.add(book);
        noOfBooksRented++;
        return true;
    }
    public void deleteAccount(){
        if (noOfBooksRented>0){
            throw new RuntimeException("You cannot delete your account until you return all the books.");
        }

        accountManager.removeMember(this.getPhoneNumber());
    }

    @Override
    public String toString() {
        return  "id                     =  " + id + "\n" +
                "name                     =  " + this.getName() + "\n" +
                "phoneNumber              =  " + this.getPhoneNumber()+"\n" +
                "No.Of.Books rented       =  " + this.getNoOfBooksRented()+"\n" +
                "Membership validity date =  " + memberShipValidDate;
    }
}
