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
        ExpenditureList.clear();
        try {
            String query = "SELECT * FROM Expenditure WHERE Username = "+HelloController.getUsername_to_pass();
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
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

    static void load_Reminder() {
        reminderList.clear();
        try {
            String query = "SELECT * FROM Reminder where Username =" + HelloController.getUsername_to_pass();
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
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

    static void load_Income() {
        incomeList.clear();

        try {
            String query = "SELECT * FROM Income WHERE Username = "+HelloController.getUsername_to_pass();
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

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

    static void load_Wishlist() {
        wishlists.clear();
        try {
            String query = "SELECT * FROM Wishlist WHERE Username = "+HelloController.getUsername_to_pass();
            preparedStatement = JDBCConnection.connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
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



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    static void insert_Expenditure(int Id, LocalDate date, double value, String Category) {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "INSERT INTO Expenditure(Id, Date, value, Category, Username) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, Id); // book_id
                    preparedStatement.setDate(2, Date.valueOf(date));
                    preparedStatement.setDouble(3, value);
                    preparedStatement.setString(4, Category);
                    preparedStatement.setString(5, HelloController.getUsername_to_pass());

                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
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

    static void insert_Income(int Id, LocalDate date, double value, String source) {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "INSERT INTO Income(Id, Date, value, source, Username) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1,Id);
                    preparedStatement.setDate(2, Date.valueOf(date));
                    preparedStatement.setDouble(3, value);
                    preparedStatement.setString(4, source);
                    preparedStatement.setString(5, HelloController.getUsername_to_pass());
                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
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

    static void insert_Reminder(int Id, LocalDate date, double value, String name, String Category, Boolean is_yearly, Boolean is_monthly) {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "INSERT INTO Reminder(Id,Date,value,name,Category,is_yearly,is_monthly) VALUES (?, ?, ?, ?, ? ,? ,?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1,Id); // book_id
                    preparedStatement.setDate(2, Date.valueOf(date)); // title
                    preparedStatement.setDouble(3, value); // author
                    preparedStatement.setString(4, name);
                    preparedStatement.setString(5, Category);
                    preparedStatement.setBoolean(6, is_yearly);
                    preparedStatement.setBoolean(7, is_monthly);
                    // Executing the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
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

    static void insert_Wishlist(int Id, String item_name, double item_price, double rate, double amount_saved, LocalDate last_calculated_date) {

        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "INSERT INTO Wishlist(Id,item_name,item_price,rate,amount_saved,last_calculated_date,Username) VALUES (?, ?, ?, ?, ? ,? ,?)";
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, Id);
                    preparedStatement.setString(2, item_name); // title
                    preparedStatement.setDouble(3, item_price); // author
                    preparedStatement.setDouble(4, rate);
                    preparedStatement.setDouble(5, amount_saved);
                    preparedStatement.setDate(6, Date.valueOf(last_calculated_date));
                    preparedStatement.setString(7, HelloController.getUsername_to_pass());
                    int rowsInserted = preparedStatement.executeUpdate();
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


    static void deleteWishlistfromdb() {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "DELETE FROM Wishlist WHERE Username =" + HelloController.getUsername_to_pass();
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    int rowsInserted = preparedStatement.executeUpdate();
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

    static void deleteIncomefromdb() {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "DELETE FROM Income WHERE Username =" + HelloController.getUsername_to_pass();
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    int rowsInserted = preparedStatement.executeUpdate();
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

    static void deleteExpenditurefromdb() {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {

                try {
                    String insertSql = "DELETE FROM Expenditure WHERE Username =" + HelloController.getUsername_to_pass();
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    int rowsInserted = preparedStatement.executeUpdate();
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


    static void deleteReminderfromdb() {
        PreparedStatement preparedStatement = null;
        while (true) {
            try {
                try {
                    String insertSql = "DELETE FROM Reminder WHERE Username =" + HelloController.getUsername_to_pass();
                    preparedStatement = JDBCConnection.connection.prepareStatement(insertSql);
                    int rowsInserted = preparedStatement.executeUpdate();
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static void Load_into_databsae(){

        Insert_Wishlist_db();
        Insert_Income_db();
        Insert_Expenditure_db();
        Insert_Reminder_db();

    }

     static void  delete_Previous_Data(){

        deleteExpenditurefromdb();
        deleteIncomefromdb();
        deleteReminderfromdb();
        deleteWishlistfromdb();

    }

    static void load_Data_AL(){
        load_Wishlist();
        load_Reminder();
        load_Income();
        load_Expenditure();

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
