package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.repository.book.UserBookManager;
import com.library.core.repository.user.UserAccountManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Member extends User {


    private final List<Book> rentedBooks;
    private final UserBookManager bookManager;
    private final UserAccountManager accountManager;
    private LocalDate memberShipValidDate;

    public Member(String id, String name, String phoneNumber, String password,LocalDate memberShipValidDate ,
                  UserBookManager manager, UserAccountManager accountManager) {
        super(id, name, phoneNumber, password);
        this.memberShipValidDate = memberShipValidDate;
        this.bookManager = manager;
        this.accountManager = accountManager;
        this.rentedBooks = new ArrayList<>();

    }
    public Member(String id, String name, String phoneNumber, String password, LocalDate validityDate,
                  UserBookManager manager, UserAccountManager accountManager,List<Book> rentedBooks ) {
        super(id, name, phoneNumber, password);
        this.memberShipValidDate = validityDate;
        this.bookManager = manager;
        this.accountManager = accountManager;
        this.rentedBooks = new ArrayList<>(rentedBooks);
    }

    public LocalDate getMemberShipValidDate() {
        return memberShipValidDate;
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookManager.getAvailableBooks();
    }

    public void returnBook(Book book) {
        if (!rentedBooks.contains(book))
            throw new RuntimeException("You cannot return a book that don't you have");
        rentedBooks.removeIf(books -> books.serialNumber.equals(book.serialNumber));
        bookManager.returnBook(book);
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void upgradeSubscription(int days) {
        this.memberShipValidDate = this.memberShipValidDate.plusDays(days);
    }

    public void rentBook(String serialNumber, int numberOfDays) {
        if (this.getMemberShipValidDate().isBefore(java.time.LocalDate.now()))
            throw new RuntimeException("You membership validity is over.");
        if (this.getMemberShipValidDate().isBefore(java.time.LocalDate.now().plusDays(numberOfDays)))
            throw new RuntimeException("Upgrade your plan to rent a book!!");
        Book rentedBook = bookManager.rentBook(serialNumber, this.getPhoneNumber(), numberOfDays);
        System.out.println(rentedBook);
        if (rentedBook != null)
            rentedBooks.add(rentedBook);
        System.out.println(rentedBooks);
    }
    public void deleteAccount(){
        if (rentedBooks.size()>0){
            throw new RuntimeException("You cannot delete your account until you return all the books.");
        }
        accountManager.removeMember(this.getPhoneNumber());
    }

    @Override
    public String toString() {
        return  "id                     =  " + id + "\n" +
                "name                     =  " + this.getName() + "\n" +
                "phoneNumber              =  " + this.getPhoneNumber()+"\n" +
                "Membership validity date =  " + memberShipValidDate;
    }
}
