package com.example.caitlin.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;



public class AddRecipe extends AppCompatActivity {

    RecipeBook rBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        rBook = RecipeBook.getInstance();

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
            addedInstTextView.setLayoutParams(paramsTV); // set width, height, weight
            addedInstTextView.setGravity(Gravity.RIGHT);
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


    public void addNewIngredientMeasure(View v) {

        EditText amountET = (EditText) findViewById(R.id.eTextAmount);
        EditText unitET = (EditText) findViewById(R.id.eTextUnit);
        EditText ingNameET = (EditText) findViewById(R.id.eTextIngredientName);

        String amountStr = amountET.getText().toString();
        String unitStr = unitET.getText().toString();
        String ingNameStr = ingNameET.getText().toString();

        amountET.setText("");
        unitET.setText("");
        ingNameET.setText("");

        amountET.setHint("1");
        unitET.setHint("cup");
        ingNameET.setHint("red wine");

        LinearLayout addedVertInstLL = (LinearLayout) findViewById(R.id.addedIMLL);

        if ((ingNameStr.trim().length() == 0) || (ingNameStr == null)) { // if no ingredient given
            return;
        } else {

            // prepare horizontal linear layout
            LinearLayout horizLL = new LinearLayout(this);
            LayoutParams paramsLL = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            horizLL.setLayoutParams(paramsLL);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);

            // prepare text views
            TextView addedIMTextView1 = new TextView(this);
            TextView addedIMTextView2 = new TextView(this);
            TextView addedIMTextView3 = new TextView(this);
            TextView addedIMTextView4 = new TextView(this);
            addedIMTextView4.setGravity(Gravity.RIGHT);

            LayoutParams paramsTV1 = new LayoutParams((int) getResources()
                    .getDimension(R.dimen.zero_dp), LayoutParams.WRAP_CONTENT, .1f);

            LayoutParams paramsTV2 = new LayoutParams((int) getResources()
                    .getDimension(R.dimen.zero_dp), LayoutParams.WRAP_CONTENT, 1f);


            addedIMTextView1.setLayoutParams(paramsTV1); // set width, height, weight
            addedIMTextView2.setLayoutParams(paramsTV1);
            addedIMTextView3.setLayoutParams(paramsTV1);
            addedIMTextView4.setLayoutParams(paramsTV2);

            addedIMTextView1.setText(amountStr);
            addedIMTextView2.setText(unitStr);
            addedIMTextView3.setText(ingNameStr);
            addedIMTextView4.setText(amountStr + " " + unitStr + " " + ingNameStr);

            addedIMTextView1.setVisibility(View.INVISIBLE);
            addedIMTextView2.setVisibility(View.INVISIBLE);
            addedIMTextView3.setVisibility(View.INVISIBLE);

            // add the text views to the horizontal linear layout
            horizLL.addView(addedIMTextView1, 0);
            horizLL.addView(addedIMTextView2, 1);
            horizLL.addView(addedIMTextView3, 2);
            horizLL.addView(addedIMTextView4, 3);

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
            horizLL.addView(deleteButton, 4);


            // add the horizontal linear layout to the vertical linear layout for added instructions
            addedVertInstLL.addView(horizLL);
        }
    }


    //Method to save recipe and go to final recipe view screen
    private void toSave() {
        Button toViewRecipe = (Button) findViewById(R.id.saveRecipeButton);
        toViewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                createAlert();
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
        Recipe newRecipe = new RecipeBuilder().setName(getRecipeName())
                .setNumServings(getRecipeServings())
                .setNumCalories(getRecipeCalories())
                .setPrepTime(getRecipePrepTime())
                .setCookTime(getRecipeCookTime())
                .setType(getRecipeType())
                .setCategory(getRecipeCategory())
                .setDirections(getRecipeInstructions())
                .setIngredientMeasures(getIngredientAmounts(),
                        getIngredientUnits(), getIngredientNames())
                .createRecipe();

        return newRecipe;
    }

    // ---------------
    // Getters for EditText Strings
    // ---------------

    private String getRecipeName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextName);
        String recipeName = edit.getText().toString();
        return recipeName;
    }

    private String getRecipeType() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextType);
        String recipeType = edit.getText().toString();
        return recipeType;
    }

    private String getRecipeCategory() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCategory);
        String recipeCategory = edit.getText().toString();
        return recipeCategory;
    }

    private String getRecipePrepTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextPrepTime);
        String recipePrep = edit.getText().toString();
        return recipePrep;
    }

    private String getRecipeCookTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCookTime);
        String recipeCook = edit.getText().toString();
        return recipeCook;
    }

    private String getRecipeServings() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextServings);
        String recipeServings = edit.getText().toString();
        return recipeServings;
    }

    private String getRecipeCalories() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCalories);
        String recipeCalories = edit.getText().toString();
        return recipeCalories;
    }

    // ---------------
    // Getters for ArrayList<String> of directions, ingredient amounts, ingredient units,
    // and ingredient names
    // ---------------

    /**
     * This method returns a list the instructions for making this recipe.
     *
     * @return a list of directions
     */
    private ArrayList<String> getRecipeInstructions() {

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

    private ArrayList<String> getIngredientAmounts() {
        ArrayList<String> amountsList = new ArrayList<String>();
        LinearLayout addedIngLL = (LinearLayout) findViewById(R.id.addedIMLL);
        LinearLayout childLL;
        TextView addedIngAmount;
        String ingredientAmount;

        for (int i = 0; i < addedIngLL.getChildCount(); i++) {

            childLL = (LinearLayout) addedIngLL.getChildAt(i);
            addedIngAmount = (TextView) childLL.getChildAt(0);
            ingredientAmount = addedIngAmount.getText().toString();
            amountsList.add(ingredientAmount);
        }

        return amountsList;
    }


    private ArrayList<String> getIngredientUnits() {

        ArrayList<String> unitsList = new ArrayList<String>();
        LinearLayout addedIngLL = (LinearLayout) findViewById(R.id.addedIMLL);
        LinearLayout childLL;
        TextView addedIngUnit;
        String ingredientUnit;

        for (int i = 0; i < addedIngLL.getChildCount(); i++) {

            childLL = (LinearLayout) addedIngLL.getChildAt(i);
            addedIngUnit = (TextView) childLL.getChildAt(1);
            ingredientUnit = addedIngUnit.getText().toString();
            unitsList.add(ingredientUnit);
        }

        return unitsList;
    }



    private ArrayList<String> getIngredientNames() {

        ArrayList<String> namesList = new ArrayList<String>();
        LinearLayout addedIngLL = (LinearLayout) findViewById(R.id.addedIMLL);
        LinearLayout childLL;
        TextView addedIngName;
        String ingredientName;

        for (int i = 0; i < addedIngLL.getChildCount(); i++) {

            childLL = (LinearLayout) addedIngLL.getChildAt(i);
            addedIngName = (TextView) childLL.getChildAt(2);
            ingredientName = addedIngName.getText().toString();
            namesList.add(ingredientName);
        }

        return namesList;
    }


    //Creating "Are you sure you want to save alert"
    private void createAlert () {
        onPause();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(getResources().getString(R.string.saveQuestion))
                .setCancelable(false).setPositiveButton(getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    //If "No clicked"
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        onResume();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    //If "Yes clicked"
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Recipe r = createRecipe();
                        rBook.addRecipe(getApplicationContext(), r);
                        Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                        intent.putExtra("recipe_id", r.getId());     //Sending added recipe name
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.setTitle(getResources().getString(R.string.save));
        alert.show();
    }
}

