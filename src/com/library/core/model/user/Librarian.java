package com.library.core.model.user;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;
import com.library.core.model.book.RentedBook;
import com.library.core.repository.book.BooksManager;
import com.library.core.repository.library.LibraryManager;
import com.library.core.repository.user.UserDetailsManager;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Librarian extends User {

    private final UserDetailsManager detailsManager;
    private final BooksManager manager;
    private final LibraryManager libraryManager;

    public Librarian(String id, String name, String phoneNumber, String password, UserDetailsManager detailsManager, BooksManager manager,LibraryManager libraryManager) {
        super(id, name, phoneNumber, password);
        this.detailsManager = detailsManager;
        this.manager = manager;
        this.libraryManager = libraryManager;
    }

    public Collection<Member> getRentedMembers() {
        return detailsManager.getRentedUsers();
    }

    public Collection<RentedBook> getRentedBooks() {
        return manager.getRentedBooks();
    }

    public List<Member> getAllMember() {
        List<Member> members = new ArrayList<>();
        for (User user : detailsManager.getAllUsers())
            if (user instanceof Member)
                members.add((Member) user);
        return members;
    }

    public void addBook(String name, String authorName, Year yearReleased, BookCategory category) {
        manager.addBook(name, authorName, yearReleased, category);
    }
    public void changeMembershipPlans(double oldPlanCost, double newPlanCost){
        libraryManager.setPlanCost( oldPlanCost, newPlanCost);
    }
    public double[] getMembershipPlans(){
        return libraryManager.getPlanCost();
    }

    public void removeBook(String id) {
        manager.removeBook(id);
    }

    @Override
    public Collection<Book> getAllBooks() {
        return manager.getAllBooks();
    }
}
