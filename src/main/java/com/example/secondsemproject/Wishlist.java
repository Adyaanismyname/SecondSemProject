package com.example.secondsemproject;


import java.time.LocalDate;
import java.util.ArrayList;

//give a reminder that this amount has been collected!!!!!
public class Wishlist {

    private int ID;
    private static int W_IDgenerator = 0;
    private String item_name;
    private double item_price;
    private double rate;
    private double amount_saved = 0;
    private String Username;
    private LocalDate lastCalculationDate;

    public static ArrayList<Wishlist> wishlists = new ArrayList<>();
    public static ArrayList<Wishlist> redeemable = new ArrayList<>();


    // Constructor
    public Wishlist(String item_name, double item_price, double rate) {
        this.ID = W_IDgenerator;
        this.item_name = item_name;
        this.item_price = item_price;
        this.rate = rate;
        this.lastCalculationDate = LocalDate.now();
        this.Username=HelloController.getUsername_to_pass();

        wishlists.add(this);
        main.UpdateLatestIdForClass("L_Wishlist_id",W_IDgenerator+1,W_IDgenerator);


        W_IDgenerator++;
    }

    public Wishlist(int Id,String item_name, double item_price, double rate,LocalDate date,String Username) {
        this.ID = Id;
        this.Username=Username;
        this.item_name = item_name;
        this.item_price = item_price;
        this.rate = rate;
        this.lastCalculationDate =date;
    }
    public static void setId(int id){
        W_IDgenerator=id;
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
        }
        else {
            this.item_price = 0;
        }
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        if (rate >= 0) {
            this.rate = rate;
        }
        else {
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


    public static void calculateAmountSaved() {
        double amount;
        double savings = Income.getTotal() - Expenditure.getTotal();
        Wishlist.redeemable.clear();

        for (Wishlist wishlist : Wishlist.wishlists) {
            boolean isNewMonth = wishlist.lastCalculationDate == null || wishlist.lastCalculationDate.getMonthValue() != LocalDate.now().getMonthValue();

            if (!wishlist.isRedeemable()){
                if(isNewMonth && savings > 0){
                    amount = savings * (wishlist.rate / 100);
                    wishlist.amount_saved += amount;
                    wishlist.lastCalculationDate = LocalDate.now();
                }

            }
            else{
                redeemable.add(wishlist);
            }

        }
    }


    //REMOVE wishlist
    public static boolean deleteWishlist (int ID){

        //for loop iterates through each wishlist
        for (int i = 0; i < redeemable.size(); i++) {

            if (redeemable.get(i).getID() == ID) {
                redeemable.remove(i);
            }
        }

        //for loop iterates through each wishlist
        for (int i = 0; i < wishlists.size(); i++){

            if (wishlists.get(i).getID() == ID){

                wishlists.remove(i);

                //successfully deleted
                return true;
            }
        }

        //this means no such wishlist with the ID
        return false;
    }


    public boolean isRedeemable (){
        return !(amount_saved < item_price);
    }


    public static boolean redeem (int ID){

        //for loop iterates through each wishlist
        for (Wishlist wishlist : redeemable) {
            if (wishlist.getID() == ID) {

                String categ = "wishlist: " + wishlist.item_name;
                //add in expenditure

                Expenditure expenditure = new Expenditure(categ, LocalDate.now(), wishlist.getAmountSaved());

                //remove the wishlist
                deleteWishlist(ID);

                return true;

            }
        }

        //the selected item cnnot be redeemed
        return false;
    }

}