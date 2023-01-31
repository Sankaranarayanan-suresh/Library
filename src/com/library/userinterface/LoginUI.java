package com.library.userinterface;

import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.database.repository.UserDataManager;
import com.library.database.utils.Utils;

import java.util.Scanner;

public class LoginUI {
    private final UserDataManager userDataManager;

    public LoginUI(UserDataManager userDataManager) {
        this.userDataManager = userDataManager;
    }

    public Member signUp() {
        System.out.println("Enter Your name: ");
        String name = new Scanner(System.in).nextLine();

        System.out.println("******  Remember the phone number that you enter now.You will be using your phone number to login again.******");
        System.out.println("Enter Your phone number: ");
        String phoneNumber = Utils.getPhoneNumber();

        if (userDataManager.userExists(phoneNumber)) {
            System.out.println("User already Exists!!!");
            signUp();
        }

        while (true) {
            System.out.println("Enter password for your account: ");
            String password = Utils.getPassword();
            System.out.println("Re-Enter your password: ");
            String reenteredPassword =Utils.getPassword();
            assert password != null;
            if (!password.equals(reenteredPassword)) {
                System.err.println("Password Mismatch");
                continue;
            }
            System.out.println("Sign-Up successful!!\nAs you are a new member you have initial Membership of one month.\nEnjoy reading:)");
            return userDataManager.addMember(name, phoneNumber, password);
        }
    }

    public User signIn() {
        while (true) {
            System.out.println("Enter Your phone number: ");
            String phoneNumber = Utils.getPhoneNumber();
            System.out.println("Enter the password: ");
            String password = Utils.getPassword();
            if (userDataManager.getUser(phoneNumber) instanceof Member) {
                if (userDataManager.checkUserCredentials(phoneNumber, password)) {
                    System.out.println("\n\nLogin Successfully Completed!!");
                    return userDataManager.getUser(phoneNumber);
                } else {
                    System.err.println("Incorrect credentials!!");
                }
            } else if (userDataManager.getUser(phoneNumber) instanceof Librarian) {
                if (userDataManager.checkUserCredentials(phoneNumber, password)) {
                    System.out.println("\n\nLogin Successfully Completed!!");
                    return userDataManager.getUser(phoneNumber);
                } else {
                    System.err.println("Incorrect credentials!!");
                }
            } else {
                System.err.println("No such User exists!!");
                return null;
            }
        }
    }
}
