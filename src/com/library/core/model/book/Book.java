package com.library.core.model.book;

import java.time.LocalDate;
import java.time.Year;

public class Book {

    public final String id;
    public final String serialNumber;
    private final String name;
    private final String authorName;
    private final Year yearReleased;
    private final BookCategory category;
    private final LocalDate returnDate;
    private final String memberPhoneNumber;
    private boolean isAvailable;

    public Book(String id, String serialNumber, String name, String authorName, Year yearReleased, BookCategory category,
                String memberPhoneNumber) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.authorName = authorName;
        this.yearReleased = yearReleased;
        this.category = category;
        this.isAvailable = true;
        this.returnDate = null;
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public Book(String id, String serialNumber, String name, String authorName, Year yearReleased, BookCategory category,
                boolean isAvailable, LocalDate returnDate, String memberPhoneNumber) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.authorName = authorName;
        this.yearReleased = yearReleased;
        this.category = category;
        this.isAvailable = isAvailable;
        this.returnDate = returnDate;
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public void changeAvailability() {
        this.isAvailable = !this.isAvailable;
    }

    @Override
    public String toString() {
        return "id          = " + id + "\n" +
                "serialNumber  = " + serialNumber + "\n" +
                "name          = " + name + "\n" +
                "authorName    = " + authorName + "\n" +
                "yearReleased  = " + yearReleased + "\n" +
                "category      = " + category + "\n" +
                "Available     = " + isAvailable + "\n" +
                "return-Date   = " + returnDate+ "\n" +
                "PhoneNumber   = "+memberPhoneNumber +"\n";
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Year getYearReleased() {
        return yearReleased;
    }

    public BookCategory getCategory() {
        return category;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getMember() {
        return memberPhoneNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

}
