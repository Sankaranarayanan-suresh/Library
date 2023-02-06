package com.library.database.model;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;
import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.database.DBConnector;
import com.library.database.ManagerProvider;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersHandler implements DatabaseFunctions<User> {

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
                        "(id VARCHAR(255) not NULL, " +
                        "Name VARCHAR(255), " +
                        "phone_number VARCHAR(255) not NULL," +
                        "Password VARCHAR(255), " +
                        "Membership_validity_date DATE ," +
                        "User_type VARCHAR(255)," +
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
                        "(id VARCHAR(255) not NULL, " +
                        "Name VARCHAR(255), " +
                        "phone_number VARCHAR(255) not NULL," +
                        "Password VARCHAR(255), " +
                        "user_type VARCHAR(255)," +
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

    private List<Book> getUserRentedBooks(String phoneNumber) {
        List<Book> rentedBooks = new ArrayList<>();
        String query = "select * from book where MemberID = " + phoneNumber + "";
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                LocalDate returnDate = null;
                if (!(result.getDate(8) == null)) {
                    returnDate = result.getDate(8).toLocalDate();
                }
                rentedBooks.add(new Book(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        Year.of(result.getInt(5)), BookCategory.valueOf(result.getString(6))
                        , Boolean.parseBoolean(result.getString(7)), returnDate, result.getString(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rentedBooks;
    }

    @Override
    public Collection<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String query = "select * from members";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (result.next()){
                String ID = result.getString(1);
                String name = result.getString(2);
                String phoneNumber = result.getString(3);
                String password = result.getString(4);
                LocalDate validityDate = result.getDate(5).toLocalDate();
                List<Book> userRentedBooks = new ArrayList<>(getUserRentedBooks(phoneNumber));
                do {
                    users.add(new Member(ID, name, phoneNumber, password, validityDate,
                            ManagerProvider.getBooksDataManager(), ManagerProvider.getUserDataManager(), userRentedBooks));
                }while (result.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            String query = "select * from librarians";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (result.next()){
                String ID = result.getString(1);
                String name = result.getString(2);
                String phoneNumber = result.getString(3);
                String password = result.getString(4);
                do {
                    users.add(new Librarian(ID, name, phoneNumber, password, ManagerProvider.getUserDataManager(),
                            ManagerProvider.getBooksDataManager(), ManagerProvider.getLibraryDataManager()));
                }while (result.next());
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
            String query = "insert into members (id,Name,phone_number,password,membership_validity_date,user_type)" +
                    "values(?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, member.id);
                preparedStatement.setString(2, member.getName());
                preparedStatement.setString(3, member.getPhoneNumber());
                preparedStatement.setString(4, member.getPassword());
                preparedStatement.setDate(5, Date.valueOf(String.valueOf(member.getMemberShipValidDate())));
                preparedStatement.setString(6, member.getClass().getSimpleName());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        else if(data instanceof Librarian){
            Librarian librarian = (Librarian) data;
            String query = "insert into librarians (id,Name,phone_number,Password,user_type) values(?,?,?,?,?) ";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, librarian.id);
                preparedStatement.setString(2, librarian.getName());
                preparedStatement.setString(3, librarian.getPhoneNumber());
                preparedStatement.setString(4, librarian.getPassword());
                preparedStatement.setString(5,librarian.getClass().getSimpleName());
                preparedStatement.execute();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public User get(String key) {
        User user = null;
        try {
            String query = "select * from members where phone_number = "+key+"";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (!result.next()){
                String query_1 = "select * from librarians where phone_number = "+key+"";
                ResultSet resultSet = stmt.executeQuery(query_1);
                if (!resultSet.next()){
                    throw new RuntimeException("No such user Exits!");
                }
                String ID = resultSet.getString(1);
                String name = resultSet.getString(2);
                String phoneNumber = resultSet.getString(3);
                String password = resultSet.getString(4);
                user = new Librarian(ID,name,phoneNumber,password,ManagerProvider.getUserDataManager(),
                        ManagerProvider.getBooksDataManager(),ManagerProvider.getLibraryDataManager());
            }else {
            String ID = result.getString(1);
            String name = result.getString(2);
            String phoneNumber = result.getString(3);
            String password = result.getString(4);
            LocalDate validityDate = result.getDate(5).toLocalDate();
            user = new Member(ID,name,phoneNumber,password,validityDate,ManagerProvider.getBooksDataManager(),
                    ManagerProvider.getUserDataManager(),getUserRentedBooks(phoneNumber));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User data) {
        if (data instanceof Member) {
            Member member = (Member) data;
            String query = "update members set id = ?,Name = ?,phone_number = ?,password = ?" +
                    ",membership_validity_date = ?,user_type = ? where phone_number = "+data.getPhoneNumber()+"";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, member.id);
                preparedStatement.setString(2, member.getName());
                preparedStatement.setString(3, member.getPhoneNumber());
                preparedStatement.setString(4, member.getPassword());
                preparedStatement.setDate(5, Date.valueOf(String.valueOf(member.getMemberShipValidDate())));
                preparedStatement.setString(6, member.getClass().getSimpleName());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        else if(data instanceof Librarian){
            Librarian librarian = (Librarian) data;
            String query = "update librarians set id = ?,Name = ?,phone_number = ?,password = ?,user_type = ? " +
                    "where phone_number = "+data.getPhoneNumber()+"";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, librarian.id);
                preparedStatement.setString(2, librarian.getName());
                preparedStatement.setString(3, librarian.getPhoneNumber());
                preparedStatement.setString(4, librarian.getPassword());
                preparedStatement.setString(5,librarian.getClass().getSimpleName());
            }catch (SQLException e){
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
            if (!result.next()){
                String query_1 = "select * from librarians where phone_number = " + key + "";
                ResultSet resultSet = stmt.executeQuery(query_1);
                if (!resultSet.next()){
                    throw new RuntimeException("No such user Exits!");
                }
                String query_2 = "delete from librarians where phone_number = "+key+"";
                stmt.execute(query_2);
            }else {
                String query_2 = "delete from members where phone_number = "+key+"";
                stmt.execute(query_2);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
