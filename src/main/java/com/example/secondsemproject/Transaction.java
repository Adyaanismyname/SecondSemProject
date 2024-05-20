package com.example.secondsemproject;

import java.time.LocalDate;


//abstract class for money related transactions
public abstract class Transaction {

    private int ID;
    private String Username;
    private LocalDate date;
    private double value;


    // Constructor
    public Transaction(int ID, LocalDate date, double value,String Username) {
        this.ID = ID;
        this.Username=Username;
        this.date = date;
        setValue(value);
    }


    // Getters and Setters
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return this.ID;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {

        if (value >= 0) {
            this.value = value;
        }
        else {
            this.value = 0;
        }
    }

    @Override
    public String toString() {
        return "Transaction ID: " + ID + ", Date: " + date + ", Value: " + value;
    }

}