package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Onclick button methods for save and cancel buttons
        toSave();
        toCancel();
    }

    //Method to save recipe and go to final recipe view screen
    private void toSave() {
        Button toViewRecipe = (Button) findViewById(R.id.saveRecipeButton);
        toViewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Recipe newRecipe = createRecipe();

                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_name", receiveRecipeName());     //Sending added recipe name
                startActivity(intent);
                finish();
            }
        });
    }

    //Method to cancel adding recipe and go back to home screen
    private void toCancel() {
        Button toViewRecipe = (Button) findViewById(R.id.cancelAddButton);
        toViewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * This method creates a Recipe object from the user-entered information in this activity.
     *
     * @return the new Recipe
     */
    private Recipe createRecipe() {

        Recipe newRecipe = new RecipeBuilder().setName(receiveRecipeName())
                .setNumServings(Integer.parseInt(receiveRecipeServings()))
                .setNumCalories(Integer.parseInt(receiveRecipeCalories()))
                .setPrepTime(receiveRecipePrepTime())
                .setCookTime(receiveRecipeCookTime())
                .setType(receiveRecipeType())
                .setCategory(receiveRecipeCategory())
                .setDirections(receiveRecipeInstructions())
                .setIngredientMeasures(receiveIngredientQuantity(),
                        receiveIngredientUnit(), receiveIngredientNames())
                .createRecipe();

        return newRecipe;
    }



    //--------------------- Methods to get edittext text from textfields ---------------------------
    private String receiveRecipeName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextName);
        String recipeName = edit.getText().toString();
        return recipeName;
    }

    private String receiveRecipeType() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextType);
        String recipeType = edit.getText().toString();
        return recipeType;
    }

    private String receiveRecipeCategory() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCategory);
        String recipeCategory = edit.getText().toString();
        return recipeCategory;
    }

    private String receiveRecipePrepTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextPrepTime);
        String recipePrep = edit.getText().toString();
        return recipePrep;
    }

    private String receiveRecipeCookTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCookTime);
        String recipeCook = edit.getText().toString();
        return recipeCook;
    }

    private String receiveRecipeServings() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextServings);
        String recipeServings = edit.getText().toString();
        return recipeServings;
    }

    private String receiveRecipeCalories() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCalories);
        String recipeCalories = edit.getText().toString();
        return recipeCalories;
    }

    //Getting recipe instrctions string on click
    private void receiveRecipeInstructions() {
        ImageButton instructionClick = (ImageButton) findViewById(R.id.addInstructionButton);
        instructionClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Getting text in edit text
                EditText edit =  (EditText) findViewById(R.id.firstInstruction);
                String recipeInstruction = edit.getText().toString();

                //*** Insert method to save text to database

                //Clearing editText to ready for next entry
                edit.setText("");
            }
        });
    }

    private void receiveRecipeIngredients() {
        ImageButton ingredientClick = (ImageButton) findViewById(R.id.addIngredientButton);
        ingredientClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //***ON Click for ingredients button
                String ingredientName = receiveIngredientName();

                //**METHOD to add ingredient name to database

                //Clearing text for next entry
                EditText edit =  (EditText) findViewById(R.id.firstIngredient);
                edit.setText("");
            }
        });
    }

    private String receiveIngredientName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.firstIngredient);
        String ingredient = edit.getText().toString();
        return ingredient;
    }

    private String receiveIngredientQuantity() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextQuantity);
        String recipeQuantity = edit.getText().toString();
        return recipeQuantity;
    }

    private String receiveIngredientUnit() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextUnit);
        String recipeUnit = edit.getText().toString();
        return recipeUnit;
    }

    private String receiveIngredientMeasure() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextMeasure);
        String recipeMeasure = edit.getText().toString();
        return recipeMeasure;
    }
    //----------------------------------------------------------------------------------------------

}
