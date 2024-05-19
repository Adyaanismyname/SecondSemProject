package com.example.secondsemproject;

import org.sqlite.JDBC;

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
import static java.time.LocalDate.*;

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
        ExpenditureList.clear();
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
        reminderList.clear();
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
        categories.clear();
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
        incomeList.clear();
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
        wishlists.clear();
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

            String query = "SELECT Id FROM " + tableName + " ORDER BY Id DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);

            int latestId = 0;
            if (resultSet.next()) {
                latestId = resultSet.getInt("Id");
            }

            // Update the stored ID in your Java program for the specified table
            switch (tableName) {
                case "Category":
                    Category.setId(latestId + 1);
                    break;
                case "Income":
                    Income.setId(latestId + 1);
                    break;
                case "Reminder":
                    Reminder.setId(latestId + 1);
                    break;
                case "Expenditure":
                    Expenditure.setId(latestId + 1);
                    break;
                case "Wishlist":
                    Wishlist.setId(latestId + 1);
                    break;
                default:
                    System.out.println("FAILED TO UPDATE");
                    // Add more cases for additional tables
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void UpdateLatestIdForClass(String targetClass, int newLatestId, int oldLatestId) {

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

    void insert_Category(String name) {

        ////////////////////////////decleration///////////////////////////////////////////////

        Scanner input = new Scanner(System.in);
        PreparedStatement preparedStatement = null;
        /////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////getting input ///////////////////////////////////////
        while (true) {
            try {

                try {
                    String insertSql = "INSERT INTO Category(Id, name,Username) VALUES (?, ?, ?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);

                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, HelloController.getUsername_to_pass());


                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        int lastInsertedId = generatedKeys.getInt(1);
                        System.out.println("Category inserted with ID: " + lastInsertedId);
                        load_Category();
                    }
                    System.out.println("Rows inserted: " + rowsInserted);


                } catch (SQLException f) {
                    System.err.println("Error executing SQL query: " + f.getMessage());


                }
            } catch (Exception e) {
                System.out.println("re enter the value ");
            }
            try {
                break;

            } catch (Exception h) {
                System.out.println("Exiting on wrong Input");
            }


        }

    }

    void insert_Expenditure(LocalDate date, double value, String Category) {

        ////////////////////////////decleration///////////////////////////////////////////////


        PreparedStatement preparedStatement = null;
        /////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////getting input ///////////////////////////////////////
        while (true) {
            try {

                try {
                    String insertSql = "INSERT INTO Expenditure(Id, Date, value, Category, Username) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
//                        preparedStatement.setInt(1, all_book_id); // book_id
                    preparedStatement.setDate(2, Date.valueOf(date));
                    preparedStatement.setDouble(3, value);
                    preparedStatement.setString(4, Category);
                    preparedStatement.setString(5, HelloController.getUsername_to_pass());

                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int lastInsertedId = generatedKeys.getInt(1);
                        System.out.println("Expenditure inserted with ID: " + lastInsertedId);
                        load_Expenditure();


                    }
                    System.out.println("Rows inserted: " + rowsInserted);


                } catch (SQLException f) {
                    System.err.println("Error executing SQL query: " + f.getMessage());


                }
            } catch (Exception e) {
                System.out.println("re enter the value ");
            }
            try {
                break;

            } catch (Exception h) {
                System.out.println("Exiting on wrong Input");
            }


        }

    }

    void insert_Income(LocalDate date, double value, String source) {

        ////////////////////////////decleration///////////////////////////////////////////////

        PreparedStatement preparedStatement = null;
        /////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////getting input ///////////////////////////////////////
        while (true) {
            try {

                try {
                    String insertSql = "INSERT INTO Income(Id, Date, value, source, Username) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    preparedStatement.setDate(2, Date.valueOf(date));
                    preparedStatement.setDouble(3, value);
                    preparedStatement.setString(4, source);
                    preparedStatement.setString(5, HelloController.getUsername_to_pass());

                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int lastInsertedId = generatedKeys.getInt(1);
                        System.out.println("Income inserted with ID: " + lastInsertedId);
                        load_Income();
                    }
                    System.out.println("Rows inserted: " + rowsInserted);


                } catch (SQLException f) {
                    System.err.println("Error executing SQL query: " + f.getMessage());


                }
            } catch (Exception e) {
                System.out.println("re enter the value ");
            }
            try {
                break;

            } catch (Exception h) {
                System.out.println("Exiting on wrong Input");
            }


        }

    }

    void insert_Reminder(LocalDate date, double value, String name, String Category, Boolean is_yearly, Boolean is_monthly) {

        ////////////////////////////decleration///////////////////////////////////////////////


        PreparedStatement preparedStatement = null;
        /////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////getting input ///////////////////////////////////////
        while (true) {
            try {

                try {
                    String insertSql = "INSERT INTO Reminder(Id,Date,value,name,Category,is_yearly,is_monthly) VALUES (?, ?, ?, ?, ? ,? ,?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
//                        preparedStatement.setInt(1, all_book_id); // book_id
                    preparedStatement.setDate(2, Date.valueOf(date)); // title
                    preparedStatement.setDouble(3, value); // author
                    preparedStatement.setString(4, name);
                    preparedStatement.setString(5, Category);
                    preparedStatement.setBoolean(6, is_yearly);
                    preparedStatement.setBoolean(7, is_monthly);
                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int lastInsertedId = generatedKeys.getInt(1);
                        System.out.println("Reminder inserted with ID: " + lastInsertedId);
                        load_Reminder();


                    }
                    System.out.println("Rows inserted: " + rowsInserted);


                } catch (SQLException f) {
                    System.err.println("Error executing SQL query: " + f.getMessage());


                }
            } catch (Exception e) {
                System.out.println("re enter the value ");
            }
            try {
                break;

            } catch (Exception h) {
                System.out.println("Exiting on wrong Input");
            }


        }

    }

    void insert_Wishlist(String item_name, double item_price, double rate, double amount_saved, LocalDate last_calculated_date) {

        ////////////////////////////decleration///////////////////////////////////////////////


        PreparedStatement preparedStatement = null;
        /////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////getting input ///////////////////////////////////////
        while (true) {
            try {

                try {
                    String insertSql = "INSERT INTO Wishlist(Id,item_name,item_price,rate,amount_saved,last_calculated_date,Username) VALUES (?, ?, ?, ?, ? ,? ,?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
//                        preparedStatement.setInt(1, all_book_id); // book_id
                    preparedStatement.setString(2, item_name); // title
                    preparedStatement.setDouble(3, item_price); // author
                    preparedStatement.setDouble(4, rate);
                    preparedStatement.setDouble(5, amount_saved);
                    preparedStatement.setDate(6, Date.valueOf(last_calculated_date));
                    preparedStatement.setString(7, HelloController.getUsername_to_pass());
                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int lastInsertedId = generatedKeys.getInt(1);
                        System.out.println("Wishlist inserted with ID: " + lastInsertedId);
                        load_Wishlist();

                    }
                    System.out.println("Rows inserted: " + rowsInserted);


                } catch (SQLException f) {
                    System.err.println("Error executing SQL query: " + f.getMessage());


                }
            } catch (Exception e) {
                System.out.println("re enter the value ");
            }
            try {
                break;

            } catch (Exception h) {
                System.out.println("Exiting on wrong Input");
            }


        }

    }


}