package com.library.core.model.book;

import java.time.Year;

public class Book {

    public final String id;
    private final String name;
    private final String authorName;
    private final Year yearReleased;
    private final BookCategory category;
    private int quantity;

    public Book(String id, String name, String authorName, Year yearReleased, BookCategory category) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.yearReleased = yearReleased;
        this.category = category;
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

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }
}
