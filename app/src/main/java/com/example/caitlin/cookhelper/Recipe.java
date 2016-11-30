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
    private long _id;
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

    public long getId() {
        return _id;
    }
    public String getName() { return name; }
    public int getNumServings() { return numServings; }
    public int getNumCalories() { return numCalories; }
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

    /**
     * This method adds a new IngredientMeasure object to the end of this recipe's
     * ingredientMeasures list.
     *
     * @param amount
     * @param unit
     * @param ingredientName
     */
    public void addIngredientMeasure(String amount, String unit, String ingredientName) {

        Ingredient newIng = new Ingredient(ingredientName);
        Number parsedAmount = Double.parseDouble(amount);
        if (Math.ceil((Double)parsedAmount) == Math.floor((Double)parsedAmount)) {
            parsedAmount = parsedAmount.intValue();
        }
        IngredientMeasure newIngMeasure = new IngredientMeasure(newIng, unit, parsedAmount);
        ingredientMeasures.add(newIngMeasure);
    }

    /**
     * This method removes an IngredientMeasure object at the specified position from
     * this recipe's ingredientMeasures list. It also deletes the IngredientMeasure object.
     *
     * @param index
     */
    public void removeIngredientMeasure(int index) {

        IngredientMeasure ingMeasureToRemove = ingredientMeasures.get(index);
        ingMeasureToRemove.delete();
        ingredientMeasures.remove(index);
    }

    /**
     * This method adds a new recipe instruction to the end of this recipe's list of directions.
     *
     * @param aNewDirection
     */
    public void addDirection(String aNewDirection) {

        directions.add(aNewDirection);
    }

    /**
     * This method removes an instruction at the given index from this recipe's directions list.
     *
     * @param index
     */
    public void removeDirection(int index) {

        directions.remove(index);
    }

    /**
     * This method removes all of a Recipe object's associated IngredientMeasure objects.
     * The Recipe object is deleted from the database.
     */
    public void deleteRecipe() {

        ingredientMeasures = null;
        name = null;
        prepTime = null;
        cookTime = null;
        directions = null;
        type = null;
        category = null;
        // TODO db.delete(this._id) e.g.
    }
}
