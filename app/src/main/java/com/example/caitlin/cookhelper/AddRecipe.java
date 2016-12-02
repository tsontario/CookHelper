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

    // ---------------
    // VARIABLES
    // ---------------

    // association
    RecipeBook rBook;

    // ---------------
    // ON CREATE
    // ---------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        rBook = RecipeBook.getInstance();

        toSave();
        toCancel();
    }

    // ---------------
    // GETTERS FOR EDITTEXT STRINGS
    // ---------------

    private String getRecipeName() {

        EditText edit =  (EditText) findViewById(R.id.editTextName);
        String recipeName = edit.getText().toString();
        return recipeName;
    }

    private String getRecipeType() {

        EditText edit =  (EditText) findViewById(R.id.editTextType);
        String recipeType = edit.getText().toString();
        return recipeType;
    }

    private String getRecipeCategory() {

        EditText edit =  (EditText) findViewById(R.id.editTextCategory);
        String recipeCategory = edit.getText().toString();
        return recipeCategory;
    }

    private String getRecipePrepTime() {

        EditText edit =  (EditText) findViewById(R.id.editTextPrepTime);
        String recipePrep = edit.getText().toString();
        return recipePrep;
    }

    private String getRecipeCookTime() {

        EditText edit =  (EditText) findViewById(R.id.editTextCookTime);
        String recipeCook = edit.getText().toString();
        return recipeCook;
    }

    private String getRecipeServings() {

        EditText edit =  (EditText) findViewById(R.id.editTextServings);
        String recipeServings = edit.getText().toString();
        return recipeServings;
    }

    private String getRecipeCalories() {

        EditText edit =  (EditText) findViewById(R.id.editTextCalories);
        String recipeCalories = edit.getText().toString();
        return recipeCalories;
    }

    // ---------------
    // GETTERS FOR RECIPE DIRECTIONS AND INGREDIENT INFORMATION
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

    /**
     * This method returns a list of all ingredient amounts, or quantities, inputted by the user.
     *
     * @return a list of ingredient amounts
     */
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

    /**
     * This method returns a list of all ingredient units, or measures, inputted by the user.
     *
     * @return a list of ingredient units
     */
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

    /**
     * This method returns a list of all ingredient names inputted by the user.
     *
     * @return a list of ingredient names
     */
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

    // ---------------
    // METHODS
    // ---------------

    /**
     * This method clears the EditText object into which users input their instructions.
     * It also adds the most recently entered instruction to an LinearLayout reserved
     * for inputted instructions.
     *
     * @param v
     */
    public void addNewInstruction(View v) {

        EditText instEditText = (EditText) findViewById(R.id.firstInstruction);
        String instruction = instEditText.getText().toString();

        if ((instruction.trim().length() == 0) || (instruction == null)) { // if whitespace

            return;
        } else {

            // prepare the EditText for the next instruction
            instEditText.setText("");
            instEditText.setHint("What's the next instruction?");

            // get a reference to the LinearLayout for inputted instructions
            LinearLayout addedVertInstLL = (LinearLayout) findViewById(R.id.addedInstructionLL);

            // prepare horizontal LinearLayout for the most recently entered instruction
            LinearLayout horizLL = new LinearLayout(this);
            LayoutParams paramsLL = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            horizLL.setLayoutParams(paramsLL);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);

            // prepare TextView, which displays the newest instruction
            TextView addedInstTextView = new TextView(this);
            LayoutParams paramsTV = new LayoutParams((int) getResources()
                    .getDimension(R.dimen.zero_dp), LayoutParams.WRAP_CONTENT, 1f);
            addedInstTextView.setLayoutParams(paramsTV); // set width, height, weight
            addedInstTextView.setGravity(Gravity.RIGHT);
            addedInstTextView.setText(instruction);

            // add the TextView to the horizontal LinearLayout
            horizLL.addView(addedInstTextView);

            // prepare Button to delete previously added instructions
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

            // add the Button to the horizontal LinearLayout
            horizLL.addView(deleteButton, 1);

            // add the horizontal LinearLayout to the vertical LinearLayout for added instructions
            addedVertInstLL.addView(horizLL);
        }
    }

    /**
     * This method clears the EditText objects into which users input the quantities, units,
     * and names of a recipe's ingredients. It also adds the most recently entered ingredient
     * information to an LinearLayout reserved for inputted ingredients.
     * @param v
     */
    public void addNewIngredientMeasure(View v) {

        EditText amountET = (EditText) findViewById(R.id.eTextAmount);
        EditText unitET = (EditText) findViewById(R.id.eTextUnit);
        EditText ingNameET = (EditText) findViewById(R.id.eTextIngredientName);

        String amountStr = amountET.getText().toString();
        String unitStr = unitET.getText().toString();
        String ingNameStr = ingNameET.getText().toString();

        if ((ingNameStr.trim().length() == 0) || (ingNameStr == null)) { // if no ingredient given

            return;
        } else {

            // prepare the EditTexts for more ingredient details
            amountET.setText("");
            unitET.setText("");
            ingNameET.setText("");

            amountET.setHint("8");
            unitET.setHint("oz");
            ingNameET.setHint("dark chocolate");

            // get a reference to the LinearLayout for inputted ingredient information
            LinearLayout addedVertInstLL = (LinearLayout) findViewById(R.id.addedIMLL);

            // prepare horizontal LinearLayout for the newest ingredient information
            LinearLayout horizLL = new LinearLayout(this);
            LayoutParams paramsLL = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            horizLL.setLayoutParams(paramsLL);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);

            // prepare TextViews to store (TextViews 1-3) and
            // to display (TextView4) ingredient information
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

            // TextViews 1-3 are for storing, but not displaying, String information
            // about quantities, units, and ingredient names
            addedIMTextView1.setVisibility(View.INVISIBLE);
            addedIMTextView2.setVisibility(View.INVISIBLE);
            addedIMTextView3.setVisibility(View.INVISIBLE);

            // add the TextViews to the horizontal LinearLayout
            horizLL.addView(addedIMTextView1, 0);
            horizLL.addView(addedIMTextView2, 1);
            horizLL.addView(addedIMTextView3, 2);
            horizLL.addView(addedIMTextView4, 3);

            // prepare Button to delete previously added ingredient details
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

            // add the button to the horizontal LinearLayout
            horizLL.addView(deleteButton, 4);

            // add the horizontal LinearLayout to the vertical LinearLayout for added ingredients
            addedVertInstLL.addView(horizLL);
        }
    }

    /**
     * When the user clicks save, this method is called to confirm the user's selection.
     */
    private void toSave() {

        // QUESTION
        // CF: I think it would be a better idea to NOT have an alert here. If a user didn't mean
        // to select save, then he or she can easily edit or delete the recipe from the next screen.
        Button toViewRecipe = (Button) findViewById(R.id.saveRecipeButton);
        toViewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                createAlert();
            }
        });
    }

    /**
     * When the user clicks cancel, this method takes the user back to the Home activity.
     */
    private void toCancel() {

        // QUESTION
        // CF: I think it would be a good idea to have an alert here. Can you image selecting
        // cancel accidentally and losing your work?
        Button toViewRecipe = (Button) findViewById(R.id.cancelAddButton);
        toViewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * This method creates a dialog that allows the user to either save his or her recipe or to
     * return to the AddRecipe activity.
     */
    private void createAlert () {

        onPause();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(getResources().getString(R.string.saveQuestion))
                .setCancelable(false).setPositiveButton(getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override

                    // if "No" clicked
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                        onResume();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override

                    // if "Yes" clicked
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // create the updated Recipe and add it to the database
                        Recipe r = createRecipe();
                        rBook.addRecipe(getApplicationContext(), r);

                        // start the next activity by passing the intent
                        Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                        intent.putExtra("recipe_id", r.getId());
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.setTitle(getResources().getString(R.string.save));
        alert.show();
    }

    /**
     * This method sends information inputted by the user to the RecipeBuilder helper class
     * in order to create a Recipe object.
     *
     * @return the new Recipe
     */
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
}