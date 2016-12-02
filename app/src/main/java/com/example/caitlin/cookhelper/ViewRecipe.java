package com.example.caitlin.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

        //Receiving recipe from RecipeBook façade
        r = rBook.getRecipe(getApplicationContext(), recipe_id);
        //Populates every View fields
        masterView(r);

        //Button clicking methods
        toEdit();
        toDelete();
    }

    //Click listener for Edit activity
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

    //Click listener for Delete activity
    private void toDelete() {
        Button toDelete = (Button) findViewById(R.id.btnDelete);
        toDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAlert();
                //Calls on method in façade class RecipeBook to delete the selected recipe by ID
                rBook.deleteRecipe(getApplicationContext(), r.getId());
            }
        });
    }

    //User alert to confirm or prevent deletion of recipe
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
    View setters for layout fields
     */

    //Sets the Title field of the recipe to be displayed to the stored value
    private void setTitle(Recipe recipe){
        String recipeTitle = recipe.getName();
        TextView resultsTitle = (TextView) findViewById(R.id.recipeName);
        resultsTitle.setText(recipeTitle);
    }

    //Sets the Serving field of the recipe to be displayed to the stored value
    private void setServing(Recipe recipe){
        String recipeServing = String.valueOf(recipe.getNumServings());
        TextView resultsServing = (TextView) findViewById(R.id.txtviewRecipeServingsNumber);
        resultsServing.setText(recipeServing);
    }

    //Sets the Preparation field of the recipe to be displayed to the stored value
    private void setPrep(Recipe recipe){
        String recipePrep = recipe.getPrepTime();
        TextView resultsPrep = (TextView) findViewById(R.id.txtviewRecipePrepNumber);
        resultsPrep.setText(recipePrep);
    }

    //Sets the Cooking Time field of the recipe to be displayed to the stored value
    private void setCookTime(Recipe recipe){
        String recipeCookTime = recipe.getCookTime();
        TextView resultsCookTime = (TextView) findViewById(R.id.txtviewCookTimeNumber);
        resultsCookTime.setText(recipeCookTime);
    }

    //Sets the Type field of the recipe to be displayed to the stored value
    private void setType(Recipe recipe){
        String recipeType = recipe.getType();
        TextView resultsType = (TextView) findViewById(R.id.viewRecipeTypeInfo);
        resultsType.setText(recipeType);
    }

    //Sets the Calories field of the recipe to be displayed to the stored value
    private void setCalories(Recipe recipe){
        String recipeCalories = String.valueOf(recipe.getNumCalories());
        TextView resultsCalories = (TextView) findViewById(R.id.viewRecipeCaloriesInfo);
        resultsCalories.setText(recipeCalories);
    }

    //Sets the Category field of the recipe to be displayed to the stored value
    private void setCategory(Recipe recipe){
        String recipeCategory = recipe.getCategory();
        TextView resultsCategory = (TextView) findViewById(R.id.viewRecipeCategoryInfo);
        resultsCategory.setText(recipeCategory);
    }

    //Sets the Ingredients field of the recipe to be displayed to the stored values
    //Converts the IngredientMeasure objects to String for display purposes
    private void setIngredients(Recipe recipe){
        ArrayList<IngredientMeasure> recipeIngredients = recipe.getIngredientMeasures();
        LinearLayout ll = (LinearLayout) findViewById(R.id.viewRecipeIngredientsInfo);

        for(int i = 0; i < recipeIngredients.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(recipeIngredients.get(i).toString());
            ll.addView(tv);
        }
    }

    /*
    * Sets the Directions field of the recipe to be displayed to the stored values
    * @param recipe Recipe to be displayed
     */
    private void setDirections(Recipe recipe){
        ArrayList<String> recipeDirections = recipe.getDirections();
        LinearLayout ll = (LinearLayout) findViewById(R.id.viewRecipeDirectionsInfo);

        for(int i = 0; i < recipeDirections.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(recipeDirections.get(i));
            ll.addView(tv);
        }
    }

    /*
    * Master method to call every view setters
    * @param recipe Recipe to be displayed
     */
    private void masterView(Recipe recipe){
        setTitle(recipe);
        setServing(recipe);
        setPrep(recipe);
        setCookTime(recipe);
        setType(recipe);
        setCalories(recipe);
        setCategory(recipe);
        setIngredients(recipe);
        setDirections(recipe);
    }
}