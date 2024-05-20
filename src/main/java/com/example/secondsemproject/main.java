package com.example.secondsemproject;

import java.sql.*;
import java.time.LocalDate;

import static com.example.secondsemproject.Expenditure.ExpenditureList;
import static com.example.secondsemproject.Income.incomeList;
import static com.example.secondsemproject.Reminder.reminderList;
import static com.example.secondsemproject.Wishlist.wishlists;
import static java.awt.AWTEventMulticaster.add;

public class main {
    static LocalDate date;
    static int Id;
    static double value;

    static String category;
    static String username;
    static String name;
    static String source;

    static Boolean is_monthly;
    static Boolean is_yearly;

    static ResultSet result;
    static PreparedStatement preparedStatement;

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
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

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
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

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
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

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

                wishlists.add(new Wishlist(Id, item_name, item_price, rate, last_cal_date, username));
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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
                    // Add more cases for additional tables if needed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            JDBCConnection.close();
        }
    }

    private static boolean isValidIdentifier(String identifier) {
        return identifier.matches("[A-Za-z_][A-Za-z0-9_]*");
    }


//    public static void UpdateLatestIdForClass(String targetClass, int newLatestId, int oldLatestId) {
//        if (!JDBCConnection.isConnectionValid()) {
//            JDBCConnection.getConnection();
//        }
//        if (targetClass == null || targetClass.isEmpty() || !isValidIdentifier(targetClass)) {
//            System.out.println("Invalid target class");
//            return;
//        }
//
//        try {
//            // Construct the SQL update statement for the specified class in the latest_id table
//            String sql = "UPDATE latest_id SET " + targetClass + " = ? WHERE " + targetClass + " = ?";
//            // Create a PreparedStatement with the SQL statement
//            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
//            preparedStatement.setInt(1, newLatestId); // Set the new latest ID
//            preparedStatement.setInt(2, oldLatestId); // Set the old latest ID
//
//            // Execute the update query
//            int rowsAffected = preparedStatement.executeUpdate();
////            JDBCConnection.close();
//
//
//            // Check the number of rows affected
//            if (rowsAffected > 0) {
//                System.out.println("ID updated successfully for " + targetClass);
//            } else {
//                System.out.println("No row found for the given old latest ID or class");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }





    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////




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
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    static void insert_Income(int Id, LocalDate date, double value, String source) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String insertSql = "INSERT INTO Income(Id, Date, value, source, Username) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            preparedStatement.setInt(1, Id);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, value);
            preparedStatement.setString(4, source);
            preparedStatement.setString(5, HelloController.getUsername_to_pass());

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    static void insert_Reminder(int Id, LocalDate date, double value, String name, String Category, Boolean is_yearly, Boolean is_monthly) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String insertSql = "INSERT INTO Reminder(Id, Date, value, name, Category, is_yearly, is_monthly, Username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            preparedStatement.setInt(1, Id);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, value);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, Category);
            preparedStatement.setBoolean(6, is_yearly);
            preparedStatement.setBoolean(7, is_monthly);
            preparedStatement.setString(8, HelloController.getUsername_to_pass());

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println("Error while inserting reminder into the database: " + e.getMessage());
            e.printStackTrace();
        }
    }


    static void insert_Wishlist(int Id, String item_name, double item_price, double rate, double amount_saved, LocalDate last_calculated_date) {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String insertSql = "INSERT INTO Wishlist(Id, item_name, item_price, rate, amount_saved, last_calculated_date, Username) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(insertSql)) {
            preparedStatement.setInt(1, Id);
            preparedStatement.setString(2, item_name);
            preparedStatement.setDouble(3, item_price);
            preparedStatement.setDouble(4, rate);
            preparedStatement.setDouble(5, amount_saved);
            preparedStatement.setDate(6, Date.valueOf(last_calculated_date));
            preparedStatement.setString(7, HelloController.getUsername_to_pass());

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }



    static void deleteWishlistfromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String deleteSql = "DELETE FROM Wishlist WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }


    static void deleteIncomefromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String deleteSql = "DELETE FROM Income WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }


    static void deleteExpenditurefromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String deleteSql = "DELETE FROM Expenditure WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }



    static void deleteReminderfromdb() {
        if (!JDBCConnection.isConnectionValid()) {
            JDBCConnection.getConnection();
        }

        String deleteSql = "DELETE FROM Reminder WHERE Username = ?";
        try (PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, HelloController.getUsername_to_pass());

            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static void Load_into_databsae (){
        try {

            Insert_Wishlist_db();
            Insert_Income_db();
            Insert_Expenditure_db();
            Insert_Reminder_db();
        }
        catch (RuntimeException e){
            System.out.println(e+"There is an error in loading");
        }
        for(Reminder x :reminderList){
            System.out.println(x.getCategory()+x.getDate()+x.getID());
        }
        for(Income i :incomeList){
            System.out.println(i.getSource()+"  "+i.getDate());
        }
        System.out.println("the data has been inserted into database");
        JDBCConnection.close();


    }

    static void  delete_Previous_Data(){

        deleteExpenditurefromdb();
        deleteIncomefromdb();
        deleteReminderfromdb();
        deleteWishlistfromdb();
        System.out.println("the data has been deleted from database");
        for(Reminder x:reminderList){
            System.out.println(x.getCategory()+x.getDate());
        }

    }

    static void load_Data_AL(){
        load_Wishlist();
        load_Reminder();
        load_Income();
        load_Expenditure();
        System.out.println("the data has been loaded");
        for(Reminder x : reminderList){
            System.out.println(x.getCategory()+x.getDate());
        }
        System.out.println("nothing printed");
//        System.out.println(Reminder.getID());

    }






    ////////////////////////////////////////////////////////////////////////////////////////////////////////////










    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    static void Insert_Wishlist_db() {
        for (Wishlist wishlist : wishlists) {
            insert_Wishlist(wishlist.getID(), wishlist.getItem_name(), wishlist.getItem_price(), wishlist.getRate(), wishlist.getAmountSaved(), wishlist.getLastCalculationDate());
        }

    }

    static void Insert_Expenditure_db() {
        for (Expenditure E : ExpenditureList) {
            insert_Expenditure(E.getID(), E.getDate(), E.getValue(), E.getCategory());
        }
    }

    static void Insert_Income_db() {
        for (Income I : incomeList) {
            insert_Income(I.getID(), I.getDate(), I.getValue(), I.getSource());
        }
    }
    static void Insert_Reminder_db() {
        for (Reminder R : reminderList) {
            insert_Reminder(R.getID(),R.getDate(),R.getValue(),R.getName(),R.getCategory(),R.isYearly(),R.isMonthly());
        }
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}