package com.example.caitlin.cookhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "recipeDatabase.db";

    // Table Names
    private static final String TABLE_RECIPES = "Recipes";
    private static final String TABLE_INGREDIENTS = "Ingredients";
    private static final String TABLE_INGREDIENT_MEASURES = "Ingredient_Measures"

    // Recipes Table Columns
    private static final String KEY_RECIPE_ID = "_id";
    private static final String KEY_RECIPE_NAME = "name";
    private static final String KEY_RECIPE_CATEGORY = "category";
    private static final String KEY_RECIPE_TYPE = "type";
    private static final String KEY_RECIPE_COOKTIME = "cooktime";
    private static final String KEY_RECIPE_PREPTIME = "preptime";
    private static final String KEY_RECIPE_DIRECTIONS = "directions";
    private static final String KEY_RECIPE_SERVINGS = "numservings";
    private static final String KEY_RECIPE_CALORIES = "calories";

    // Ingredient Columns
    private static final String KEY_INGREDIENT_NAME = "name";

    // IngredientMeasure Columns
    private static final String KEY_INGREDIENT_MEASURE_RECIPE = "recipe";
    private static final String KEY_INGREDIENT_MEASURE_NAME = "name";
    private static final String KEY_INGREDIENT_MEASURE_QTY = "quantity";
    private static final String KEY_INGREDIENT_MEASURE_MEASUREMENT = "measurement";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE =
                "CREATE TABLE " + TABLE_RECIPES + "("
                + KEY_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + KEY_RECIPE_NAME + " TEXT, "
                + KEY_RECIPE_CATEGORY + " TEXT" + ")";

        String CREATE_INGREDIENTS_TABLE =
                "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_INGREDIENT_NAME + "TEXT PRIMARY KEY NOT NULL UNIQUE";
        String CREATE_INGREDIENT_MEASURES_TABLE =
                "CREATE TABLE " + TABLE_INGREDIENT_MEASURES + "("
                        + KEY_INGREDIENT_MEASURE_RECIPE + ","
                        + KEY_INGREDIENT_MEASURE_NAME + ","
                        + KEY_INGREDIENT_MEASURE_QTY + ","
                        + KEY_INGREDIENT_MEASURE_MEASUREMENT + ","
                        + "FOREIGN KEY(" + KEY_INGREDIENT_MEASURE_RECIPE + ") REFERENCES "
                        + TABLE_RECIPES + "(" + KEY_RECIPE_ID + "),"
                        + "FOREIGN KEY(" + KEY_INGREDIENT_NAME + ") REFERENCES "
                        + TABLE_INGREDIENTS + "(" + KEY_INGREDIENT_NAME + ")"
                        + ");";

        db.execSQL(CREATE_RECIPES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);

        // Create tables again
        onCreate(db);
    }

    // CRUD OPERATIONS
    public void addRecipe(Recipe r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_NAME, r.getRecipeName());         // Recipe name
        values.put(KEY_RECIPE_CATEGORY, r.getRecipeCategory()); // Recipe category

        // Insert the row
        db.insert(TABLE_RECIPES, null, values);
        db.close();
    }

    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPES,
                new String[] {
                        KEY_RECIPE_ID, KEY_RECIPE_NAME, KEY_RECIPE_CATEGORY},
                KEY_RECIPE_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Recipe recipe = new Recipe(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        // Return the recipe
        return recipe;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<Recipe>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPES;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Recipe r = new Recipe();
                r.setRecipeName(cursor.getString(1));
                r.setRecipeCategory(cursor.getString(2));

                // Add Recipe to List
                recipeList.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipeList;


    }

    public int getRecipesCount() {
        String countQuery = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateRecipe(Recipe r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_NAME, r.getRecipeName());
        values.put(KEY_RECIPE_CATEGORY, r.getRecipeCategory());

        return db.update(TABLE_RECIPES, values, KEY_RECIPE_ID + " = ?",
                new String[] {String.valueOf(r.getID())});
    }

    public void deleteRecipe(Recipe r) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RECIPES, KEY_RECIPE_NAME + " =?",
                new String[] {String.valueOf(r.getRecipeName())});
        db.close();
    }




}