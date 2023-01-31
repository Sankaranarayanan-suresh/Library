package com.library.userinterface;

import com.library.core.model.book.Book;
import com.library.core.model.book.RentedBook;
import com.library.core.model.user.Member;
import com.library.database.repository.LibraryDataManager;
import com.library.database.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberUI {

    private final Member member;
    private final LibraryDataManager libraryDataManager;

    public MemberUI(Member member, LibraryDataManager libraryDataManager) {
        this.member = member;
        this.libraryDataManager = libraryDataManager;
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
                    member.setName(newName);
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
                                member.setPassword(newPassword);
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

    private void rentBook() {
        List<Book> books = new ArrayList<>(member.getAllBooks());
        int i = 1;
        if (books.size() == 0) {
            System.out.println("No Books to view!!");
            return;
        }
        for (Book book : books) {
            System.out.println(i++ + "." + book);
        }
        System.out.println("Select the book that you want to rent.");
        int rentBookPreference = Utils.getInteger();
        while (rentBookPreference > member.getAllBooks().size()) {
            System.out.println("Select from the given books.");
            rentBookPreference = Utils.getInteger();
        }
        System.out.println("Enter the number days you want to rent: ");
        int numberOdDays = Utils.getInteger();
        String bookID = member.getAllBooks().get(rentBookPreference-1).id;
        try {
            member.rentBook(bookID, numberOdDays);
            System.out.println("Book rented successfully");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteAccount() {
        try {
            member.deleteAccount();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void viewRentedBooks() {
        List<RentedBook> books = new ArrayList<>(member.getRentedBooks());
        int i = 1;
        if (books.size() == 0) {
            System.out.println("You have not rented any book!!");
            return;
        }
        for (RentedBook book : books) {
            System.out.println(i++ + "." + book);
        }
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();
    }

    private boolean payment(double actualAmount) {
        System.out.print("Enter the amount: Rs.");
        double amount = Utils.getAmount();

        while (amount != actualAmount) {
            System.out.println("Incorrect amount!!!!");
            System.out.println("Enter Correct amount: Rs.");
            amount = Utils.getAmount();
        }
        System.out.println("Payment Successful:)");
        return true;
    }

    private void returnBook() {
        List<RentedBook> books = new ArrayList<>(member.getRentedBooks());
        int i = 1;
        if (books.size() == 0) {
            System.out.println("No Books to return!!");
            return;
        }
        for (RentedBook book : books) {
            System.out.println(i++ + "." + book);
        }
        System.out.println("Select the book that you want to return.");
        int returnBookPreference = Utils.getInteger();
        while (returnBookPreference > member.getRentedBooks().size()) {
            System.out.println("Select from the given books.");
            returnBookPreference = Utils.getInteger();
        }
        RentedBook book = member.getRentedBooks().get(returnBookPreference-1);
        if (book.getReturnDate().isBefore(java.time.LocalDate.now())) {
            System.out.println("The return date of the book has passed. You need tp pay a penalty for the late return.");
            int numberOfDays = book.getReturnDate().compareTo(java.time.LocalDate.now());
            System.out.println("Penalty amount is : Rs." + numberOfDays * 50);
            payment(numberOfDays * 50);
        }
        try {
            member.returnBook(book);
            System.out.println("Book returned successfully:)");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void upgradeSubscription() {
        double[] planCost = libraryDataManager.getPlanCost();
        for (int i = 1; i <= planCost.length; i++) {
            System.out.println(i + "." + i * 3 + "-Months --->" + planCost[i - 1]);
        }
        System.out.println("Select your Plan!!");
        int planPreference = Utils.getInteger();
        while (planPreference > planCost.length) {
            System.out.println("Select from the given plan!!");
            planPreference = Utils.getInteger();
        }
        if (payment(planCost[planPreference - 1]))
            member.upgradeSubscription(planPreference * 3 * 30);
    }

    private void viewProfile() {
        System.out.println(member.toString());
        System.out.println("Press any key to exit.");
        String exit = new Scanner(System.in).nextLine();
    }


    public void showMenu() {
        driverFunction:
        while (true) {
            System.out.println("\n1.Edit My profile\n2.Rent a book\n3.View rented Books\n4.Return Book\n5.View my Profile\n" +
                    "6.Upgrade Subscription\n7.Delete account\n8.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    rentBook();
                    break;
                case 3:
                    viewRentedBooks();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    upgradeSubscription();
                    break;
                case 7:
                    deleteAccount();
                    break driverFunction;
                case 8:
                    break driverFunction;
                default:
                    System.out.println("Please select from the given option.");
            }
        }

    }

}
