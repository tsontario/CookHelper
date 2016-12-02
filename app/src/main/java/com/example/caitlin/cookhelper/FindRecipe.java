package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.caitlin.cookhelper.database.DatabaseHandler;

import java.util.ArrayList;

public class FindRecipe extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);

        //Onclick listeners for the buttons
        goToCancel();
        goToSearch();

        //Populating spinners
        populateCategorySpinner();
        populateTypeSpinner();
    }

    //Onclick method for Search button
    private void goToSearch() {
        Button toSearch = (Button) findViewById(R.id.findRecipeButton);
        toSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                ArrayList<String> allCriteria = new ArrayList<String>();
                allCriteria.add(getSpinnerCategory());
                allCriteria.add(getSpinnerType());
                allCriteria.add(ingredientCriteria());

                Intent intent = new Intent(getApplicationContext(), Results.class);
                intent.putStringArrayListExtra("criteria", allCriteria);
                intent.putExtra("search_type", "Find_with_criteria");
                startActivity(intent);
            }
        });
    }

    private String ingredientCriteria() {
        EditText edit =  (EditText) findViewById(R.id.editTextFind);
        String findCriteria = edit.getText().toString();

        return findCriteria;
    }

    private void populateTypeSpinner() {
        DatabaseHandler db = new DatabaseHandler(this);
        String[] categories = receiveSpinnerInfo(db.getCategories());

        //Populating category spinner
        Spinner spinnerType = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<String> adapterType=
                new ArrayAdapter<String>(FindRecipe.this,android.R.layout.simple_spinner_item, categories);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
    }

    private void populateCategorySpinner() {
        DatabaseHandler db = new DatabaseHandler(this);
        String[] types = receiveSpinnerInfo(db.getTypes());

        //Populating category spinner
        Spinner spinnerCategory = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adapterCategory=
                new ArrayAdapter<String>(FindRecipe.this,android.R.layout.simple_spinner_item, types);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);
    }

    //Onclick method for cancel
    private void goToCancel() {
        Button toCancel = (Button) findViewById(R.id.cancelFindButton);
        toCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //Will return the selected item from the "category" spinner
    private String getSpinnerCategory() {
        Spinner spinner = (Spinner)findViewById(R.id.categorySpinner);
        String selectedText = spinner.getSelectedItem().toString();

        return selectedText;
    }

    //Will return the selected item from the "type" spinner
    private String getSpinnerType() {
        Spinner spinner = (Spinner)findViewById(R.id.typeSpinner);
        String selectedText = spinner.getSelectedItem().toString();

        return selectedText;
    }

    //Method to change the database returned ArrayList
    private String[] receiveSpinnerInfo(ArrayList<String> databaseList) {

        //Creating a string array to store the filtered list values into
        String[] filteredString = new String[databaseList.size()];

        //Copying filtered list values to the String array
        for (int i=0; i<databaseList.size();i++) {
            filteredString[i] = databaseList.get(i);
        }

        return filteredString;
    }
}
