package com.example.caitlin.cookhelper;

/**
 * The IngredientMeasure class stores information about the quantity of a specific ingredient.
 */
public class IngredientMeasure {

    // ---------------
    // VARIABLES
    // ---------------

    // attributes
    private String unit;
    private String amount;

    // association
    private Ingredient ingredient;

    // ---------------
    // CONSTRUCTOR
    // ---------------

    public IngredientMeasure(Ingredient ingredient, String unit, String amount) {
        this.ingredient = ingredient;
        this.unit = unit;
        this.amount = amount;
    }

    // ---------------
    // GETTERS
    // ---------------

    public String getUnit() { return unit; }

    public String getAmount() { return amount; }

    public Ingredient getIngredient() { return ingredient; }

    @Override
    public String toString(){
        return( amount + " " + unit + " " + ingredient.getName());
    }

}
