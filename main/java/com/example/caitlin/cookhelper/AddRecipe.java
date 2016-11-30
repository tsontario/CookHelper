package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.view.Gravity;
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

    public void addNewInstruction(View v) {

        EditText instEditText = (EditText) findViewById(R.id.firstInstruction);
        String instruction = instEditText.getText().toString();
        instEditText.setText("");
        instEditText.setHint("What's the next instruction?");
        LinearLayout addedVertInstLL = (LinearLayout) findViewById(R.id.addedInstructionLL);

        if ((instruction.trim().length() == 0) || (instruction == null)) { // if whitespace
            return;
        } else {

            // prepare horizontal linear layout
            LinearLayout horizLL = new LinearLayout(this);
            LayoutParams paramsLL = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            horizLL.setLayoutParams(paramsLL);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);

            // prepare text view
            TextView addedInstTextView = new TextView(this);
            LayoutParams paramsTV = new LayoutParams((int) getResources()
                    .getDimension(R.dimen.zero_dp), LayoutParams.WRAP_CONTENT, 1f);
            paramsTV.gravity = Gravity.END;
            addedInstTextView.setLayoutParams(paramsTV); // set width, height, weight
            addedInstTextView.setText(instruction);

            // add the text view to the horizontal linear layout
            horizLL.addView(addedInstTextView);

            // prepare button
            ImageButton deleteButton = new ImageButton(this);
            deleteButton.setBackgroundResource(android.R.drawable.ic_delete);
            deleteButton.setClickable(true);
            deleteButton.setContentDescription("Click to delete instruction");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout horizParent = (LinearLayout) v.getParent();
                    LinearLayout vertParent = (LinearLayout) horizParent.getParent();
                    vertParent.removeView(horizParent);
                }
            });

            // add the button to the horizontal linear layout
            horizLL.addView(deleteButton, 1);

            // add the horizontal linear layout to the vertical linear layout for added instructions
            addedVertInstLL.addView(horizLL);
        }
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

    /**
     * This method returns a list the instructions for making this recipe.
     *
     * @return a list of directions
     */
    private ArrayList<String> receiveRecipeInstructions() {

        ArrayList<String> directions = new ArrayList<String>();
        LinearLayout addedInstLL = (LinearLayout) findViewById(R.id.addedInstructionLL);
        LinearLayout childLL;
        TextView addedInstText;
        String instruction;

        for (int i = 0; i < addedInstLL.getChildCount(); i++) {

            childLL = (LinearLayout) addedInstLL.getChildAt(i);
            addedInstText = (TextView) childLL.getChildAt(0);
            instruction = addedInstText.getText().toString();
            directions.add(instruction);
        }

        return directions;
    }

    private ArrayList<String> receiveRecipeIngredients() {
        ImageButton ingredientClick = (ImageButton) findViewById(R.id.addIngredientButton);
        ingredientClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //***ON Click for ingredients button
                String ingredientName = "";

                //**METHOD to add ingredient name to database

                //Clearing text for next entry
                EditText edit =  (EditText) findViewById(R.id.firstIngredient);
                edit.setText("");

            }
        });
        return new ArrayList<String>();
    }

    private ArrayList<String> receiveIngredientNames() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.firstIngredient);
        String ingredient = edit.getText().toString();
        return new ArrayList<String>();
    }

    private ArrayList<String> receiveIngredientQuantity() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextQuantity);
        String recipeQuantity = edit.getText().toString();
        return new ArrayList<String>();
    }

    private ArrayList<String> receiveIngredientUnit() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextUnit);
        String recipeUnit = edit.getText().toString();
        return new ArrayList<String>();
    }

    //----------------------------------------------------------------------------------------------

}
