package com.example.secondsemproject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.secondsemproject.Category.categories;
import static com.example.secondsemproject.Expenditure.ExpenditureList;
import static com.example.secondsemproject.Income.incomeList;
import static com.example.secondsemproject.Reminder.reminderList;
import static com.example.secondsemproject.Wishlist.wishlists;
import static java.awt.AWTEventMulticaster.add;

public class main {
    LocalDate date;
    int Id;
    double value;

    String category;
    String username;
    String name;
    String source;

    Boolean is_monthly;
    Boolean is_yearly;

    ResultSet result;
    PreparedStatement preparedStatement;

    void load_Expenditure() {
        try {
            String query = "SELECT * FROM Expenditure";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            // Create an ArrayList to store the data


            // Iterate over the ResultSet and store the values in the ArrayList
            while (result.next()) {
                Id = result.getInt("Id");
                date = result.getDate("Date").toLocalDate();
                value = result.getDouble("value");
                category = result.getString("Category");
                username = result.getString("Username");
                ExpenditureList.add(new Expenditure(Id, date, value, category, username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }

    void load_Reminder() {
        try {
            String query = "SELECT * FROM Reminder";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            // Create an ArrayList to store the data


            // Iterate over the ResultSet and store the values in the ArrayList
            while (result.next()) {
                Id = result.getInt("Id");
                date = result.getDate("Date").toLocalDate();
                value = result.getDouble("value");
                category = result.getString("Category");
                is_yearly = result.getBoolean("is_yearly");
                is_monthly = result.getBoolean("is_monthly");
                username = result.getString("Username");
                name = result.getString("name");
                reminderList.add(new Reminder(Id, name, category, date, value, is_monthly, is_yearly, username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }

    void load_Category() {
        try {
            String query = "SELECT * FROM Category";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            // Create an ArrayList to store the data


            // Iterate over the ResultSet and store the values in the ArrayList
            while (result.next()) {
                Id = result.getInt("Id");
                name = result.getString("name");
                username = result.getString("Username");
                categories.add(new Category(Id, name, username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }

    void load_Income() {
        try {
            String query = "SELECT * FROM Income";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            // Create an ArrayList to store the data


            // Iterate over the ResultSet and store the values in the ArrayList
            while (result.next()) {
                Id = result.getInt("Id");
                date = result.getDate("Date").toLocalDate();
                value = result.getDouble("value");
                source = result.getString("source");
                username = result.getString("Username");
                incomeList.add(new Income(Id, source, date, value, username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }

    void load_Wishlist() {
        try {
            String query = "SELECT * FROM Wishlist";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            // Create an ArrayList to store the data


            // Iterate over the ResultSet and store the values in the ArrayList
            while (result.next()) {
                Id = result.getInt("Id");
                LocalDate last_cal_date = result.getDate("last_calculated_date").toLocalDate();
                double amount_saved = result.getDouble("amount_saved");
                double item_price = result.getDouble("item_price");
                double rate = result.getDouble("rate");
                username = result.getString("Username");
                String item_name = result.getString("item_name");
                wishlists.add(new Wishlist(Id, item_name, item_price, rate, date, username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }

    public static void setIdForTable(String tableName) {
        try {
            Statement statement = JDBCConnection.connection.createStatement();

            String query = "SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);

            int latestId = 0;
            if (resultSet.next()) {
                latestId = resultSet.getInt("Id");
            }

            // Update the stored ID in your Java program for the specified table
            switch (tableName) {
                case "Category":
                    Category.setId(latestId+1);
                    break;
                case "Income":
                    Income.setId(latestId+1);
                    break;
                case "Reminder":
                    Reminder.setId(latestId+1);
                    break;
                case "Expenditure":
                    Expenditure.setId(latestId+1);
                    break;
                case "Wishlist":
                    Wishlist.setId(latestId+1);
                    break;
                default:
                    System.out.println("FAILED TO UPDATE");
                    // Add more cases for additional tables
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public static void UpdateLatestIdForClass(String targetClass,int newLatestId,int oldLatestId) {

            try {
                // Establish a JDBC connection

                // Define the old latest ID, new latest ID, and the class for which you want to update the ID
                 // Example old latest ID
                 // Example new latest ID
                 // Specify the class for which you want to update the ID

                // Construct the SQL update statement for the specified class in the latest_id table
                String sql = "UPDATE latest_id SET " + targetClass + " = ? WHERE " + targetClass + " = ?";

                // Create a PreparedStatement with the SQL statement
                PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
                preparedStatement.setInt(1, newLatestId); // Set the new latest ID
                preparedStatement.setInt(2, oldLatestId); // Set the old latest ID

                // Execute the update query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check the number of rows affected
                if (rowsAffected > 0) {
                    System.out.println("ID updated successfully for " + targetClass);
                } else {
                    System.out.println("No row found for the given old latest ID or class");
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
}