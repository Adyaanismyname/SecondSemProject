package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reminder extends Transaction {
    private static int R_IDgenerator ;

    private String name;
    private String category;
    private boolean yearly = false;
    private boolean monthly = false;

    // Lists to store reminders
    public static ArrayList<Reminder> reminderList = new ArrayList<>();
    public static ArrayList<Reminder> upcomingReminders = new ArrayList<>();
    public static ArrayList<Reminder> showing_reminders = new ArrayList<>();

    // Constructor with all parameters
    public Reminder(int id, String name, String category, LocalDate date, double value, boolean monthly, boolean yearly, String Username) {
        super(id, date, value, Username);
        this.name = name;
        this.category = category;
        this.monthly = monthly;
        this.yearly = yearly;
        main.setIdForTable("Reminder"); // Set ID for the Reminder table
        getUpcomingReminders(); // Update upcoming reminders list
    }

    // Constructor with basic parameters
    public Reminder(String name, String category, LocalDate date, double value, boolean monthly, boolean yearly) {
        super(R_IDgenerator, date, value, HelloController.getUsername_to_pass());
        this.name = name;
        this.category = category;
        this.monthly = monthly;
        this.yearly = yearly;
        reminderList.add(this); // Add reminder to the list

        // Check if reminder date is today or passed
        LocalDate five_days_later = LocalDate.now().plusDays(5);
        boolean thisdate = (date.isEqual(LocalDate.now()));
        boolean passed = (date.isBefore(LocalDate.now()));

        // Add reminder to showing_reminders if it's today or passed
        if (thisdate || passed) {
            showing_reminders.add(this);
        }

        R_IDgenerator++; // Increment ID generator
        getUpcomingReminders(); // Update upcoming reminders list
    }

    // Setter for ID generator
    public static void setId(int id) {
        R_IDgenerator = id;
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

    // Override toString method to provide string representation of Reminder object
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

    // Method to delete a reminder
    public static boolean deleteReminder(int ID) {
        for (int i = 0; i < upcomingReminders.size(); i++) {
            if (upcomingReminders.get(i).getID() == ID) {
                upcomingReminders.remove(i);
            }
        }

        for (int i = 0; i < reminderList.size(); i++) {
            if (reminderList.get(i).getID() == ID) {
                reminderList.remove(i);
                getUpcomingReminders(); // Update upcoming reminders list
                return true; // Successfully deleted
            }
        }
        return false; // No such reminder with the ID
    }

    // Method to delete a showing reminder
    public static Boolean deleteShowingReminders(int ID) {
        for (int i = 0; i < showing_reminders.size(); i++) {
            if (showing_reminders.get(i).getID() == ID) {
                showing_reminders.remove(i);
                return true; // Successfully deleted
            }
        }
        return false; // No such showing reminder with the ID
    }

    // Method to pay a reminder
    public static boolean payReminder(int ID) {
        for (int i = 0; i < reminderList.size(); i++) {
            Reminder reminder = reminderList.get(i);
            if (reminder.getID() == ID) {
                Expenditure expense = new Expenditure(reminder.category, LocalDate.now(), reminder.getValue());
                if (!(reminder.yearly || reminder.monthly)) {
                    deleteReminder(ID);
                    getUpcomingReminders(); // Update upcoming reminders list
                    return true; // Successfully paid
                } else if (reminder.isMonthly()) {
                    LocalDate current = reminder.getDate();
                    reminder.setDate(current.plusMonths(1));
                    getUpcomingReminders(); // Update upcoming reminders list
                    return true; // Successfully paid
                } else {
                    LocalDate current = reminder.getDate();
                    reminder.setDate(current.plusYears(1));
                    getUpcomingReminders(); // Update upcoming reminders list
                    return true; // Successfully paid
                }
            }
        }
        return false; // No reminder found with the given ID
    }

    // Method to check if reminder date has passed
    public boolean datePassed() {
        LocalDate currentDate = LocalDate.now();
        return (getDate().isBefore(currentDate) || getDate().isEqual(currentDate));
    }

    // Method to update the list of upcoming reminders
    public static void getUpcomingReminders() {
        upcomingReminders.clear(); // Clear the existing list of upcoming reminders

        LocalDate five_days_later = LocalDate.now().plusDays(5);

        // Iterate through each reminder in the reminderList
        for (Reminder reminder : reminderList) {
            LocalDate date = reminder.getDate();
            boolean upcoming = (date.isBefore(five_days_later));

            if (upcoming) {
                upcomingReminders.add(reminder); // Add the reminder to the upcoming reminders list
            }
        }
    }


    // Method to update the list of reminders that are to be shown
    public static void getShowingReminders() {
        showing_reminders.clear(); // Clear the existing list of showing reminders
        LocalDate five_days_later = LocalDate.now().plusDays(5);

        // Iterate through each reminder in the reminderList
        for (Reminder reminder : reminderList) {
            boolean thisdate = (reminder.getDate().isEqual(LocalDate.now()));
            boolean passed = (reminder.getDate().isBefore(LocalDate.now()));

            if (thisdate || passed) {
                showing_reminders.add(reminder); // Add the reminder to the showing reminders list
            }
        }
    }



}