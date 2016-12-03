package com.example.caitlin.cookhelper;

import java.util.ArrayList;
import android.content.Context;
import com.example.caitlin.cookhelper.database.*;

/**
 * This class is the means through which the UI communicates with the database.
 */
public class RecipeBook {

    // ---------------
    // VARIABLES
    // ---------------

    // association
    private static RecipeBook theInstance;

    // ---------------
    // CONSTRUCTOR
    // ---------------

    private RecipeBook() { }

    // ---------------
    // GETTER
    // ---------------

    public static RecipeBook getInstance(){

        if (theInstance == null) {
            theInstance = new RecipeBook();
        }
        return theInstance;
    }

    // ---------------
    // METHODS
    // ---------------

    /**
     * This method takes input from the UI. It calls a corresponding method in the DatabaseHandler
     * class to add a Recipe to the database.
     *
     * @param context the application context
     * @param r the newly added recipe
     */
    public void addRecipe(Context context, Recipe r){

        new DatabaseHandler(context).addRecipe(r);
    }

    /**
     * This method takes input from the UI. It calls a corresponding method in the DatabaseHandler
     * class to get a Recipe from the database.
     *
     * @param context the application context
     * @param id the id of the recipe to return
     * @return the recipe with the given id
     */
    public Recipe getRecipe(Context context, long id){

        return new DatabaseHandler(context).getRecipe((int) id);
    }

    /**
     * This method takes input from the UI. It calls a corresponding method in the DatabaseHandler
     * class to update a Recipe in the database.
     *
     * @param context the application context
     * @param r the recipe to update
     */
    public void updateRecipe(Context context, Recipe r){

        new DatabaseHandler(context).updateRecipe(r);
    }

    /**
     * This method takes input from the UI. It calls a corresponding method in the DatabaseHandler
     * class to delete a Recipe and its IngredientMeasures from the database.
     *
     * @param context the application context
     * @param id the id of the recipe to delete
     */
    public void deleteRecipe(Context context, long id){

        new DatabaseHandler(context).deleteIngredientMeasures(id);
        new DatabaseHandler(context).deleteRecipe(id);
    }

    /**
     * This method takes input from the UI. It calls a corresponding method in the DatabaseHandler
     * class to search for relevant Recipes in the database.
     *
     * @param context the application context
     * @param category the recipe category
     * @param type the recipe type
     * @param ingredientCriteria the ingredients for which to search
     * @return a list of SearchResults
     */
    public ArrayList<SearchResult> searchWithCriteria(Context context, String category, String type,
                                   String ingredientCriteria) {
        return new DatabaseHandler(context).findRecipes(category, type, ingredientCriteria);
    }

    /**
     * This method takes input from the UI. It calls a corresponding method in the DatabaseHandler
     * class return all saved Recipes.
     *
     * @param context the application context
     * @return all Recipes in the database
     */
    public ArrayList<SearchResult> browseAllRecipes(Context context){

        return (ArrayList<SearchResult>) new DatabaseHandler(context).getAllRecipes();
    }
}