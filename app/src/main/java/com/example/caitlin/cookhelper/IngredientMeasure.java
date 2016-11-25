package com.example.caitlin.cookhelper;

/**
 * IngredientMeasure class
 */

public class IngredientMeasure {

    private String unit;
    private int amount;
    private Ingredient ingredient;

    /*
    IngredientMeasure constructor
     */
    public IngredientMeasure(String unit, int amount){
        this.unit = unit;
        this.amount = amount;
    }

    /*
    IngredientMeasure getters
     */
    public String getUnit(){
        return unit;
    }

    public int getAmount(){
        return amount;
    }

    /*
    IngredientMeasure setters
     */
    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}
