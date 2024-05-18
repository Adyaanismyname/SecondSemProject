package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Income extends Transaction {
    private static int I_IDgenerator;
    private String source;
    private String Username;
    public static ArrayList<Income> incomeList = new ArrayList<>();


    public Income(String source, LocalDate date, double value) {


        super(I_IDgenerator, date, value, HelloController.getUsername_to_pass());
        this.source = source;
        main.UpdateLatestIdForClass("L_Income_id",I_IDgenerator+1,I_IDgenerator);

        I_IDgenerator++;
        incomeList.add(this);



    }

    public Income(int Id, String source, LocalDate date, double value, String Username) {

        super(Id, date, value, Username);
        this.source = source;
        main.setIdForTable("Income");

    }
    public static void setId(int id){
        I_IDgenerator=id;
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


    public static double getTotal() {
        double total = 0;

        for (Income income : incomeList) {
            total += income.getValue();
        }
        return total;
    }


    public static boolean deleteIncome(int ID) {

        //for loop iterates through each income
        for (int i = 0; i < incomeList.size(); i++) {

            if (incomeList.get(i).getID() == ID) {

                incomeList.remove(i);

                //successfully deleted
                return true;
            }
        }

        //this means no such income with the ID
        return false;
    }

    public static double getMonthIncome(int number){

        double monthlyIncome = 0;
        int currentYear = LocalDate.now().getYear();


        for(Income income : Income.incomeList){

            int month = income.getDate().getMonthValue();
            int year = income.getDate().getYear();

            boolean isCurrent = (number == month) && (currentYear == year);

            if (isCurrent){
                monthlyIncome += income.getValue();
            }
        }

        return monthlyIncome;

    }


}

