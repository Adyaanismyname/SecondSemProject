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
        I_IDgenerator = num; // why this ssssssssssss


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

    public static boolean deleteIncome (int ID){

        //for loop iterates through each income
        for (int i = 0; i < incomeList.size(); i++){

            if (incomeList.get(i).getID() == ID){

                incomeList.remove(i);

                //successfully deleted
                return true;
            }
        }

        //this means no such income with the ID
        return false;
    }
}
