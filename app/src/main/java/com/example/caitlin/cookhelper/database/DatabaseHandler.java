package com.example.caitlin.cookhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.caitlin.cookhelper.Ingredient;
import com.example.caitlin.cookhelper.IngredientMeasure;
import com.example.caitlin.cookhelper.Recipe;
import com.example.caitlin.cookhelper.RecipeBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    /** SCHEMA DEFINITIONS */
    // Database Version
    private static final int DATABASE_VERSION               = 1;

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
                + KEY_INGREDIENT_MEASURE_QTY                        + " REAL, "
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
    public Recipe getRecipe(int id) {
        // Get the database row
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null,
                String.valueOf(id),
                null, null, null, null);

        // Get row if exists
        if (cursor != null) {
            cursor.moveToFirst();
        }
        else {
            return null;
        }
        // Iterate through all columns
        String name = cursor.getString(1);
        int numServings = cursor.getInt(2);
        int numCalories = cursor.getInt(3);
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
     * @return the number of recipes in db.
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


    /************** PRIVATE UTILITY / HELPER METHODS *******************/

    /** Helper methods for getRecipe(int id) */
    private void getDirections(String rawDirectionsString, ArrayList<String> directions) {
        try {
            JSONArray transform = new JSONArray(rawDirectionsString);
            for (int i=0; i<transform.length(); i++) {
                directions.add(transform.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getIngredientMeasures(int id, ArrayList<IngredientMeasure> ingredientMeasures,
                                       SQLiteDatabase db) {
        Cursor cursor;
        cursor = db.query(TABLE_INGREDIENT_MEASURES, null, String.valueOf(id) + " =?",
                new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Ingredient i = new Ingredient(cursor.getString(0));
            String unit = cursor.getString(1);
            int amount = cursor.getInt(2);
            IngredientMeasure iM = new IngredientMeasure(i, unit, amount);
            ingredientMeasures.add(iM);
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

        db.close();
    }

    /** Helper methods for addRecipe(Recipe r) */
    private void addToIngredientTables(long _id, ArrayList<IngredientMeasure> iMeasures,
                                       SQLiteDatabase db) {
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
}