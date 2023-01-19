package com.library.core.model.book;

import com.library.core.model.user.Member;

import java.time.LocalDate;
import java.time.Year;

public class RentedBook extends Book{

    private final LocalDate returnDate;
    private final Member member;
    public RentedBook(String id, String name, String authorName, Year yearReleased, BookCategory category,Member customerID) {
        super(id, name, authorName, yearReleased, category);
        this.returnDate = java.time.LocalDate.now();
        this.member = customerID;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Member getMember() {
        return member;
    }
}
