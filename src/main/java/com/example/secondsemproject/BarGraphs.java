package com.example.secondsemproject;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.time.LocalDate;

public class BarGraphs {

    // method to display a bar chart
    public static void displayBarChart(BarChart<String, Number> barChart){

        // determining the starting month for the chart
        int startingMonth = getStartingMonth();

        // array lists for each months data
        double[] monthlyIncome = new double[5];
        double[] monthlyExpense = new double[5];

        // getting monthly income and expenses for the chart
        for (int i = 0; i < 5; i++){
            monthlyIncome[i] = Income.getMonthIncome(startingMonth + i);
            monthlyExpense[i] = Expenditure.getMonthExpense(startingMonth + i);
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // setting chart title
        barChart.setTitle("Monthly Transactions");
        barChart.lookup(".chart-title").setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

        xAxis.setLabel("Month");
        xAxis.setStyle("-fx-text-fill: white;");

        yAxis.setLabel("Amount");
        yAxis.setStyle("-fx-text-fill: white;");

        barChart.setCategoryGap(5);

        // creating series for income
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        // adding data points for income series
        for (int i = 0; i < 5; i++) {
            incomeSeries.getData().add(new XYChart.Data<>(getMonthName(startingMonth + i), monthlyIncome[i]));
        }

        // creating series for expenses
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expense");
        // adding data points for expense series
        for (int i = 0; i < 5; i++) {
            expenseSeries.getData().add(new XYChart.Data<>(getMonthName(startingMonth + i), monthlyExpense[i]));
        }

        // defining series for balance
        XYChart.Series<String, Number> balanceSeries = new XYChart.Series<>();
        balanceSeries.setName("Balance");
        // adding data points for balance series
        for (int i = 0; i < 5; i++) {
            double balance = Math.abs(monthlyIncome[i] - monthlyExpense[i]);

            balanceSeries.getData().add(new XYChart.Data<>(getMonthName(startingMonth + i), balance));
        }

        // adding all series to the bar chart
        barChart.getData().addAll(incomeSeries, expenseSeries, balanceSeries);

        // changing the color of the balance bar if its a loss
        for (int i = 0; i < 5; i++){
            if (monthlyIncome[i] - monthlyExpense[i] < 0){
                balanceSeries.getData().get(i).getNode().setStyle("-fx-bar-fill: red;");
            }
        }

    }

    // method to get the name of a month
    public static String getMonthName(int num) {
        String monthName;
        switch (num) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:
                monthName = "Invalid Month";
                break;
        }
        return monthName;
    }

    // method to get the starting month for the chart
    public static int getStartingMonth(){

        int currentMonth = LocalDate.now().getMonthValue();
        int startingMonth;

        switch (currentMonth - 4){
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

            default:
                startingMonth = currentMonth - 4;
                break;
        }

        return startingMonth;
    }

}
