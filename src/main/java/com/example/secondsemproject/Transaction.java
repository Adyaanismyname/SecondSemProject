package com.example.secondsemproject;

import java.time.LocalDate;

public abstract class Transaction {

    private int ID;
    private LocalDate date;
    private double value;

    // Constructor
    public Transaction(int ID, LocalDate date, double value) {
        this.ID = ID;
        this.date = date;
        setValue(value);
    }


    // Getters and Setters
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return ID;
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
