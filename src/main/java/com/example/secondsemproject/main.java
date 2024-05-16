package com.example.secondsemproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Date;

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

    String Category;
    String username;
    String name;
    String source;

    Boolean is_monthly;
    Boolean is_yearly;

    ResultSet result;
    PreparedStatement preparedStatement;
    void load_Expenditure(){
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
                Category = result.getString("Category");
                username = result.getString("Username");
                ExpenditureList.add(new Expenditure(Id,date,value,Category,username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }
    void load_Reminder(){
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
                Category = result.getString("Category");
                is_yearly= result.getBoolean("is_yearly");
                is_monthly = result.getBoolean("is_monthly");
                username = result.getString("Username");
                name = result.getString("name");
                reminderList.add(new Reminder(Id,name,Category,date,value,is_monthly,is_yearly,username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }
    void load_Category(){
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
                categories.add(new Category(Id,name,username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }
    void load_Income(){
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
                incomeList.add(new Income(Id,source,date,value,username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }
    void load_Wishlist(){
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
                wishlists.add(new Wishlist(Id,item_name,item_price,rate,date,username));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }


}