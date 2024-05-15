package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Expenditure extends  Transaction{
    private static int E_IDgenerator = 1;
    private String category;
    public static ArrayList<Expenditure> ExpenditureList = new ArrayList<>();

    public Expenditure(String category, LocalDate date, double value){

        super(E_IDgenerator, date, value);
        this.category = category;

        E_IDgenerator++;
        ExpenditureList.add(this);

    }

    // Getters and setters for category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    // ToString Method
    @Override
    public String toString() {
        return "Expenditure ID: " + getID() + ", Category: " + category + ", Date: " + getDate() + ", Value: " + getValue();
    }


    public static double getTotal(){
        double total = 0;

        for(Expenditure spent  : ExpenditureList){
            total+= spent.getValue();
        }
        return total;
    }



}
