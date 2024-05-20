package com.example.secondsemproject;

import java.sql.*;
import java.time.LocalDate;

import static com.example.secondsemproject.Expenditure.ExpenditureList;
import static com.example.secondsemproject.Income.incomeList;
import static com.example.secondsemproject.Reminder.reminderList;
import static com.example.secondsemproject.Wishlist.wishlists;

public class main {

    static ResultSet result;
    static PreparedStatement preparedStatement;

    // Method to load expenditure data from the database
    static void load_Expenditure() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }
        ExpenditureList.clear();
        try {
            String query = "SELECT * FROM Expenditure WHERE Username = ?";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            preparedStatement.setString(1, HelloController.getUsername_to_pass());
            result = preparedStatement.executeQuery();
            while (result.next()) {
                int Id = result.getInt("Id");
                LocalDate date = result.getDate("Date").toLocalDate();
                double value = result.getDouble("value");
                String category = result.getString("Category");
                String username = result.getString("Username");

                ExpenditureList.add(new Expenditure(Id, date, value, category, username));
            }
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to load reminder data from the database
    static void load_Reminder() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }
        reminderList.clear();
        try {
            String query = "SELECT * FROM Reminder WHERE Username = ?";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            preparedStatement.setString(1, HelloController.getUsername_to_pass());
            result = preparedStatement.executeQuery();
            while (result.next()) {
                int Id = result.getInt("Id");
                LocalDate date = result.getDate("Date").toLocalDate();
                double value = result.getDouble("value");
                String category = result.getString("Category");
                boolean is_yearly = result.getBoolean("is_yearly");
                boolean is_monthly = result.getBoolean("is_monthly");
                String username = result.getString("Username");
                String name = result.getString("name");

                reminderList.add(new Reminder(Id, name, category, date, value, is_monthly, is_yearly, username));
            }
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to load income data from the database
    static void load_Income() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }
        incomeList.clear();
        try {
            String query = "SELECT * FROM Income WHERE Username = ?";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            preparedStatement.setString(1, HelloController.getUsername_to_pass());
            result = preparedStatement.executeQuery();
            while (result.next()) {
                int Id = result.getInt("Id");
                LocalDate date = result.getDate("Date").toLocalDate();
                double value = result.getDouble("value");
                String source = result.getString("source");
                String username = result.getString("Username");

                incomeList.add(new Income(Id, source, date, value, username));
            }
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to load wishlist data from the database
    static void load_Wishlist() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }
        wishlists.clear();
        try {
            String query = "SELECT * FROM Wishlist WHERE Username = ?";
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            preparedStatement.setString(1, HelloController.getUsername_to_pass());
            result = preparedStatement.executeQuery();
            while (result.next()) {
                int Id = result.getInt("Id");
                LocalDate last_cal_date = result.getDate("last_calculated_date").toLocalDate();
                double amount_saved = result.getDouble("amount_saved");
                double item_price = result.getDouble("item_price");
                double rate = result.getDouble("rate");
                String username = result.getString("Username");
                String item_name = result.getString("item_name");

                wishlists.add(new Wishlist(Id, item_name, item_price, rate, last_cal_date, username, amount_saved));
            }
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to set the ID for the table
    public static void setIdForTable(String tableName) {
        if (tableName == null || tableName.isEmpty() || !isValidIdentifier(tableName)) {
            System.out.println("Invalid table name");
            return;
        }

        try {
            String query = "SELECT Id FROM " + tableName + " ORDER BY Id DESC LIMIT 1";
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            int latestId = -1;  // Initialize latestId to -1

            if (resultSet.next()) {
                latestId = resultSet.getInt("Id");
            }

            // Update the stored ID in your Java program for the specified table
            switch (tableName) {
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
                    System.out.println("Failed to update ID for table: " + tableName);
            }

        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            e.printStackTrace();
        }
    }

    // Method to check if a string is a valid identifier
    private static boolean isValidIdentifier(String identifier) {
        return identifier.matches("[A-Za-z_][A-Za-z0-9_]*");
    }

    // Method to insert expenditure data into the database
    static void insert_Expenditure(int Id, LocalDate date, double value, String Category) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String insertSql = "INSERT INTO Expenditure(Id, Date, value, Category, Username) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            preparedStatement.setInt(1, Id);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, value);
            preparedStatement.setString(4, Category);
            preparedStatement.setString(5, HelloController.getUsername_to_pass());

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);

        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }


    // Method to insert income data into the database
    static void insert_Income(int Id, LocalDate date, double value, String source) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to insert income data
        String insertSql = "INSERT INTO Income(Id, Date, value, source, Username) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            // Setting parameters for the prepared statement
            preparedStatement.setInt(1, Id);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, value);
            preparedStatement.setString(4, source);
            preparedStatement.setString(5, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to insert reminder data into the database
    static void insert_Reminder(int Id, LocalDate date, double value, String name, String Category, Boolean is_yearly, Boolean is_monthly) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to insert reminder data
        String insertSql = "INSERT INTO Reminder(Id, Date, value, name, Category, is_yearly, is_monthly, Username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            // Setting parameters for the prepared statement
            preparedStatement.setInt(1, Id);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, value);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, Category);
            preparedStatement.setBoolean(6, is_yearly);
            preparedStatement.setBoolean(7, is_monthly);
            preparedStatement.setString(8, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);

        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error while inserting reminder into the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to insert wishlist data into the database
    static void insert_Wishlist(int Id, String item_name, double item_price, double rate, double amount_saved, LocalDate last_calculated_date) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to insert wishlist data
        String insertSql = "INSERT INTO Wishlist(Id, item_name, item_price, rate, amount_saved, last_calculated_date, Username) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            // Setting parameters for the prepared statement
            preparedStatement.setInt(1, Id);
            preparedStatement.setString(2, item_name);
            preparedStatement.setDouble(3, item_price);
            preparedStatement.setDouble(4, rate);
            preparedStatement.setDouble(5, amount_saved);
            preparedStatement.setDate(6, Date.valueOf(last_calculated_date));
            preparedStatement.setString(7, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);

        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to delete wishlist data from the database
    static void deleteWishlistfromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to delete wishlist data
        String deleteSql = "DELETE FROM Wishlist WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            // Setting parameter for the prepared statement
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to delete income data from the database
    static void deleteIncomefromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to delete income data
        String deleteSql = "DELETE FROM Income WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            // Setting parameter for the prepared statement
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to delete expenditure data from the database
    static void deleteExpenditurefromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to delete expenditure data
        String deleteSql = "DELETE FROM Expenditure WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            // Setting parameter for the prepared statement
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to delete reminder data from the database
    static void deleteReminderfromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        // SQL query to delete reminder data
        String deleteSql = "DELETE FROM Reminder WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            // Setting parameter for the prepared statement
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            // Executing the SQL query
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            // Error handling for SQL query execution failure
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Method to load data into the database
    static void Load_into_databsae (){
        try {
            // Attempting to insert wishlist, income, expenditure, and reminder data into the database
            Insert_Wishlist_db();
            Insert_Income_db();
            Insert_Expenditure_db();
            Insert_Reminder_db();
        }
        catch (RuntimeException e){
            // Handling runtime exceptions during loading
            System.out.println(e + " There is an error in loading");
        }
        // Printing reminder data after loading
        for(Reminder x : reminderList){
            System.out.println(x.getCategory() + x.getDate() + x.getID());
        }
        // Printing income data after loading
        for(Income i : incomeList){
            System.out.println(i.getSource() + "  " + i.getDate());
        }
        System.out.println("the data has been inserted into database");
        // Closing JDBC connection after loading
        JDBCConnection.close();
    }

    // Method to delete previous data from the database
    static void delete_Previous_Data(){
        // Deleting expenditure, income, reminder, and wishlist data from the database
        deleteExpenditurefromdb();
        deleteIncomefromdb();
        deleteReminderfromdb();
        deleteWishlistfromdb();
        System.out.println("the data has been deleted from database");
        // Printing reminder data after deletion
        for(Reminder x:reminderList){
            System.out.println(x.getCategory() + x.getDate());
        }
    }

    // Method to load data from the database
    static void load_Data_AL(){
        // Loading wishlist, reminder, income, and expenditure data
        load_Wishlist();
        load_Reminder();
        load_Income();
        load_Expenditure();
        System.out.println("the data has been loaded");
        // Printing reminder data after loading
        for(Reminder x : reminderList){
            System.out.println(x.getCategory() + x.getDate());
        }
    }

    // Method to insert wishlist data into the database
    static void Insert_Wishlist_db() {
        for (Wishlist wishlist : wishlists) {
            insert_Wishlist(wishlist.getID(), wishlist.getItem_name(), wishlist.getItem_price(), wishlist.getRate(), wishlist.getAmountSaved(), wishlist.getLastCalculationDate());
        }
    }

    // Method to insert expenditure data into the database
    static void Insert_Expenditure_db() {
        for (Expenditure E : ExpenditureList) {
            insert_Expenditure(E.getID(), E.getDate(), E.getValue(), E.getCategory());
        }
    }

    // Method to insert income data into the database
    static void Insert_Income_db() {
        for (Income I : incomeList) {
            insert_Income(I.getID(), I.getDate(), I.getValue(), I.getSource());
        }
    }

    // Method to insert reminder data into the database
    static void Insert_Reminder_db() {
        for (Reminder R : reminderList) {
            insert_Reminder(R.getID(),R.getDate(),R.getValue(),R.getName(),R.getCategory(),R.isYearly(),R.isMonthly());
        }
    }
}
