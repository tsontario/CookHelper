package com.example.caitlin.cookhelper;

/**
 * Ingredient class
 */

public class Ingredient {

    private String name;

    public Ingredient(String name){
        this.name = name;
    }

    /*
    Getters and setters to handle search & edit activities
     */
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
