package com.library.core.model.book;

import com.library.core.model.user.Member;

import java.time.LocalDate;
import java.time.Year;

public class RentedBook extends Book {

    private final LocalDate returnDate;
    private final Member member;

    @Override
    public String toString() {
        return  "id         = " + id + "\n" +
                "serialNumber = " + super.getSerialNumber() + "\n" +
                "Name         = " + getName() +"\n"+
                "returnDate   = " + returnDate +"\n" +
                "member       = " + member.getName()+"\n" ;
    }

    public RentedBook(String id,int serialNumber, String name, String authorName, Year yearReleased, BookCategory category, Member customerID, int numberOfDays) {
        super(id,serialNumber,name, authorName, yearReleased, category);
        this.returnDate = java.time.LocalDate.now().plusDays(numberOfDays);
        this.member = customerID;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Member getMember() {
        return member;
    }
}
