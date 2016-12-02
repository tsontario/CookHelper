package com.example.caitlin.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewRecipe extends AppCompatActivity {

    RecipeBook rBook;
    Recipe r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        rBook = RecipeBook.getInstance();

        //Receiving sent recipe name from previous activity
        Intent intent = getIntent();
        long recipe_id = intent.getLongExtra("recipe_id", 0l);

        r = rBook.getRecipe(getApplicationContext(), recipe_id);


        //setting sent recipe name as title
        TextView recipeNameToChange = (TextView) findViewById(R.id.recipeName);
        // recipeNameToChange.setText(recipeName);

        //Button clicking methods
        toEdit();
        toDelete();
    }

    private void toEdit() {
        Button toEdit = (Button) findViewById(R.id.btnEdit);
        toEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Edit.class);
                intent.putExtra("recipe_id", r.getId());     //Sending selected recipe name
                startActivity(intent);
                finish();
            }
        });
    }

    private void toDelete() {
        Button toDelete = (Button) findViewById(R.id.btnDelete);
        toDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                createAlert();
            }
        });
    }


    private void createAlert () {
        onPause();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(getResources().getString(R.string.deleteQuestion)).setCancelable(false).setPositiveButton(getResources().getString(R.string.no),
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
                        //****To implement: Deleting the recipe from database method
                        onBackPressed();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.setTitle(getResources().getString(R.string.delete));
        alert.show();
    }


    /*
    View setters
    ********** public or private? ************
     */
    private void setTitle(Recipe recipe){
        String recipeTitle = recipe.getName();
        TextView resultsTitle = (TextView) findViewById(R.id.recipeName);
        resultsTitle.setText(recipeTitle);
    }

    private void setServing(Recipe recipe){
        String recipeServing = String.valueOf(recipe.getNumServings());
        TextView resultsServing = (TextView) findViewById(R.id.txtviewRecipeServingsNumber);
        resultsServing.setText(recipeServing);
    }

    private void setPrep(Recipe recipe){
        String recipePrep = recipe.getPrepTime();
        TextView resultsPrep = (TextView) findViewById(R.id.txtviewRecipePrepNumber);
        resultsPrep.setText(recipePrep);
    }

    private void setCookTime(Recipe recipe){
        String recipeCookTime = recipe.getCookTime();
        TextView resultsCookTime = (TextView) findViewById(R.id.txtviewCookTimeNumber);
        resultsCookTime.setText(recipeCookTime);
    }

    private void setType(Recipe recipe){
        String recipeType = recipe.getType();
        TextView resultsType = (TextView) findViewById(R.id.viewRecipeTypeInfo);
        resultsType.setText(recipeType);
    }

    private void setCalories(Recipe recipe){
        String recipeCalories = String.valueOf(recipe.getNumCalories());
        TextView resultsCalories = (TextView) findViewById(R.id.viewRecipeCaloriesInfo);
        resultsCalories.setText(recipeCalories);
    }

    /**
    private void setIngredients(Recipe recipe){
        ArrayList<IngredientMeasure> recipeIngredients = recipe.getIngredientMeasures();
        TextView resultsIngredients = (TextView) findViewById(R.id.viewRecipeIngredientsInfo);
        for(IngredientMeasure i : recipeIngredients)
            resultsIngredients.append(i.toString() + "\n");
    }

    private void setDirections(Recipe recipe){
        ArrayList<String> recipeDirections = recipe.getDirections();
        TextView resultsDirections = (TextView) findViewById(R.id.viewRecipeDirectionsInfo);
        for(String i : recipeDirections)
            resultsDirections.append(i + "\n");
    }
     */

}
