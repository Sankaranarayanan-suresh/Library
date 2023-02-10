package com.library.database.utils;

import com.library.database.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.Scanner;

public class Utils {
    public static int getInteger() {
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextInt();
        } catch (Exception e) {
            System.err.println("Enter valid option!!.");
            return getInteger();
        }
    }

    public static String getPhoneNumber() {
        String phoneNumber = new Scanner(System.in).nextLine();
        while (!phoneNumber.matches("[6-9]{1}[0-9]{9}")) {
            System.out.println("Phone number is not valid");
            phoneNumber = new Scanner(System.in).nextLine();
        }
        return phoneNumber;
    }

    public static String getBookSerialNumber() {
        Connection con = DBConnector.getConnection();
        String query = "SELECT serial_number as id from book order by id desc LIMIT 1;";
        Statement stmt;
        String serialNumber;
        try {
            stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            int id;
            if(!result.next()){
                id = 1;
                return String.valueOf(id);
            }
            id = Integer.parseInt(result.getString(1))+1;
            serialNumber = String.valueOf(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return serialNumber;
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

    public static String generateID(String type, String uniqueValue) {
        if (type.equalsIgnoreCase("book")) {
            return "Book-" + uniqueValue;
        } else if (type.equalsIgnoreCase("Member")) {
            return "Member-" + uniqueValue;
        } else if (type.equalsIgnoreCase("librarian")) {
            return "Librarian-" + uniqueValue;
        } else {
            throw new RuntimeException("Cannot create ID.");
        }
    }

    public static String getPassword() {
        String s = new Scanner(System.in).nextLine();
        while (!s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")) {
            System.out.println("Invalid Password");
            System.out.println("Note: The password should contain a lower case character, upper case character," +
                    " atleast one digit, a total of 8 - 20 character length, and special characters like ['@#$%^&-+=()']");
            System.out.println("Enter your password correctly: ");
            s = new Scanner(System.in).nextLine();
        }
        return s;
    }
}


