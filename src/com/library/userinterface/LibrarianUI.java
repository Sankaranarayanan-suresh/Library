package com.library.userinterface;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;
import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.database.utils.Utils;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarianUI {
    private final Librarian librarian;

    public LibrarianUI(Librarian librarian) {
        this.librarian = librarian;
    }

    private void editProfile() {
        editProfile:
        while (true) {
            System.out.println("1.Change Name\n2.Change password\n3.Go back to main-menu");
            int editProfilePreference = Utils.getInteger();
            switch (editProfilePreference) {
                case 1:
                    System.out.print("Enter your new name: ");
                    String newName = new Scanner(System.in).nextLine();
                    librarian.setName(newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.err.println("Changing your Password can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String passwordConfirmation = new Scanner(System.in).nextLine();
                    if (passwordConfirmation.equalsIgnoreCase("y")) {
                        while (true) {
                            System.out.println("Enter your old password: ");
                            String oldPassword = new Scanner(System.in).nextLine();
                            System.out.println("Enter your New Password: ");
                            String newPassword = new Scanner(System.in).nextLine();
                            if (!oldPassword.equals(newPassword)) {
                                librarian.setPassword(newPassword);
                                break;
                            }
                            System.out.println("Old password and new password are the same please change your password");
                        }
                    }
                case 3:
                    break editProfile;

            }
        }
    }

    private void viewListOfBooks() {
        List<Book> books = new ArrayList<>(librarian.getAllBooks());
        System.out.println(librarian.getAllBooks().size());
        int i = 1;
        if (books.size() == 0) {
            System.out.println("No Books to view!!");
            return;
        }
        for (Book book : books) {
            System.out.println(i++ + "." + book);
        }
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();
    }

    private void viewRentedMembers() {
        List<Member> members = new ArrayList<>(librarian.getRentedMembers());
        int i = 1;
        if (members.size() == 0) {
            System.out.println("No member to view!!");
            return;
        }
        for (Member member : members) {
            System.out.println(i++ + "." + member);
        }
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();
    }

    private void addBook() {
        System.out.println("Enter the book name: ");
        String bookName = new Scanner(System.in).nextLine();
        System.out.println("Enter the Author name: ");
        String authorName = new Scanner(System.in).nextLine();
        System.out.println("Enter the year of release: ");
        Year year = Utils.getYear();
        int i = 1;
        for (BookCategory category : BookCategory.values()) {
            System.out.println(i++ + "." + category);
        }
        System.out.println("Select the category of the book");
        int category = Utils.getInteger();
        while (category > BookCategory.values().length) {
            System.out.println("Select from the given category!");
            category = Utils.getInteger();
        }
        BookCategory bookCategory = null;
        switch (category) {
            case 1:
                bookCategory = BookCategory.ENGLISH;
                break;
            case 2:
                bookCategory = BookCategory.MATHS;
                break;
            case 3:
                bookCategory = BookCategory.PHYSICS;
                break;
            case 4:
                bookCategory = BookCategory.CHEMISTRY;
                break;
            case 5:
                bookCategory = BookCategory.BIOLOGY;
                break;
            case 6:
                bookCategory = BookCategory.TAMIL;
                break;
            case 7:
                bookCategory = BookCategory.UNKNOWN;
                break;
            default:
                System.out.println("Select from the given categories!");
        }
        librarian.addBook(bookName, authorName, year, bookCategory);
        System.out.println("Books added successfully:)");
    }

    private void removeBook() {
        List<Book> books = new ArrayList<>(librarian.getAllBooks());
        int i = 1;
        if (books.size() == 0) {
            System.out.println("No Books to view!!");
            return;
        }
        for (Book book : books) {
            System.out.println(i++ + "." + book);
        }
        System.out.println("Select the book you want to remove!");
        int removingPreference = Utils.getInteger();
        while (removingPreference > books.size()) {
            System.out.println("Select from the given list!!");
            removingPreference = Utils.getInteger();
        }
        Book book = books.get(removingPreference-1);
        try {
            librarian.removeBook(book.id);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void viewProfile() {
        System.out.println(librarian);
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();
    }

    private void viewRentedBooks() {
        List<Book> books = new ArrayList<>(librarian.getRentedBooks());
        int i = 1;
        if (books.size() == 0) {
            System.out.println("No Books to view!!");
            return;
        }
        for (Book book : books) {
            System.out.println(i++ + "." + book);
        }
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();

    }

    private void changeMembershipPlans() {
        System.out.println("Enter the old cost of the plan that you want to change: Rs.");
        double oldCost = Utils.getAmount();
        System.out.println("Enter the new amount: Rs.");
        double newCost = Utils.getAmount();
        librarian.changeMembershipPlans(oldCost, newCost);
    }

    private void viewMembers() {
        List<Member> members = new ArrayList<>(librarian.getAllMember());
        int i = 1;
        if (members.size() == 0) {
            System.out.println("No member to view!!");
            return;
        }
        for (Member member : members) {
            System.out.println(i++ + "." + member);
        }
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();
    }

    public void showMenu() {
        driverFunction:
        while (true) {
            System.out.println("\n1.Edit My profile\n2.View List of Books\n3.View rented Members\n4.Add Book\n5.Remove book\n" +
                    "6.View my Profile\n7.View RentedBooks\n8.View Members\n9.Change membership plans\n10.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    viewListOfBooks();
                    break;
                case 3:
                    viewRentedMembers();
                    break;
                case 4:
                    addBook();
                    break;
                case 5:
                    removeBook();
                    break;
                case 6:
                    viewProfile();
                    break;
                case 7:
                    viewRentedBooks();
                    break;
                case 8:
                    viewMembers();
                    break;
                case 9:
                    changeMembershipPlans();
                    break;
                case 10:
                    break driverFunction;
                default:
                    System.out.println("Please select from the given option.");
            }
        }

    }

}
