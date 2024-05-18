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
    public static ArrayList<Reminder> upcomingReminders = new ArrayList<>();

    public Reminder(int id,String name, String category, LocalDate date, double value, boolean monthly, boolean yearly,String Username) {
        super(id, date, value,HelloController.getUsername_to_pass());
        this.name = name;
        this.category = category;
        this.monthly = monthly;
        this.yearly = yearly;



    }


    // Constructor
    public Reminder(String name, String category, LocalDate date, double value, boolean monthly, boolean yearly) {
        main.setIdForTable("Reminder");
        super(R_IDgenerator, date, value,HelloController.getUsername_to_pass());
        this.name = name;
        this.category = category;
        this.monthly = monthly;
        this.yearly = yearly;
        reminderList.add(this);
        main.UpdateLatestIdForClass("L_Reminder_id",R_IDgenerator+1,R_IDgenerator);
        R_IDgenerator++;
    }
    public static void setId(int id){
        R_IDgenerator=id;
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
    public static boolean deleteReminder (int ID){

        //for loop iterates through each reminder in the upcoming section
        for (int i = 0; i < upcomingReminders.size(); i++){

            if (upcomingReminders.get(i).getID() == ID){

                upcomingReminders.remove(i);
            }
        }

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

    public static void payReminder(int ID){

        //for loop iterates through each reminder
        for (int i = 0; i < reminderList.size(); i++){

            Reminder reminder = reminderList.get(i);

            if (reminder.getID() == ID){

                Expenditure expense = new Expenditure(reminder.category, LocalDate.now(), reminder.getValue());

                if (!(reminder.yearly || reminder.monthly)) {
                    deleteReminder(ID);
                }
            }
        }
    }


    public boolean datePassed() {

        LocalDate currentDate = LocalDate.now();

        return !(getDate().isBefore(currentDate) || getDate().isEqual(currentDate));
    }

    public static void getUpcomingReminders(){

        LocalDate five_days_later = LocalDate.now().plusDays(5);

        //for loop iterates through each reminder
        for (Reminder reminder : reminderList) {

            boolean upcoming = reminder.getDate().isBefore(five_days_later);

            if (upcoming) {

                upcomingReminders.add(reminder);

            }
        }
    }


}