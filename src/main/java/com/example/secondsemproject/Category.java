package com.example.secondsemproject;

import java.util.ArrayList;

public class Category {

    private int ID;
    private String name;
    private String Username;
    private static int C_IDgenerator;
    public static ArrayList<Category>  categories = new ArrayList<>();

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    Category(int Id,String name,String Username){
        this.ID=Id;
        this.name=name;
        this.Username=Username;
    }
    Category(String name){
        this.ID=C_IDgenerator;
        this.name=name;
        this.Username=HelloController.getUsername_to_pass();
        categories.add(this);
        C_IDgenerator++;
    }
}