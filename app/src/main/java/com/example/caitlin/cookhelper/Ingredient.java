package com.example.caitlin.cookhelper;

/**
 * The Ingredient class represents a single ingredient.
 */
public class Ingredient {

    // ---------------
    // VARIABLES
    // ---------------

    // attribute
    private String name;

    // ---------------
    // CONSTRUCTOR
    // ---------------
    public Ingredient(String name){
        this.name = name;
    }

    // ---------------
    // GETTER AND SETTER
    // ---------------

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
