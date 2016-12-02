package com.example.caitlin.cookhelper;

import java.util.ArrayList;
import android.content.Context;
import com.example.caitlin.cookhelper.database.*;
import android.database.sqlite.SQLiteDatabase;

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

    // ---------------
    // CONSTRUCTOR
    // ---------------
    private RecipeBook() { }

    // ---------------
    // GETTERS & SETTERS
    // ---------------

    public static RecipeBook getInstance(){

        if (theInstance == null) {
            theInstance = new RecipeBook();
        }
        return theInstance;
    }

    /**
    public int getNumRecipes() { return numRecipes; }

    public ArrayList<Recipe> getRecipes() {         // include some criteria as params

//        recipes = // search database and return ArrayList<Recipe>
//        setNumRecipes(recipes.size());
        return recipes;
    }

    private void setNumRecipes(int aNewNumber) { numRecipes = aNewNumber; }

    public void removeRecipe(Recipe recipe) {

        recipe.deleteRecipe();
    }
     */

    // ---------------
    // METHODS
    // ---------------

    public void addRecipe(Context context, Recipe r){

        new DatabaseHandler(context).addRecipe(r);
    }

    public Recipe getRecipe(Context context, long id){
        return new DatabaseHandler(context).getRecipe((int) id);
    }


}