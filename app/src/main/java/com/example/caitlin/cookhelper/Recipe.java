package com.example.caitlin.cookhelper;

import java.util.ArrayList;

/**
 * The Recipe class stores information about a recipe's details, its ingredients,
 * and its preparation.
 */
public class Recipe {

    // ---------------
    // VARIABLES
    // ---------------

    // attributes
    private long _id;
    private String name;
    private String numServings;
    private String numCalories;
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

    public Recipe(String aName, String aNumServings, String aNumCalories, String aPrepTime,
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

    public long getId() {
        return _id;
    }
    public String getName() { return name; }
    public String getNumServings() { return numServings; }
    public String getNumCalories() { return numCalories; }
    public String getPrepTime() { return prepTime; }
    public String getCookTime() { return cookTime; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public ArrayList<String> getDirections() { return directions; }
    public ArrayList<IngredientMeasure> getIngredientMeasures() { return ingredientMeasures; }

    public void setId(long id) {
        _id = id;
    }
    public void setName(String aNewName) { name = aNewName; }
    public void setNumServings(String aNewNumServings) { numServings = aNewNumServings; }
    public void setNumCalories(String aNewNumCalories) { numCalories = aNewNumCalories; }
    public void setPrepTime(String aNewPrepTime) { prepTime = aNewPrepTime; }
    public void setCookTime(String aNewCookTime) { cookTime = aNewCookTime; }
    public void setType(String aNewType) { type = aNewType; }
    public void setCategory(String aNewCategory) { category = aNewCategory; }
    public void setDirections(ArrayList<String> newDirections) {directions = newDirections; }
    public void setIngredientMeasures(ArrayList<IngredientMeasure> newIngredientMeasures) {
        ingredientMeasures = newIngredientMeasures;
    }

}
