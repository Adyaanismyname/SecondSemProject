package com.example.secondsemproject;

import java.util.ArrayList;

public class Category {

    private int ID;
    private String name;
    private static int C_IDgenerator;
    public static ArrayList<String>  categories = new ArrayList<>();

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){


    }
}
