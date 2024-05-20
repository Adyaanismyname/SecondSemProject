package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Expenditure extends  Transaction{
    private static int E_IDgenerator=0 ;
    private String category;
    public static ArrayList<Expenditure> ExpenditureList = new ArrayList<>();

    public Expenditure(int id,LocalDate date,double value,String Category,String Username){
        super(id,date,value,Username);
        this.category=Category;
        main.setIdForTable("Expenditure");
        System.out.println(E_IDgenerator);
    }

    public Expenditure(String category, LocalDate date, double value){


        super(E_IDgenerator, date, value,HelloController.getUsername_to_pass());
        this.category = category;
        ExpenditureList.add(this);
        main.UpdateLatestIdForClass("L_Expenditure_id",E_IDgenerator+1,E_IDgenerator);
        E_IDgenerator++;


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
    public static void setId(int id){
        E_IDgenerator=id;
    }


    public static boolean deleteExpense(int ID) {

        //for loop iterates through each expense
        for (int i = 0; i < ExpenditureList.size(); i++) {

            if (ExpenditureList.get(i).getID() == ID) {

                ExpenditureList.remove(i);

                //successfully deleted
                return true;
            }
        }

        //this means no such expense with the ID
        return false;
    }

    public static double getMonthExpense(int number, int monthYear){

        double monthlyExpense = 0;


        for(Expenditure expenditure : ExpenditureList){

            int month = expenditure.getDate().getMonthValue();
            int year = expenditure.getDate().getYear();

            boolean isCurrent = (number == month) && (monthYear == year);

            if (isCurrent){
                monthlyExpense += expenditure.getValue();
            }
        }

        return monthlyExpense;

    }

}