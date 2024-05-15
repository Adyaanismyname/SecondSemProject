package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Income extends Transaction{
    private static int I_IDgenerator;
    private String source;
    public static ArrayList<Income> incomeList = new ArrayList<>();


    public Income(String source, LocalDate date, double value){

        super(I_IDgenerator, date, value);
        this.source = source;

        I_IDgenerator++;
        incomeList.add(this);
        int num =5;
        I_IDgenerator = num;


    }

    // Getters and setters for source
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    @Override
    public String toString() {
        return "Income ID: " + getID() + ", Source: " + source + ", Date: " + getDate() + ", Value: " + getValue();
    }



    public static double getTotal(){
        double total = 0;

        for(Income income  : incomeList){
            total+= income.getValue();
        }
        return total;
    }

}
