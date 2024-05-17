package com.example.secondsemproject;


import java.time.LocalDate;
import java.util.ArrayList;

//give a reminder that this amount has been collected!!!!!
public class Wishlist {

    private int ID;
    private static int W_IDgenerator = 1;
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
        main.setIdForTable("Wishlist");
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

    public String getItemName() {
        return item_name;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public double getItemPrice() {
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


    public void calculateAmountSaved(){

        double amount;
        double savings = Income.getTotal() - Expenditure.getTotal();

        boolean isNewMonth = lastCalculationDate.getMonthValue() != LocalDate.now().getMonthValue();

        if (isNewMonth){

            if (!isRedeemable()){

                if (savings > 0) {
                    amount = savings * (this.rate / 100);
                    this.amount_saved += amount;
                }

            }
            else {
                redeemable.add(this);
            }

            this.lastCalculationDate = LocalDate.now();
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

    // convert the wishlist to an expenditure and delEte the wishlist
    public boolean isRedeemable (){
        return !(amount_saved < item_price);
    }

    public static boolean redeem (int ID){

        //for loop iterates through each wishlist
        for (int i = 0; i < redeemable.size(); i++) {
            if (redeemable.get(i).getID() == ID) {

                //add in expenditure
                Expenditure expenditure = new Expenditure("wishlist", LocalDate.now(), redeemable.get(i).getItemPrice());

                //remove the wishlist
                deleteWishlist(ID);

                return true;

            }
        }

        //the selected item cnnot be redeemed
        return false;
    }

}