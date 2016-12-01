package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Edit extends AppCompatActivity {

    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //receiving recipe name
        Bundle bundle = getIntent().getExtras();
        recipeName = bundle.getString("recipe_name");
        //Setting recipe name
        EditText recipeNameToChange = (EditText) findViewById(R.id.editTextNameEdit);
        recipeNameToChange.setText(recipeName);

        //Calling on click methods
        toSave();
        toCancel();
    }

    private void toSave() {
        Button toEdit = (Button) findViewById(R.id.saveRecipeButtonEdit);
        toEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Receiving the edited text name
                EditText edit =  (EditText) findViewById(R.id.editTextNameEdit);
                String recipeName = edit.getText().toString();
                //Sending edited text name to view activity, and then starting viewRecipe
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_name", recipeName);     //Sending added recipe name
                startActivity(intent);
                finish();  //Ending edit activity
            }
        });
    }

    private void toCancel() {
        Button toEdit = (Button) findViewById(R.id.cancelEditButton);
        toEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Sending original text name to view activity, and then starting viewRecipe
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_name", recipeName);     //Sending added recipe name
                startActivity(intent);
                finish();   //Ending edit activity
            }
        });
    }

    //--------------------- Methods to get edittext text from textfields ---------------------------
    private String receiveRecipeName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextNameEdit);
        String recipeName = edit.getText().toString();
        return recipeName;
    }

    private String receiveRecipeType() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextTypeEdit);
        String recipeType = edit.getText().toString();
        return recipeType;
    }

    private String receiveRecipeCategory() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCategoryEdit);
        String recipeCategory = edit.getText().toString();
        return recipeCategory;
    }

    private String receiveRecipePrepTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextPrepTimeEdit);
        String recipePrep = edit.getText().toString();
        return recipePrep;
    }

    private String receiveRecipeCookTime() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCookTimeEdit);
        String recipeCook = edit.getText().toString();
        return recipeCook;
    }

    private String receiveRecipeServings() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextServingsEdit);
        String recipeServings = edit.getText().toString();
        return recipeServings;
    }

    private String receiveRecipeCalories() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextCaloriesEdit);
        String recipeCalories = edit.getText().toString();
        return recipeCalories;
    }

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

    private void receiveRecipeIngredients() {
        ImageButton ingredientClick = (ImageButton) findViewById(R.id.addIngredientButtonEdit);
        ingredientClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //***ON Click for ingredients button
                String ingredientName = receiveIngredientName();

                //**METHOD to add ingredient name to database

                //Clearing text for next entry
                EditText edit =  (EditText) findViewById(R.id.firstIngredientEdit);
                edit.setText("");
            }
        });
    }

    private String receiveIngredientName() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.firstIngredientEdit);
        String ingredient = edit.getText().toString();
        return ingredient;
    }

    private String receiveIngredientQuantity() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextQuantityEdit);
        String recipeQuantity = edit.getText().toString();
        return recipeQuantity;
    }

    private String receiveIngredientUnit() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextUnitEdit);
        String recipeUnit = edit.getText().toString();
        return recipeUnit;
    }

    private String receiveIngredientMeasure() {
        //Getting edit text text
        EditText edit =  (EditText) findViewById(R.id.editTextMeasureEdit);
        String recipeMeasure = edit.getText().toString();
        return recipeMeasure;
    }
}
