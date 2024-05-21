package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

// Expenditure class extending the Transaction class
public class Expenditure extends Transaction {
    private static int E_IDgenerator ; // Static variable to generate unique ID for each expenditure
    private String category; // Category of the expenditure
    public static ArrayList<Expenditure> ExpenditureList = new ArrayList<>(); // List to store all expenditures



    // Constructor with parameters
    public Expenditure(int id, LocalDate date, double value, String Category, String Username) {
        super(id, date, value, Username);
        this.category = Category;
        main.setIdForTable("Expenditure");
        System.out.println(E_IDgenerator);
    }


    // Constructor for creating a new expenditure
    public Expenditure(String category, LocalDate date, double value) {
        super(E_IDgenerator, date, value, HelloController.getUsername_to_pass());
        this.category = category;
        ExpenditureList.add(this);
        E_IDgenerator++;
    }


    // Getter for category
    public String getCategory() {
        return category;
    }

    // Setter for category
    public void setCategory(String category) {
        this.category = category;
    }


    // ToString method
    @Override
    public String toString() {
        return "Expenditure ID: " + getID() + ", Category: " + category + ", Date: " + getDate() + ", Value: " + getValue();
    }


    // Method to get total value of all expenditures
    public static double getTotal() {
        double total = 0;
        for (Expenditure spent : ExpenditureList) {
            total += spent.getValue();
        }
        return total;
    }


    // Method to set the ID generator to a specific value
    public static void setId(int id) {
        E_IDgenerator = id;
    }



    // Method to delete an expenditure by its ID
    public static boolean deleteExpense(int ID) {
        for (int i = 0; i < ExpenditureList.size(); i++) {
            if (ExpenditureList.get(i).getID() == ID) {
                ExpenditureList.remove(i);
                return true; // Successfully deleted
            }
        }
        return false; // No such expense with the ID
    }



    // Method to get total expenditures for a specific month and year
    public static double getMonthExpense(int month, int year) {
        double monthlyExpense = 0;
        for (Expenditure expenditure : ExpenditureList) {
            int expMonth = expenditure.getDate().getMonthValue();
            int expYear = expenditure.getDate().getYear();
            boolean isCurrent = (month == expMonth) && (year == expYear);
            if (isCurrent) {
                monthlyExpense += expenditure.getValue();
            }
        }
        return monthlyExpense;
    }
}
