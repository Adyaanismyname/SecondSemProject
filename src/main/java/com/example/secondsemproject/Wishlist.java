package com.example.secondsemproject;

import java.time.LocalDate;
import java.util.ArrayList;

// This class represents a Wishlist item
public class Wishlist {

    private int ID;
    private static int W_IDgenerator;
    private String item_name;
    private double item_price;
    private double rate;
    private double amount_saved;
    private String Username;
    private LocalDate lastCalculationDate;

    // ArrayLists to store Wishlists and redeemable items
    public static ArrayList<Wishlist> wishlists = new ArrayList<>();
    public static ArrayList<Wishlist> redeemable = new ArrayList<>();


    // Constructor
    public Wishlist(String item_name, double item_price, double rate) {
        this.ID = W_IDgenerator;
        this.item_name = item_name;
        this.item_price = item_price;
        this.rate = rate;
        this.lastCalculationDate = LocalDate.now();
        this.Username = HelloController.getUsername_to_pass();
        wishlists.add(this);
        W_IDgenerator++;
    }


    // Constructor wih different parameters
    public Wishlist(int Id, String item_name, double item_price, double rate, LocalDate date, String Username, double amount_saved) {
        this.ID = Id;
        this.Username = Username;
        this.item_name = item_name;
        this.item_price = item_price;
        this.amount_saved = amount_saved;
        this.rate = rate;
        this.lastCalculationDate = date;
        main.setIdForTable("Wishlist");
    }


    // Method to set ID
    public static void setId(int id) {
        W_IDgenerator = id;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItemPrice(double item_price) {
        if (item_price >= 0) {
            this.item_price = item_price;
        } else {
            this.item_price = 0;
        }
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        if (rate >= 0) {
            this.rate = rate;
        } else {
            this.rate = 0;
        }
    }

    public LocalDate getLastCalculationDate() {
        return lastCalculationDate;
    }

    public double getAmountSaved() {
        return amount_saved;
    }


    // ToString Method
    @Override
    public String toString() {
        return "Wishlist Item ID: " + ID +
                "\nItem Name: " + item_name +
                "\nItem Price: $" + item_price +
                "\nRate: " + rate +
                "\nAmount Saved: $" + amount_saved;
    }


    // Method to calculate the amount saved
    public static void calculateAmountSaved() {
        double amount;
        double savings = Income.getTotal() - Expenditure.getTotal();
        Wishlist.redeemable.clear();

        for (int i = 0; i < Wishlist.wishlists.size(); i++) {

            Wishlist wishlist = Wishlist.wishlists.get(i);
            boolean isNewMonth = wishlist.lastCalculationDate == null || wishlist.lastCalculationDate.getMonthValue() != LocalDate.now().getMonthValue();
            savings -= wishlist.getAmountSaved();

            if (!wishlist.isRedeemable()) {
                if (isNewMonth && savings > 0) {
                    amount = savings * (wishlist.rate / 100);

                    //so that the amount saved doesnt exceed the price
                    if (wishlist.amount_saved + amount > wishlist.item_price) {


                        double final_amount = wishlist.getItem_price() - wishlist.amount_saved;
                        savings -= final_amount;
                        wishlist.amount_saved += final_amount;

                        wishlist.lastCalculationDate = LocalDate.now();

                    }

                    else {
                        savings -= amount;
                        wishlist.amount_saved += amount;
                        wishlist.lastCalculationDate = LocalDate.now();
                    }

                }

            } else {
                redeemable.add(wishlist);
            }

        }
    }


    // Method to delete a Wishlist item
    public static boolean deleteWishlist(int ID) {

        // Iterate through each wishlist
        for (int i = 0; i < redeemable.size(); i++) {

            if (redeemable.get(i).getID() == ID) {
                redeemable.remove(i);
            }
        }

        // Iterate through each wishlist
        for (int i = 0; i < wishlists.size(); i++) {

            if (wishlists.get(i).getID() == ID) {

                wishlists.remove(i);

                // Successfully deleted
                return true;
            }
        }

        // No wishlist with the given ID found
        return false;
    }


    // Method to check if a Wishlist item is redeemable
    public boolean isRedeemable() {
        return !(amount_saved < item_price);
    }


    // Method to redeem a Wishlist item
    public static boolean redeem(int ID) {

        // Iterate through each wishlist
        for (Wishlist wishlist : redeemable) {
            if (wishlist.getID() == ID) {

                String categ = "wishlist: " + wishlist.item_name;
                // Add in expenditure

                Expenditure expenditure = new Expenditure(categ, LocalDate.now(), wishlist.getAmountSaved());

                // Remove the wishlist
                deleteWishlist(ID);

                return true;

            }
        }

        // The selected item cannot be redeemed
        return false;
    }

}
