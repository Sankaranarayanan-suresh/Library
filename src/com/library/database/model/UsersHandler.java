package com.library.database.model;

import com.library.core.model.book.Book;
import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.database.DBConnector;
import com.library.database.ManagerProvider;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersHandler implements UserDatabaseFunctions<User> {

    private static final Connection con = DBConnector.getConnection();
    private static UsersHandler handlerInstance = null;

    private UsersHandler() {
    }

    public static UsersHandler getInstance() {
        DatabaseMetaData tables;
        try {
            tables = con.getMetaData();
            ResultSet res = tables.getTables(null, "Library", "Members", null);
            Statement stmt = con.createStatement();
            boolean tableExist = res.next();
            if (!tableExist) {
                String sql = "CREATE TABLE Members" +
                        "(id VARCHAR(20) not NULL, " +
                        "Name VARCHAR(50) not NULL, " +
                        "phone_number VARCHAR(25) not NULL," +
                        "Password VARCHAR(20) not NULL, " +
                        "No_of_books_rented INTEGER(2) , " +
                        "Membership_validity_date DATE not NULL," +
                        "PRIMARY KEY ( phone_number ))";
                stmt.execute(sql);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            tables = con.getMetaData();
            ResultSet res = tables.getTables(null, "Library", "Librarians", null);
            Statement stmt = con.createStatement();
            boolean tableExist = res.next();
            if (!tableExist) {
                String sql = "CREATE TABLE librarians" +
                        "(id VARCHAR(20) not NULL, " +
                        "Name VARCHAR(50) not NULL, " +
                        "phone_number VARCHAR(25) not NULL," +
                        "Password VARCHAR(20) not NULL, " +
                        "PRIMARY KEY ( phone_number ))";
                stmt.execute(sql);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        if (handlerInstance == null)
            handlerInstance = new UsersHandler();
        return handlerInstance;
    }

    @Override
    public Collection<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String query = "select * from members";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String ID = result.getString(1);
                String name = result.getString(2);
                String phoneNumber = result.getString(3);
                String password = result.getString(4);
                int noOfBooksRented = result.getInt(5);
                LocalDate validityDate = result.getDate(6).toLocalDate();
                users.add(new Member(ID, name, phoneNumber, password, validityDate,noOfBooksRented,
                        ManagerProvider.getBooksDataManager(), ManagerProvider.getUserDataManager()));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            String query = "select * from librarians";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                String ID = result.getString(1);
                String name = result.getString(2);
                String phoneNumber = result.getString(3);
                String password = result.getString(4);
                do {
                    users.add(new Librarian(ID, name, phoneNumber, password, ManagerProvider.getUserDataManager(),
                            ManagerProvider.getBooksDataManager(), ManagerProvider.getLibraryDataManager()));
                } while (result.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void set(User data) {
        if (data instanceof Member) {
            Member member = (Member) data;
            String query = "insert into members (id,Name,phone_number,password,No_of_books_rented,membership_validity_date)" +
                    "values(?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, member.id);
                preparedStatement.setString(2, member.getName());
                preparedStatement.setString(3, member.getPhoneNumber());
                preparedStatement.setString(4, member.getPassword());
                preparedStatement.setInt(5,member.getNoOfBooksRented());
                preparedStatement.setDate(6, Date.valueOf(String.valueOf(member.getMemberShipValidDate())));
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else if (data instanceof Librarian) {
            Librarian librarian = (Librarian) data;
            String query = "insert into librarians (id,Name,phone_number,Password) values(?,?,?,?) ";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, librarian.id);
                preparedStatement.setString(2, librarian.getName());
                preparedStatement.setString(3, librarian.getPhoneNumber());
                preparedStatement.setString(4, librarian.getPassword());
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public User get(String key) {
        User user = null;
        try {
            String query = "select * from members where phone_number = " + key + "";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (!result.next()) {
                String query_1 = "select * from librarians where phone_number = " + key + "";
                ResultSet resultSet = stmt.executeQuery(query_1);
                if (!resultSet.next()) {
                    throw new RuntimeException("No such user Exits!");
                }
                String ID = resultSet.getString(1);
                String name = resultSet.getString(2);
                String phoneNumber = resultSet.getString(3);
                String password = resultSet.getString(4);
                user = new Librarian(ID, name, phoneNumber, password, ManagerProvider.getUserDataManager(),
                        ManagerProvider.getBooksDataManager(), ManagerProvider.getLibraryDataManager());
            } else {
                String ID = result.getString(1);
                String name = result.getString(2);
                String phoneNumber = result.getString(3);
                String password = result.getString(4);
                int noOfBooksRented = result.getInt(5);
                LocalDate validityDate = result.getDate(6).toLocalDate();
                user = new Member(ID, name, phoneNumber, password, validityDate,noOfBooksRented,ManagerProvider.getBooksDataManager(),
                        ManagerProvider.getUserDataManager());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User data) {
        if (data instanceof Member) {
            Member member = (Member) data;
            String query = "update members set id = ?,Name = ?,phone_number = ?,password = ?,No_of_books_rented = ?" +
                    ",membership_validity_date = ? where phone_number = " + data.getPhoneNumber() + "";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, member.id);
                preparedStatement.setString(2, member.getName());
                preparedStatement.setString(3, member.getPhoneNumber());
                preparedStatement.setString(4, member.getPassword());
                preparedStatement.setInt(5,member.getNoOfBooksRented());
                preparedStatement.setDate(6, Date.valueOf(String.valueOf(member.getMemberShipValidDate())));
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else if (data instanceof Librarian) {
            Librarian librarian = (Librarian) data;
            String query = "update librarians set id = ?,Name = ?,phone_number = ?,password = ? " +
                    "where phone_number = " + data.getPhoneNumber() + "";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, librarian.id);
                preparedStatement.setString(2, librarian.getName());
                preparedStatement.setString(3, librarian.getPhoneNumber());
                preparedStatement.setString(4, librarian.getPassword());
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void remove(String key) {
        String query = "select * from members where phone_number = " + key + "";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (!result.next()) {
                String query_1 = "select * from librarians where phone_number = " + key + "";
                ResultSet resultSet = stmt.executeQuery(query_1);
                if (!resultSet.next()) {
                    throw new RuntimeException("No such user Exits!");
                }
                String query_2 = "delete from librarians where phone_number = " + key + "";
                stmt.execute(query_2);
            } else {
                String query_2 = "delete from members where phone_number = " + key + "";
                stmt.execute(query_2);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
