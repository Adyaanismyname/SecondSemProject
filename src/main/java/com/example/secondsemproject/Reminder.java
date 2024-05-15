package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reminder extends Transaction {
    private static int R_IDgenerator = 1;
    private String name;
    private String category;
    private boolean yearly = false;
    private boolean monthly = false;

    public static ArrayList<Reminder>  reminderList= new ArrayList<>();


    // Constructor
    public Reminder(String name, String category, LocalDate date, double value, boolean monthly, boolean yearly) {
        super(R_IDgenerator, date, value);

        this.name = name;
        this.category = category;
        this.monthly = monthly;
        this.yearly = yearly;

        reminderList.add(this);

        R_IDgenerator++;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public boolean isYearly() {
        return yearly;
    }

    public void setYearly(boolean yearly) {
        this.yearly = yearly;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }


    @Override
    public String toString() {
        String repetition = "";
        if (yearly) {
            repetition = "Yearly";
        } else if (monthly) {
            repetition = "Monthly";
        }

        return "Reminder ID: " + this.getID() +
                "\nName: " + name +
                "\nCategory: " + category +
                "\nDate: " + getDate() +
                "\nRepetition: " + repetition;
    }

    //REMOVE REMINDER
    public boolean deleteReminder (int ID){

        //for loop iterates through each reminder
        for (int i = 0; i < reminderList.size(); i++){

            if (reminderList.get(i).getID() == ID){

                reminderList.remove(i);

                //successfully deleted
                return true;
            }
        }

        //this means no such reminder with the ID
        return false;
    }

    public void payReminder(){

        Expenditure expense = new Expenditure(this.category, LocalDate.now(), getValue());

        if (!(this.yearly || this.monthly)){
            deleteReminder(getID());
        }
    }


    public boolean datePassed() {

        LocalDate currentDate = LocalDate.now();

        return !(getDate().isBefore(currentDate) || getDate().isEqual(currentDate));
    }


}
