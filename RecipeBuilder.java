package com.example.caitlin.cookhelper;

import java.util.ArrayList;

/**
 * Created by Caitlin on 11/28/2016.
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

    public Recipe createRecipe() {
        return new Recipe(name, numServings, numCalories, prepTime, cookTime, type, category,
                directions, ingredientMeasures);
    }

}