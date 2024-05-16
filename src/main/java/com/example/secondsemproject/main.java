package com.example.secondsemproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Date;

import static com.example.secondsemproject.Expenditure.ExpenditureList;
import static java.awt.AWTEventMulticaster.add;

public class main {
    LocalDate date;
    int Id;
    double value;
    String Category;
    String username;
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
                ExpenditureList.add(new Expenditure(Id,date,value,Category));
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
                username = result.getString("Username");
                ExpenditureList.add(new Expenditure(Id,date,value,Category));
            }

        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

    }


}
