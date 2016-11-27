package com.example.caitlin.cookhelper;

import java.util.ArrayList;

/**
 * Created by Caitlin on 11/27/2016.
 */
public class RecipeBook {

    // ---------------
    // VARIABLES
    // ---------------

    // attributes
    private int numRecipes;
    private static RecipeBook theInstance;

    // associations
    private ArrayList<Recipe> recipes;

    // ---------------
    // CONSTRUCTOR
    // ---------------
    private RecipeBook() {}

    // ---------------
    // GETTERS & SETTERS
    // ---------------

    public static RecipeBook getInstance(){

        if (theInstance == null) {
            theInstance = RecipeBook();
        }
        return theInstance;
    }

    public int getNumRecipes() { return numRecipes; }
    public ArrayList<Recipe> getRecipes() {         // include some criteria as params

        recipes = // search database and return ArrayList<Recipe>
        setNumRecipes(recipes.size());
        return recipes;
    }

    private void setNumRecipes(int aNewNumber) { numRecipes = aNewNumber; }

    // ---------------
    // METHODS
    // ---------------

    public void addRecipe(String aName, int aNumServings, int aNumCalories,String aPrepTime,
                          String aCookTime, String someDirections, String aType, String aCategory) {

        new Recipe(aName, aNumServings, aNumCalories, aPrepTime,
                aCookTime, someDirections, aType, aCategory);
    }

    public void removeRecipe(Recipe recipe) {

        recipe.deleteRecipe();
    }

    public void getHelp() {

        // I'm not sure what goes here.
    }
}
