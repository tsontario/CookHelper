package com.example.caitlin.cookhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.caitlin.cookhelper.Ingredient;
import com.example.caitlin.cookhelper.IngredientMeasure;
import com.example.caitlin.cookhelper.Recipe;
import com.example.caitlin.cookhelper.RecipeBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class describes the full functionality of the persistent storage of the CookHelper
 * application. It defines all table schema and all relevant CRUD operations (as defined by
 * the public interface).
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    /** SCHEMA DEFINITIONS */
    // Database Version
    private static final int DATABASE_VERSION               = 2;

    // Database Name
    private static final String DATABASE_NAME               = "recipeDatabase.db";

    // Table Names
    private static final String TABLE_RECIPES               = "Recipes";
    private static final String TABLE_INGREDIENTS           = "Ingredients";
    private static final String TABLE_INGREDIENT_MEASURES   = "Ingredient_Measures";

    // Recipes Table Columns
    private static final String KEY_RECIPE_ID               = "_id";
    private static final String KEY_RECIPE_NAME             = "name";
    private static final String KEY_RECIPE_SERVINGS         = "numservings";
    private static final String KEY_RECIPE_CALORIES         = "calories";
    private static final String KEY_RECIPE_PREPTIME         = "preptime";
    private static final String KEY_RECIPE_COOKTIME         = "cooktime";
    private static final String KEY_RECIPE_TYPE             = "type";
    private static final String KEY_RECIPE_CATEGORY         = "category";
    private static final String KEY_RECIPE_DIRECTIONS       = "directions";

    // Ingredient Columns
    private static final String KEY_INGREDIENT_NAME         = "name";

    // IngredientMeasure Columns
    private static final String KEY_INGREDIENT_MEASURE_RECIPE       = "recipe";
    private static final String KEY_INGREDIENT_MEASURE_NAME         = "name";
    private static final String KEY_INGREDIENT_MEASURE_QTY          = "quantity";
    private static final String KEY_INGREDIENT_MEASURE_MEASUREMENT  = "measurement";
    // Temp column
    public static final String INGS                                 = "ings";


    /** Constructor */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Initial Database creation. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE =
                "CREATE TABLE "
                + TABLE_RECIPES         + "("
                + KEY_RECIPE_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + KEY_RECIPE_NAME       + " TEXT, "
                + KEY_RECIPE_SERVINGS   + " INTEGER, "
                + KEY_RECIPE_CALORIES   + " INTEGER, "
                + KEY_RECIPE_PREPTIME   + " TEXT, "
                + KEY_RECIPE_COOKTIME   + " TEXT, "
                + KEY_RECIPE_TYPE       + " TEXT, "
                + KEY_RECIPE_CATEGORY   + " TEXT, "
                + KEY_RECIPE_DIRECTIONS + " TEXT "
                + ");";

        String CREATE_INGREDIENTS_TABLE =
                "CREATE TABLE "         + TABLE_INGREDIENTS + "("
                + KEY_INGREDIENT_NAME   + " TEXT PRIMARY KEY NOT NULL UNIQUE"
                + ");";

        String CREATE_INGREDIENT_MEASURES_TABLE =
                "CREATE TABLE " + TABLE_INGREDIENT_MEASURES         + "("
                + KEY_INGREDIENT_MEASURE_RECIPE                     + " INTEGER, "
                + KEY_INGREDIENT_MEASURE_NAME                       + " TEXT, "
                + KEY_INGREDIENT_MEASURE_QTY                        + " TEXT, "
                + KEY_INGREDIENT_MEASURE_MEASUREMENT                + " TEXT, "
                + "FOREIGN KEY(" + KEY_INGREDIENT_MEASURE_RECIPE    + ") REFERENCES "
                + TABLE_RECIPES + "(" + KEY_RECIPE_ID               + "),"
                + "FOREIGN KEY(" + KEY_INGREDIENT_MEASURE_NAME      + ") REFERENCES "
                + TABLE_INGREDIENTS + "(" + KEY_INGREDIENT_NAME     + ")"
                + ");";

        // Create the tables
        db.execSQL(CREATE_RECIPES_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_INGREDIENT_MEASURES_TABLE);
    }

    /** On version change */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_MEASURES);

        // Create tables again
        onCreate(db);
    }

    /************** CREATE READ UPDATE DELETE FUNCTIONS ***********************/

    /** Adds a Recipe object to the database. Also adds relevant rows to IngredientMeasures table and
     * Ingredients tables.
     *
     * @param r A Recipe object
     */
    public void addRecipe(Recipe r) {
        SQLiteDatabase db = this.getWritableDatabase();

        // add to Recipe table
        addToRecipeTable(r, db);

        // add to Ingredient and Ingredient_Measures tables
        ArrayList<IngredientMeasure> iMeasures = r.getIngredientMeasures();
        addToIngredientTables(r.getId(), iMeasures, db);

        // Updates done, close DB
        db.close();
    }

    /** Return a specific Recipe object from the database
     *
     * @param id ID of the recipe, can be accessed via a SearchResult object
     * @return a Recipe object
     * */
    public Recipe getRecipe(long id) {
        // Get the database row
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null,
                KEY_RECIPE_ID + " =?",
                new String[] {String.valueOf(id)}, null, null, null);

        // Get row if exists
        if (cursor != null) {
            cursor.moveToFirst();
        }
        else {
            return null;
        }

        // Iterate through all columns
        String name = cursor.getString(1);
        String numServings = cursor.getString(2);
        String numCalories = cursor.getString(3);
        String prepTime = cursor.getString(4);
        String cookTime = cursor.getString(5);
        String type = cursor.getString(6);
        String category = cursor.getString(7);

        // Get directions as ArrayList (stored in DB as JSON string)
        String directionsJSONString = cursor.getString(8);
        ArrayList<String> directions = new ArrayList<>();
        getDirections(directionsJSONString, directions);

        // Get Ingredient Measures from separate table
        ArrayList<IngredientMeasure> ingredientMeasures = new ArrayList<>();
        getIngredientMeasures(id, ingredientMeasures, db);

        // Now build the recipe
        RecipeBuilder builder = new RecipeBuilder();
        builder.setName(name)
                .setNumServings(numServings)
                .setNumCalories(numCalories)
                .setPrepTime(prepTime)
                .setCookTime(cookTime)
                .setType(type)
                .setCategory(category)
                .setDirections(directions)
                .setIngredientMeasures(ingredientMeasures);

        // Return the recipe
        Recipe r = builder.createRecipe();
        r.setId(id);

        cursor.close();
        db.close();
        return r;
    }

    /**
     * Returns a list of all recipes in the database. Does *NOT* return Recipe objects. Returns
     * SearchResult objects that contain (name, id). Useful for populating lists in the UI.
     *
     * @return A List of type SearchResult
     */
    public List<SearchResult> getAllRecipes() {
        ArrayList<SearchResult> recipeList = new ArrayList<SearchResult>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPES;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                SearchResult obj = new SearchResult(cursor.getString(1), cursor.getInt(0));
                recipeList.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recipeList;
    }

    /** Counts total number of recipes in the database
     *
     * @return the number of recipes in the DB.
     */
    public int getRecipesCount() {
        String countQuery = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    /** Updates the values of the given recipe. ID is kept the same but all other values can change.
     *  Also deletes and reinserts all IngredientMeasure associations to avoid erroneous entries.
     *
     * @param r The recipe that has been edited
     * @return the id of the edited recipe
     */
    public long updateRecipe(Recipe r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues recipeValues = new ContentValues();
        recipeValues.put(KEY_RECIPE_ID,           r.getId());
        recipeValues.put(KEY_RECIPE_NAME,         r.getName());
        recipeValues.put(KEY_RECIPE_SERVINGS,     r.getNumServings());
        recipeValues.put(KEY_RECIPE_CALORIES,     r.getNumCalories());
        recipeValues.put(KEY_RECIPE_PREPTIME,     r.getPrepTime());
        recipeValues.put(KEY_RECIPE_COOKTIME,     r.getCookTime());
        recipeValues.put(KEY_RECIPE_TYPE,         r.getType());
        recipeValues.put(KEY_RECIPE_CATEGORY,     r.getCategory());

        // Store variable # of directions as JSON string
        JSONArray directions = new JSONArray(r.getDirections());
        recipeValues.put(KEY_RECIPE_DIRECTIONS,   directions.toString());

        db.update(TABLE_RECIPES, recipeValues, KEY_RECIPE_ID + " =?",
                new String[] {String.valueOf(r.getId())});

        // Delete old references to IngredientMeasures and replace with new
        deleteIngredientMeasures(r.getId());
        ArrayList<IngredientMeasure> iMeasures = r.getIngredientMeasures();
        addToIngredientTables(r.getId(), iMeasures, db);

        db.close();
        return r.getId();
    }

    /** Deletes the recipe with the given id from the database.
     *
     * @param id the id of the recipe to be deleted
     */
    public void deleteRecipe(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RECIPES, KEY_RECIPE_ID + " =?",
                new String[] {String.valueOf(id)});
        db.delete(TABLE_INGREDIENT_MEASURES, KEY_INGREDIENT_MEASURE_RECIPE + " =?",
                new String[] {String.valueOf(id)});

        db.close();
    }


    /** Searches for recipes based on the given criteria. Returns a list of SearchResult objects
     *
     */
    public ArrayList<SearchResult> findRecipes(String category, String type, String ingredientQuery) {
        ArrayList<SearchResult> results = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // Make a temporary search table
            String TABLE_SEARCH = "search";
            String createTempTable = "CREATE TEMPORARY TABLE " + TABLE_SEARCH + "("
                    + KEY_RECIPE_ID + " INTEGER, "
                    + KEY_RECIPE_CATEGORY + " TEXT, "
                    + KEY_RECIPE_TYPE + " TEXT, "
                    + KEY_RECIPE_NAME + " TEXT, "
                    + INGS + " TEXT"
                    + ");";

            // Create and populate the search table
            String dropTable = "DROP TABLE IF EXISTS " + TABLE_SEARCH;
            db.execSQL(dropTable);
            db.execSQL(createTempTable);
            db.execSQL("INSERT INTO search ("
                    + KEY_RECIPE_ID + ", " + KEY_RECIPE_CATEGORY + ", "
                    + KEY_RECIPE_TYPE + ", " + KEY_RECIPE_NAME + ", ings) SELECT "
                    + TABLE_INGREDIENT_MEASURES + "." + KEY_INGREDIENT_MEASURE_RECIPE + ", "
                    + TABLE_RECIPES + "." + KEY_RECIPE_CATEGORY + ", "
                    + TABLE_RECIPES + "." + KEY_RECIPE_TYPE + ", "
                    + TABLE_RECIPES + "." + KEY_RECIPE_NAME + ", "
                    + "group_concat( " + TABLE_INGREDIENT_MEASURES + "." + KEY_INGREDIENT_MEASURE_NAME + ",\"!\") "
                    + "FROM " + TABLE_RECIPES + " LEFT JOIN " + TABLE_INGREDIENT_MEASURES
                    + " ON " + TABLE_RECIPES + "." + KEY_RECIPE_ID + " = "
                    + TABLE_INGREDIENT_MEASURES + "." + KEY_INGREDIENT_MEASURE_RECIPE
                    + " GROUP BY " + TABLE_RECIPES + "." + KEY_RECIPE_ID + ";");

            // Normalize the inputs
            category = category.trim();
            type = type.trim();
            // Create the custom query
            ArrayList<String> rankArgs = new ArrayList<>(); // For ranking results later

            String searchQuery = "SELECT " + KEY_RECIPE_ID + ", " + KEY_RECIPE_NAME + ", "
                    + INGS + " FROM "
                    + TABLE_SEARCH + " WHERE ";

            boolean lookBehind = false;
            if (category != null && category.length() > 0) {
                searchQuery += KEY_RECIPE_CATEGORY + " LIKE \"%" + category + "%\" ";
                lookBehind = true;
            }
            if (type != null && type.length() > 0) {
                if (lookBehind) {
                    searchQuery += " AND ";
                }
                searchQuery += KEY_RECIPE_TYPE + " LIKE \"%" + type + "%\" ";
                lookBehind = true;
            }
            if (lookBehind) {
                searchQuery += "AND ";
            }
            System.out.println(searchQuery);
            searchQuery += generateSQLQuery(ingredientQuery, INGS, rankArgs);
            System.out.println(searchQuery);
            Cursor cursor = db.rawQuery(searchQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
            } else {
                return null;
            }

            // Gather all results, ranking as we go.
            while (!cursor.isAfterLast()) {
                String r_name = cursor.getString(1);
                long r_id = cursor.getInt(0);
                String s = cursor.getString(2);
                SearchResult result = new SearchResult(r_name, r_id);
                setRank(result, rankArgs, s);
                results.add(result);
                cursor.moveToNext();
            }

            // Now sort the results according to ranking
            Collections.sort(results, new Comparator<SearchResult>() {
                public int compare(SearchResult o1, SearchResult o2) {
                    if (o1.getRank() > o2.getRank()) {
                        return -1;
                    } else if (o1.getRank() < o2.getRank()) {
                        return 1;
                    }
                    return 0;
                }
            });

            cursor.close();
            db.close();
        }
        // On error, return an error list
        catch (SQLiteException e) {
            results = new ArrayList<>();
            results.add(new SearchResult("Error parsing query!", 1));
        }
        catch (EmptyStackException e) {
            results = new ArrayList<>();
            results.add(new SearchResult("Error parsing query!", 1));
        }
        return results;
    }

    /************** PRIVATE UTILITY / HELPER METHODS *******************/

    /** Helper methods for getRecipe(int id) */
    private void getDirections(String rawDirectionsString, ArrayList<String> directions) {
        try {
            JSONArray transform = new JSONArray(rawDirectionsString);
            for (int i=0; i<transform.length(); i++) {
                directions.add(transform.get(i).toString());
            }
        } catch (JSONException e) {
            directions.add("Error reading file. Please try again");
        }
    }

    private void getIngredientMeasures(long id, ArrayList<IngredientMeasure> ingredientMeasures,
                                         SQLiteDatabase db) {
        Cursor cursor;
        cursor = db.query(TABLE_INGREDIENT_MEASURES, null, KEY_INGREDIENT_MEASURE_RECIPE + " =?",
                new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Ingredient i = new Ingredient(cursor.getString(1));
            String unit = cursor.getString(3);
            String amount = cursor.getString(2);
            IngredientMeasure iM = new IngredientMeasure(i, unit, amount);
            ingredientMeasures.add(iM);
            cursor.moveToNext();
        }
        cursor.close();
    }
    /** End of helper methods for getRecipe(int id) */

    /** Convenience method while updating a recipe
     *  Deletes all references to a recipe in Ingredient_Measures table
     *  New references will be populated in updateRecipe().
     */
    public void deleteIngredientMeasures(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INGREDIENT_MEASURES, KEY_INGREDIENT_MEASURE_RECIPE + " =?",
                new String[] {String.valueOf(id)});
        // DB closed in caller
    }

    /** Helper methods for addRecipe(Recipe r) */
    private void addToIngredientTables(long _id, ArrayList<IngredientMeasure> iMeasures,
                                        SQLiteDatabase db) {
        if (iMeasures != null) {
            for (IngredientMeasure im: iMeasures) {
                ContentValues ingredientValues = new ContentValues();
                ContentValues ingredientMeasureValues = new ContentValues();

                // Add Ingredient
                ingredientValues.put(KEY_INGREDIENT_NAME, im.getIngredient().getName());
                db.insertWithOnConflict(TABLE_INGREDIENTS, null,
                        ingredientValues, db.CONFLICT_IGNORE);

                // IngredientMeasure table
                ingredientMeasureValues.put(KEY_INGREDIENT_MEASURE_NAME, im.getIngredient().getName());
                ingredientMeasureValues.put(KEY_INGREDIENT_MEASURE_RECIPE, _id);
                ingredientMeasureValues.put(KEY_INGREDIENT_MEASURE_QTY, im.getAmount());
                ingredientMeasureValues.put(KEY_INGREDIENT_MEASURE_MEASUREMENT, im.getUnit());

                db.insert(TABLE_INGREDIENT_MEASURES, null, ingredientMeasureValues);
                // DB closed in caller
            }
        }
    }

    private void addToRecipeTable(Recipe r, SQLiteDatabase db) {
        ContentValues recipeValues = new ContentValues();

        recipeValues.put(KEY_RECIPE_NAME,         r.getName());
        recipeValues.put(KEY_RECIPE_SERVINGS,     r.getNumServings());
        recipeValues.put(KEY_RECIPE_CALORIES,     r.getNumCalories());
        recipeValues.put(KEY_RECIPE_PREPTIME,     r.getPrepTime());
        recipeValues.put(KEY_RECIPE_COOKTIME,     r.getCookTime());
        recipeValues.put(KEY_RECIPE_TYPE,         r.getType());
        recipeValues.put(KEY_RECIPE_CATEGORY,     r.getCategory());

        // Store variable # of directions as JSON string
        JSONArray directions = new JSONArray(r.getDirections());
        recipeValues.put(KEY_RECIPE_DIRECTIONS,   directions.toString());

        // Insert the row, create an id reference for recipe object
        long _id = db.insert(TABLE_RECIPES, null, recipeValues); // will return value of primary key
        r.setId(_id);
        // DB closed in caller
    }
    /** End of helper methods for addRecipe(Recipe r) */

    /**
     * Delegated call to static helper method for parsing text input into a legal SQLite statement
     * @param q The raw query string
     * @param prefix The name of the column to search over
     * @param rankArgs when generateSQLQuery returns, this will contain a 1-1 mapping of each atomic
     *                 query value (e.g. one ingredient). This wil be used for ranking results.
     * @return A legally formatted SQLite string that can execute a query on the DB.
     */
    private String generateSQLQuery(String q, String prefix, ArrayList<String> rankArgs) {
        return SQLParser.generateSQLQuery(q, prefix, rankArgs);
    }


    /**
     * Fetches a list of all the categories of recipes in the database. Also includes an empty string
     * to denote when no type is specified.
     * @return An ArrayList containing all the categories in the DB.
     */
    public ArrayList<String> getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("");  // default empty value

        String queryString = "SELECT " + KEY_RECIPE_CATEGORY + " FROM " + TABLE_RECIPES + " GROUP BY " +
                TABLE_RECIPES + "." + KEY_RECIPE_CATEGORY;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            cursor.close();
            db.close();
            return categories;
        }

        while (!cursor.isAfterLast()) {
            String category = cursor.getString(0);
            if (category != null && !category.isEmpty()) {
                categories.add(category);
            }
            cursor.moveToNext();
        }

        for (String s : categories) {
            System.out.println(s);
        }
        System.out.println("THOSE ARE CATS");
        cursor.close();
        db.close();
        return categories;
    }

    /**
     * Fetches a list of all the types of recipes in the database. Also includes an empty string
     * to denote when no type is specified
     * @return An ArrayList containing all the types in the DB.
     */
    public ArrayList<String> getTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> types = new ArrayList<>();
        types.add("");  // default empty value

        String queryString = "SELECT " + KEY_RECIPE_TYPE + " FROM " + TABLE_RECIPES + " GROUP BY " +
                TABLE_RECIPES + "." + KEY_RECIPE_TYPE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            cursor.close();
            db.close();
            return types;
        }

        while (!cursor.isAfterLast()) {
            String type = cursor.getString(0);
            if (type != null && !type.isEmpty()) {
                types.add(type);
            }
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return types;
    }

    /**
     * Iterates over search results and ranks them according to how many of their terms
     * match the search criteria. Only ranks over ingredients, not type and category.
     * @param result A SearchResult object
     * @param criteria A mapping of the ingredients from the query
     * @param ings The ingredients in the actual recipe being ranked (to be compared to 'criteria'
     */
    public void setRank(SearchResult result, ArrayList<String> criteria, String ings) {
        String[] ingList = ings.split("!");
        for (String i: ingList) {
            int rank = 0;
            for (String el : criteria) {
                if (el.equalsIgnoreCase(i)) {
                    rank++;
                }
            }
            result.setRank(rank);
        }
    }
}