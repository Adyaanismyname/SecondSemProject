package com.example.secondsemproject;

import java.sql.*;

public class JDBCConnection {
    // Declaration of connection and statement variables
    public static Connection connection = null;
    public static Statement statement;


    // Default constructor
    public JDBCConnection() {
    }


    // Method to establish a connection to the SQLite database
    public static void getConnection() {
        try {
            String url = "jdbc:sqlite:src/main/java/com/example/secondsemproject/database3.db";

            // Establishing the connection
            connection = DriverManager.getConnection(url);

            // Creating a statement object for executing queries
            statement = connection.createStatement();

            System.out.println("The connection was successful");

        } catch (SQLException var1) {
            System.err.println("Error connecting to SQLite database: " + var1.getMessage());
        }
    }


    // Method to execute a query and return the result set
    public static ResultSet ExecuteQuery(String query) throws SQLException {

        ResultSet resultSet = null;

        try {
            // Executing the query and storing the result set
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {

            System.err.println("Error executing query: " + e.getMessage());
        }
        return resultSet;
    }

    // Method to execute a query with no expected result
    public static void ExecuteQueryWithNoResult(String query) throws SQLException {
        try {
            // Executing the query
            statement.execute(query);

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }

    // Method to close the connection to the database
    public static void close() {
        try {
            // Closing the connection
            connection.close();

        } catch (SQLException var1) {

            System.err.println("Error closing SQLite database connection: " + var1.getMessage());
        }
    }

    // Method to check if the connection to the database is valid
    public static boolean isConnectionValid() {
        try {
            // Checking if connection is not null and not closed
            return connection != null && !connection.isClosed();

        } catch (SQLException e) {
            
            // Error handling for checking connection validity
            e.printStackTrace();
            return false;
        }
    }
}
