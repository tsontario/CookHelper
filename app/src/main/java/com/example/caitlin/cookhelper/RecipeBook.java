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

    public void addRecipe(Context context, Recipe r){

        new DatabaseHandler(context).addRecipe(r);
    }


    public Recipe getRecipe(Context context, long id){

        return new DatabaseHandler(context).getRecipe((int) id);
    }


    public void updateRecipe(Context context, Recipe r){

        new DatabaseHandler(context).updateRecipe(r);
    }


    public ArrayList<SearchResult> searchWithCriteria(Context context, String category, String type,
                                   String ingredientCriteria) {

        return new DatabaseHandler(context).findRecipes(category, type, ingredientCriteria);
    }




}