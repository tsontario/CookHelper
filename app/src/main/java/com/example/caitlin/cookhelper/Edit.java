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
import android.widget.TextView;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    RecipeBook rBook;
    Recipe r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        rBook = RecipeBook.getInstance();

        Intent intent = getIntent();
        long recipe_id = intent.getLongExtra("recipe_id", 0l);

        r = rBook.getRecipe(getApplicationContext(), recipe_id);

        //Setting recipe name
        EditText recipeNameToChange = (EditText) findViewById(R.id.editTextNameEdit);
        // recipeNameToChange.setText(recipeName);


        //Calling on click methods
        toSave();
        toCancel();

    }

    private void toSave() {
        Button toEdit = (Button) findViewById(R.id.saveRecipeButtonEdit);
        toEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                createAlertEdit();
            }
        });
    }

    private void toCancel() {
        Button toEdit = (Button) findViewById(R.id.cancelEditButton);
        toEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Sending original text name to view activity, and then starting viewRecipe
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_id", r.getId());     //Sending added recipe name
                startActivity(intent);
                finish();   //Ending edit activity
            }
        });
    }


    //--------------------- Methods to get edittext text from textfields ---------------------------
    private String getRecipeName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextNameEdit);
        String recipeName = edit.getText().toString();
        return recipeName;
    }

    private String getRecipeType() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextTypeEdit);
        String recipeType = edit.getText().toString();
        return recipeType;
    }

    private String getRecipeCategory() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCategoryEdit);
        String recipeCategory = edit.getText().toString();
        return recipeCategory;
    }

    private String getRecipePrepTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextPrepTimeEdit);
        String recipePrep = edit.getText().toString();
        return recipePrep;
    }

    private String getRecipeCookTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCookTimeEdit);
        String recipeCook = edit.getText().toString();
        return recipeCook;
    }

    private String getRecipeServings() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextServingsEdit);
        String recipeServings = edit.getText().toString();
        return recipeServings;
    }

    private String getRecipeCalories() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCaloriesEdit);
        String recipeCalories = edit.getText().toString();
        return recipeCalories;
    }

    /*
    //Getting recipe instrctions string on click
    private void receiveRecipeInstructions() {
        ImageButton instructionClick = (ImageButton) findViewById(R.id.addInstructionButtonEdit);
        instructionClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Getting text in edit text
                EditText edit =  (EditText) findViewById(R.id.firstInstructionEdit);
                String recipeInstruction = edit.getText().toString();

                //*** Insert method to save text to database

                //Clearing editText to ready for next entry
                edit.setText("");
            }
        });
    }


    //When ingredients "plus" is clicked
    private void ingredientPlusClicked() {
        ImageButton instructionClick = (ImageButton) findViewById(R.id.addIngredientButtonEdit);
        instructionClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Getting text in edit text
                EditText editName =  (EditText) findViewById(R.id.editTextIngredientNameEdit);
                EditText editQuantity =  (EditText) findViewById(R.id.editTextQuantityEdit);
                EditText editUnit =  (EditText) findViewById(R.id.editTextUnitEdit);

                //*** Insert method to save text to database

                //Adding the edit text fields to the array list
                receiveIngredientName();
                receiveIngredientQuantity();
                receiveIngredientUnit();

                addIngredientLayout();
                //Clearing editText to ready for next entry
                editName.setText("");
                editQuantity.setText("");
                editUnit.setText("");
            }
        });
    }

    private void deleteClicked() {
        ImageButton toDelete = (ImageButton) findViewById(R.id.deleteIngredientButtonEdit);
        toDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeIngredientChildren();
            }
        });
    }


    private String receiveIngredientName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextIngredientNameEdit);
        String ingredient = edit.getText().toString();

        ingredientNames.add(ingredient);
        return ingredient;
    }

    private String receiveIngredientQuantity() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextQuantityEdit);
        String recipeQuantity = edit.getText().toString();

        ingredientQuantitys.add(recipeQuantity);
        return recipeQuantity;
    }

    private String receiveIngredientUnit() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextUnitEdit);
        String recipeUnit = edit.getText().toString();

        ingredientUnits.add(recipeUnit);
        return recipeUnit;
    }
    */
    //-------------------------------------------------------

    //Creating "Are you sure you want to save alert"
    private void createAlertEdit () {
        onPause();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(getResources().getString(R.string.saveQuestion)).setCancelable(false).setPositiveButton(getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    //If "No clicked"
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        onResume();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    //If "Yes clicked"
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //****To implement: editing the recipe from database method
                        //Receiving the edited text name
                        EditText edit =  (EditText) findViewById(R.id.editTextNameEdit);
                        String recipeName = edit.getText().toString();
                        //Sending edited text name to view activity, and then starting viewRecipe

                        updateRecipe();

                        Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                        intent.putExtra("recipe_id", r.getId());     //Sending added recipe name
                        startActivity(intent);
                        finish();  //Ending edit activity
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.setTitle(getResources().getString(R.string.save));
        alert.show();
    }


    private void updateRecipe(){
        r.setName(getRecipeName());
        r.setNumServings(getRecipeServings());
        r.setNumCalories(getRecipeCalories());
        r.setPrepTime(getRecipePrepTime());
        r.setCookTime(getRecipeCookTime());
        r.setType(getRecipeType());
        r.setCategory(getRecipeCategory());
        // r.setDirections(getRecipeInstructions());
        // r.setIngredientMeasures(getIngredientAmounts();
        // r.getIngredientUnits(), getIngredientNames());
        }
    }

    /*
    //Method to add the ingredient horizontal layout
    public void addIngredientLayout() {
        //Getting id of parent layout to add the inner horizontal layout to
        LinearLayout layoutToAdd = (LinearLayout) findViewById(R.id.LayoutToAddIngredientsEdit);

        //add LinearLayout Horizontal
        LinearLayout myLInearLayout = new LinearLayout(this);
        //add LayoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myLInearLayout.setOrientation(LinearLayout.HORIZONTAL);
        myLInearLayout.setLayoutParams(params);

        //add TextView for ingredient name
        LinearLayout.LayoutParams lparms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lparms.weight = 1.0f;
        lparms.gravity = Gravity.CENTER;
        TextView ingredientName = new TextView(this);
        ingredientName.setLayoutParams(lparms);
        EditText edit =  (EditText) findViewById(R.id.editTextIngredientNameEdit);
        String ingredientNameAdded = edit.getText().toString();
        ingredientName.setText(ingredientNameAdded);

        //add Textview for measure
        TextView ingredientMeasure = new TextView(this);
        ingredientMeasure.setLayoutParams(lparms);
        EditText editQuantity =  (EditText) findViewById(R.id.editTextQuantityEdit);
        String ingredientQuantity = editQuantity.getText().toString();
        EditText editMeasure =  (EditText) findViewById(R.id.editTextUnitEdit);
        String ingredientUnit = editMeasure.getText().toString();
        ingredientMeasure.setText(ingredientQuantity+" "+ingredientUnit);


        //Add textviews to horizontal layout
        myLInearLayout.addView(ingredientName);
        myLInearLayout.addView(ingredientMeasure);
        //add horizontal layout to parent linear layout
        layoutToAdd.addView(myLInearLayout);
    }


    //Method to delete all of the user's entered items
    private void removeIngredientChildren() {
        LinearLayout layoutToRemoveChildren = (LinearLayout) findViewById(R.id.LayoutToAddIngredientsEdit);
        layoutToRemoveChildren.removeAllViews();

        ingredientNames.clear();
        ingredientUnits.clear();
        ingredientQuantitys.clear();
    }

    private void updateIngredientFields() {
        LinearLayout layoutToAdd = (LinearLayout) findViewById(R.id.LayoutToAddIngredientsEdit);

        if (ingredientQuantitys == null) {
            return;
        }
        else if (ingredientQuantitys.size()>=1) {
            for (int i=0; i<ingredientQuantitys.size(); i++){
                //add LinearLayout Horizontal
                LinearLayout myLInearLayout = new LinearLayout(this);
                //add LayoutParams
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                myLInearLayout.setOrientation(LinearLayout.HORIZONTAL);
                myLInearLayout.setLayoutParams(params);

                //add TextView for ingredient name
                LinearLayout.LayoutParams lparms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lparms.weight = 1.0f;
                lparms.gravity = Gravity.CENTER;
                TextView ingredientName = new TextView(this);
                ingredientName.setLayoutParams(lparms);
                String ingredientNameAdded = ingredientNames.get(i);
                ingredientName.setText(ingredientNameAdded);

                //add Textview for measure
                TextView ingredientMeasure = new TextView(this);
                ingredientMeasure.setLayoutParams(lparms);
                String ingredientQuantity = ingredientQuantitys.get(i);
                String ingredientUnit = ingredientUnits.get(i);
                ingredientMeasure.setText(ingredientQuantity+" "+ingredientUnit);


                //Add textviews to horizontal layout
                myLInearLayout.addView(ingredientName);
                myLInearLayout.addView(ingredientMeasure);
                //add horizontal layout to parent linear layout
                layoutToAdd.addView(myLInearLayout);
            }
        }
    }
    */

}
