package com.library.database.utils;

import java.time.Year;
import java.util.Scanner;

public class Utils {
    private static int i = 1;

    public static int getInteger() {
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextInt();
        } catch (Exception e) {
            System.err.println("Enter valid option!!.");
            return getInteger();
        }
    }

    public static Year getYear() {
        int x = getInteger();
        while (Year.of(x).isAfter(Year.now())) {
            System.out.println("Enter Correct year: ");
            getYear();
        }
        return Year.of(x);
    }

    public static double getAmount() {
        Scanner sc = new Scanner(System.in);
        double x;
        try {
            x = sc.nextDouble();
            if (x <= 0) {
                throw new Exception();
            }
            return x;
        } catch (Exception e) {
            System.out.println("Enter correct Amount!!!.");
            return getAmount();
        }
    }

    public static String generateID(String type) {
        String s = type.hashCode() + String.valueOf(i++);
        if (type.equalsIgnoreCase("book")) {
            return "Book-" + s;
        } else if (type.equalsIgnoreCase("Member")) {
            return "Member-" + s;
        } else if (type.equalsIgnoreCase("librarian")) {
            return "Librarian-" + s;
        } else {
            throw new RuntimeException("Cannot create ID.");
        }
    }
}


