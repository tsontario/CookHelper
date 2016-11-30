package com.example.caitlin.cookhelper;

/**
 * IngredientMeasure class
 */

public class IngredientMeasure {

    private String unit;
    private Number amount;
    private Ingredient ingredient;

    /*
    IngredientMeasure constructor
     */
    public IngredientMeasure(Ingredient ingredient, String unit, Number amount) {
        this.ingredient = ingredient;
        this.unit = unit;
        this.amount = amount;
    }


    /*
    IngredientMeasure getters
     */
    public String getUnit(){
        return unit;
    }

    public Number getAmount(){
        return amount;
    }

    /*
    IngredientMeasure setters
    */
    public void setUnit(String unit){this.unit = unit;
    }

    public void setAmount(Number amount){
        this.amount = amount;
    }

    /*
    Unidirectional [*..1] link to specific Ingredient
     */
    public void setIngredientLink(Ingredient ingredient){
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient(){
        return ingredient;
    }

    /**
     * This method deletes the calling IngredientMeasure object and removes it from the databse.
     */
    public void delete() {
        ingredient = null;
        unit = null;
        amount = null;
        // TO DO delete from database
    }

}
