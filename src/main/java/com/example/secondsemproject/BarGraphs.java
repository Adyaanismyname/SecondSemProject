package com.example.secondsemproject;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.ArrayList;

public class BarGraphs {


    public static void displayBarChart(BarChart<String, Number> barChart){


        int startingMonth = getStartingMonth();

        double [] monthlyIncome = new double[5];
        double [] monthlyExpense = new double[5];

        for (int i = 0; i < 5; i++){

            monthlyIncome[i] = Income.getMonthIncome(startingMonth + i);
            monthlyExpense[i] = Expenditure.getMonthExpense(startingMonth + i);

        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();


        barChart.setTitle("Monthly Transactions");
        xAxis.setLabel("Month");
        yAxis.setLabel("Amount");
        barChart.setCategoryGap(10);




        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        incomeSeries.getData().addAll(
                new XYChart.Data<>(getMonthName(startingMonth), monthlyIncome[0]),
                new XYChart.Data<>(getMonthName(startingMonth+1), monthlyIncome[1]),
                new XYChart.Data<>(getMonthName(startingMonth+2), monthlyIncome[2]),
                new XYChart.Data<>(getMonthName(startingMonth+3), monthlyIncome[3],
                new XYChart.Data<>(getMonthName(startingMonth+4), monthlyIncome[4]))
        );

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expense");
        expenseSeries.getData().addAll(
                new XYChart.Data<>(getMonthName(startingMonth), monthlyExpense[0]),
                new XYChart.Data<>(getMonthName(startingMonth+1), monthlyExpense[1]),
                new XYChart.Data<>(getMonthName(startingMonth+2), monthlyExpense[2]),
                new XYChart.Data<>(getMonthName(startingMonth+3), monthlyExpense[3],
                new XYChart.Data<>(getMonthName(startingMonth+4), monthlyExpense[4]))
        );

        XYChart.Series<String, Number> balanceSeries = new XYChart.Series<>();
        balanceSeries.setName("Balance");
        balanceSeries.getData().addAll(
                new XYChart.Data<>(getMonthName(startingMonth), monthlyIncome[0]-monthlyExpense[0]),
                new XYChart.Data<>(getMonthName(startingMonth+1), monthlyIncome[1]-monthlyExpense[1]),
                new XYChart.Data<>(getMonthName(startingMonth+2), monthlyIncome[2]-monthlyExpense[2]),
                new XYChart.Data<>(getMonthName(startingMonth+3), monthlyIncome[3]-monthlyExpense[3],
                new XYChart.Data<>(getMonthName(startingMonth+4), monthlyIncome[4]-monthlyExpense[4]))
        );

        barChart.getData().addAll(incomeSeries, expenseSeries, balanceSeries);



    }




    public static String getMonthName(int num) {
        String monthName;

        switch (num) {
            case 1:
                monthName = "Jan";
                break;
            case 2:
                monthName = "Feb";
                break;
            case 3:
                monthName = "Mar";
                break;
            case 4:
                monthName = "Apr";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "Jun";
                break;
            case 7:
                monthName = "Jul";
                break;
            case 8:
                monthName = "Aug";
                break;
            case 9:
                monthName = "Sep";
                break;
            case 10:
                monthName = "Oct";
                break;
            case 11:
                monthName = "Nov";
                break;
            case 12:
                monthName = "Dec";
                break;
            default:
                monthName = "Invalid Month";
                break;
        }
        return monthName;
    }

    public static int getStartingMonth(){

        int currentMonth = LocalDate.now().getDayOfMonth();
        int startingMonth;

        switch (currentMonth - 5){
            case 0:
                startingMonth = 12;
                break;

            case (-1):
                startingMonth = 11;
                break;

            case (-2):
                startingMonth = 10;
                break;

            case (-3):
                startingMonth = 9;
                break;

            case (-4):
                startingMonth = 8;
                break;

            default:
                startingMonth = currentMonth - 5;
                break;
        }

        return currentMonth;
    }

}
