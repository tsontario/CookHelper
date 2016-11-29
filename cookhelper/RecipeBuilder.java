package com.example.caitlin.cookhelper;

import java.util.ArrayList;

/**
 * The RecipeBuilder class facilitates the creation of a Recipe object.
 */
public class RecipeBuilder {

    private String name;
    private int numServings;
    private int numCalories;
    private String prepTime;
    private String cookTime;
    private String type;
    private String category;
    private ArrayList<String> directions;
    private ArrayList<IngredientMeasure> ingredientMeasures;

    // ---------------
    // CONSTRUCTOR
    // ---------------

    public RecipeBuilder() { }

    // ---------------
    // SETTERS
    // ---------------

    public RecipeBuilder setName(String aName) {
        name = aName;
        return this;
    }

    public RecipeBuilder setNumServings(int aNumServings) {
        numServings = aNumServings;
        return this;
    }

    public RecipeBuilder setNumCalories(int aNumCalories) {
        numCalories = aNumCalories;
        return this;
    }

    public RecipeBuilder setPrepTime(String aPrepTime) {
        prepTime = aPrepTime;
        return this;
    }

    public RecipeBuilder setCookTime(String aCookTime) {
        cookTime = aCookTime;
        return this;
    }

    public RecipeBuilder setType(String aType) {
        type = aType;
        return this;
    }

    public RecipeBuilder setCategory(String aCategory) {
        category = aCategory;
        return this;
    }

    public RecipeBuilder setDirections(ArrayList<String> someDirections) {
        directions = someDirections;
        return this;
    }

    /**
     * This method creates Ingredient and IngredientMeasure objects. It then populates
     * the list ingredientMeasures.
     *
     * @param amounts the quantities of the ingredients' units
     * @param units the units, or measure, of each ingredient
     * @param ingredients the ingredient names
     * @return the calling RecipeBuilder object
     */
    public RecipeBuilder setIngredientMeasures(ArrayList<String> amounts,
                                               ArrayList<String> units,
                                               ArrayList<String> ingredients) {

        Ingredient newIng;
        IngredientMeasure newIngMeasure;

        for (int i = 0; i < amounts.size(); i++) {

            newIng = new Ingredient(ingredients.get(i));
            newIngMeasure = new IngredientMeasure(Integer.parseInt(amounts.get(i)),
                    units.get(i), newIng);
            ingredientMeasures.add(newIngMeasure);
        }

        return this;
    }

    /**
     * This method calls class Recipe's constructor to instantiate a new Recipe object.
     *
     * @return the new Recipe
     */
    public Recipe createRecipe() {
        return new Recipe(name, numServings, numCalories, prepTime, cookTime, type, category,
                directions, ingredientMeasures);
    }

}
