package com.example.secondsemproject ;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;

public class JDBCConnection {
    public static Connection connection = null;
    public static Statement statement;

    public JDBCConnection() {
    }

    public static void getConnection() {
        try {

            String url = "jdbc:sqlite:src/main/java/com/example/secondsemproject/database3.db";
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            System.out.println("the connection was successful");



        } catch (SQLException var1) {
            System.err.println("Error connecting to SQLite database: " + var1.getMessage());
        }

    }

    public static ResultSet ExecuteQuery(String query) throws SQLException {

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        return resultSet;


    }
    public static void ExecuteQueryWithNoResult(String query) throws SQLException {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException var1) {
            System.err.println("Error connecting to SQLite database: " + var1.getMessage());
        }

    }
    public static boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}