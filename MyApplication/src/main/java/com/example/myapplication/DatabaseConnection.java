package com.example.myapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection databaseLink;

    String databaseName = "myApplicationDB";
    String userName = "root";
    String password = "passdimuna#19";
    String URL = "jdbc:mysql://localhost:3306/" + databaseName;

    public Connection getConnection(){
    try {
        databaseLink = DriverManager.getConnection(URL, userName, password);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
        return databaseLink;
    }
}
