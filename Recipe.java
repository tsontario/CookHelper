package com.example.caitlin.cookhelper;

import java.util.ArrayList;

/**
 * Created by Caitlin on 11/27/2016.
 */
public class Recipe {

    // ---------------
    // VARIABLES
    // ---------------

    // attributes
    private long _id = -1;
    private String name;
    private int numServings;
    private int numCalories;
    private String prepTime;
    private String cookTime;
    private String type;
    private String category;
    private ArrayList<String> directions;

    // associations
    private RecipeBook recipeBook;
    private ArrayList<IngredientMeasure> ingredientMeasures;

    // ---------------
    // CONSTRUCTOR
    // ---------------

    public Recipe(String aName, int aNumServings, int aNumCalories, String aPrepTime,
                  String aCookTime, String aType, String aCategory,ArrayList<String> someDirections,
                  ArrayList<IngredientMeasure> someIngredientMeasures) {

        recipeBook = RecipeBook.getInstance();
        name = aName;
        numServings = aNumServings;
        numCalories = aNumCalories;
        prepTime = aPrepTime;
        cookTime = aCookTime;
        type = aType;
        category = aCategory;
        directions = someDirections;
        ingredientMeasures = someIngredientMeasures;
    }

    // ---------------
    // GETTERS & SETTERS
    // ---------------

    public String getName() { return name; }
    public int getNumServings() { return numServings; }
    public int getNumCalories() { return numCalories; }
    public String getPrepTime() { return prepTime; }
    public String getCookTime() { return cookTime; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public ArrayList<String> getDirections() { return directions; }
    public ArrayList<IngredientMeasure> getIngredientMeasures() { return ingredientMeasures; }

    public void setName(String aNewName) { name = aNewName; }
    public void setNumServings(int aNewNumServings) { numServings = aNewNumServings; }
    public void setNumCalories(int aNewNumCalories) { numCalories = aNewNumCalories; }
    public void setPrepTime(String aNewPrepTime) { prepTime = aNewPrepTime; }
    public void setCookTime(String aNewCookTime) { cookTime = aNewCookTime; }
    public void setType(String aNewType) { type = aNewType; }
    public void setCategory(String aNewCategory) { category = aNewCategory; }
    public void setDirections(ArrayList<String> newDirections) {directions = newDirections; }
    public void setIngredientMeasures(ArrayList<IngredientMeasure> newIngredientMeasures) {
        ingredientMeasures = newIngredientMeasures;
    }

    // ---------------
    // METHODS
    // ---------------

    // public void addDirection
    // public void removeDirection

    // public void addIngredientMeasure
    // public void removeIngredientMeasure

    /**
     * This method determines whether a particular Ingredient object already exists in the
     * database. If the Ingredient object exists, it returns it; otherwise, it creates
     * a new Ingredient object and returns it.
     *
     * @param anIngredientName
     * @return
     */
    public Ingredient checkIngredientTable(String anIngredientName) {

        Ingredient ingredient;
        boolean ingredientExists;

        if (ingredientExists) {
            // RETURN IT FROM DATABASE
        } else {
            ingredient = new Ingredient(anIngredientName);
            return ingredient;
        }
    }

    /**
     * This method creates a new IngredientMeasure object and adds it to the calling Recipe
     * object's list, ingredientMeasures.
     *
     * @param anIngredient
     * @param unit
     * @param amount
     */
    public void addIngredientMeasure(Ingredient anIngredient,
            String unit, int amount) {

        IngredientMeasure newIngredientMeasure;
        newIngredientMeasure = new IngredientMeasure(this, anIngredient, unit, amount);
        ingredientMeasures.add(newIngredientMeasure);
    }

    /**
     * This method removes all of a Recipe object's associated IngredientMeasure objects.
     * The Recipe object is deleted from the database.
     */
    public void deleteRecipe() {

        IngredientMeasure ingredientMeasure;
        while (ingredientMeasures.size() > 0) {
            ingredientMeasure = ingredientMeasures.get(0);
            removeIngredientMeasure(ingredientMeasure);
        }

        // REMOVE RECIPE FROM DATABASE

        // Q: Is the below code necessary or useful?
        name = null;
        numServings = null;
        numCalories = null;
        prepTime = null;
        cookTime = null;
        directions = null;
        type = null;
        category = null;
        // TODO db.delete(this._id) e.g.
    }

    /**
     * This method removes a given IngredientMeasure object from the calling Recipe object's
     * list, ingredientMeasures.
     *
     * @param ingredientMeasure
     */
    public void removeIngredientMeasure(IngredientMeasure ingredientMeasure) {

        // Q: Does an Ingredient object see all of its associated IngredientMeasure objects?
        ingredientMeasure.deleteLinks(); // Delete links to Recipe and Ingredient
        ingredientMeasures.remove(ingredientMeasure);
    }
}
