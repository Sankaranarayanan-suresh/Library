package com.library.database.model;

import com.library.core.model.book.Book;
import com.library.core.model.book.BookCategory;
import com.library.database.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BookHandler implements DatabaseFunctions<Book> {


    private static final Connection con = DBConnector.getConnection();
    private static BookHandler handlerInstance = null;

    private BookHandler() {
    }

    public static BookHandler getInstance() {
        DatabaseMetaData tables;
        try {
            tables = con.getMetaData();
            ResultSet res = tables.getTables(null, "Library", "Book", null);
            Statement stmt = con.createStatement();
            boolean tableExist = res.next();
            if (!tableExist) {
                String sql = "CREATE TABLE book" +
                        "(id VARCHAR(255) not NULL, " +
                        "serial_number VARCHAR(255) not NULL," +
                        "Book_Name VARCHAR(255), " +
                        "Author_Name VARCHAR(255), " +
                        "Year INTEGER, " +
                        "Category VARCHAR(255)," +
                        "IsAvailable VARCHAR(255)," +
                        "return_date DATE DEFAULT NULL," +
                        "MemberID VARCHAR(255)," +
                        "PRIMARY KEY ( serial_number ))";
                stmt.execute(sql);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        if (handlerInstance == null)
            handlerInstance = new BookHandler();
        return handlerInstance;
    }

    @Override
    public Collection<Book> getAll() {
        String query = "select * from book";
        List<Book> allBooks = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                LocalDate returnDate = null;
                if (!(result.getDate(8) == null)) {
                    returnDate = result.getDate(8).toLocalDate();
                }
                allBooks.add(new Book(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        Year.of(result.getInt(5)), BookCategory.valueOf(result.getString(6))
                        , Boolean.parseBoolean(result.getString(7)), returnDate, result.getString(9)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allBooks;
    }

    @Override
    public void set(Book data) {
        String query = "insert into book (id,serial_number,Book_Name,Author_Name,Year,Category,isAvailable,MemberId)" +
                "values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, data.id);
            preparedStatement.setString(2, data.getSerialNumber());
            preparedStatement.setString(3, data.getName());
            preparedStatement.setString(4, data.getAuthorName());
            preparedStatement.setInt(5, Integer.parseInt(data.getYearReleased().toString()));
            preparedStatement.setString(6, data.getCategory().name());
            preparedStatement.setString(7, Boolean.toString(data.isAvailable()));
            preparedStatement.setString(8, data.getMember());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Book get(String key) {
        String query = "select * from Book where serial_number = " + key + "";
        Book book;
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            result.next();
            LocalDate returnDate = null;
            if (!(result.getDate(8) == null)) {
                returnDate = result.getDate(8).toLocalDate();
            }
            book = new Book(result.getString(1), result.getString(2),
                    result.getString(3), result.getString(4),
                    Year.of(result.getInt(5)), BookCategory.valueOf(result.getString(6))
                    , Boolean.parseBoolean(result.getString(7)), returnDate,
                    result.getString(9));

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return book;
    }

    @Override
    public void update(Book data) {
        Date returnDate;
        String query = "update book set id = ?,serial_number = ?,Book_Name = ?,Author_name = ?," +
                "Year = ?,Category = ?,isAvailable = ?,return_date = ?,MemberID = ? where serial_number = ?";
        if (data.getReturnDate() == null) {
            returnDate = null;
        } else {
            returnDate = Date.valueOf(String.valueOf(data.getReturnDate()));
        }
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, data.id);
            preparedStatement.setString(2, data.serialNumber);
            preparedStatement.setString(3, data.getName());
            preparedStatement.setString(4, data.getAuthorName());
            preparedStatement.setInt(5, Integer.parseInt(data.getYearReleased().toString()));
            preparedStatement.setString(6, data.getCategory().name());
            preparedStatement.setString(7, Boolean.toString(data.isAvailable()));
            preparedStatement.setDate(8, returnDate);
            preparedStatement.setString(9, data.getMember());
            preparedStatement.setString(10, data.serialNumber);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void remove(String key) {
        String query = "delete from book where serial_number = " + key + "";
        try {
            Statement stmt = con.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find that book in library");
        }
    }
}

