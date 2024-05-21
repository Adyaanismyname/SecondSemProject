package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

// Income class extends Transaction class
public class Income extends Transaction {
    private static int I_IDgenerator;
    private String source;
    public static ArrayList<Income> incomeList = new ArrayList<>();

    // Constructor to initialize an Income object with source, date, and value
    public Income(String source, LocalDate date, double value) {
        super(I_IDgenerator, date, value, HelloController.getUsername_to_pass());
        this.source = source;
        incomeList.add(this);
        I_IDgenerator++;
    }

    // Constructor to initialize an Income object with provided ID, source, date, value, and username
    public Income(int Id, String source, LocalDate date, double value, String Username) {
        super(Id, date, value, Username);
        this.source = source;
        main.setIdForTable("Income");
    }

    // Method to set the ID generator
    public static void setId(int id){
        I_IDgenerator=id;
    }

    // Getter method for source
    public String getSource() {
        return source;
    }

    // Setter method for source
    public void setSource(String source) {
        this.source = source;
    }

    // Method to represent Income object as a String
    @Override
    public String toString() {
        return "Income ID: " + getID() + ", Source: " + source + ", Date: " + getDate() + ", Value: " + getValue();
    }

    // Method to calculate total income
    public static double getTotal() {
        double total = 0;

        for (Income income : incomeList) {

            // Adding the value of each income object to the total
            total += income.getValue();
        }
        return total;
    }

    // Method to delete an income object based on its ID
    public static boolean deleteIncome(int ID) {

        for (int i = 0; i < incomeList.size(); i++) {
            if (incomeList.get(i).getID() == ID) {

                // Removing the income object from incomeList
                incomeList.remove(i);

                // Successfully deleted
                return true;
            }
        }

        // No income found with the provided ID
        return false;
    }

    // Method to calculate total income for a specific month and year
    public static double getMonthIncome(int number, int monthYear){
        double monthlyIncome = 0;

        for(Income income : Income.incomeList){
            // Extracting month and year from the income object's date
            int month = income.getDate().getMonthValue();
            int year = income.getDate().getYear();

            // Checking if the income object's date matches the provided month and year
            boolean isCurrent = (number == month) && (monthYear == year);

            if (isCurrent){
                // Adding the value of the income object to the monthlyIncome
                monthlyIncome += income.getValue();
            }
        }

        return monthlyIncome;
    }
}
