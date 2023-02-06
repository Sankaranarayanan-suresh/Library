package com.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Connection con = null;
    public static Connection getConnection(){
        if(con==null){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","Test@123");
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return con;
    }
    private DBConnector(){

    }

}
