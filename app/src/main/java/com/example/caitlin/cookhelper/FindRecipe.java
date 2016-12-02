package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
                //Getting the edit text for search criteria
                String findCriteria = ingredientCriteria();

                Intent intent = new Intent(getApplicationContext(), Results.class);
                intent.putExtra("search_type", "Find");     //Sending selected recipe name
                intent.putExtra("search_criteria", findCriteria);     //Sending find criteria
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
        //Populating category spinner
        Spinner spinnerType = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterCategory);
    }

    private void populateCategorySpinner() {
        //Populating category spinner
        Spinner spinnerCategory = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this,
                R.array.Category, android.R.layout.simple_spinner_item);
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
        //New arraylist with removed white/nullspaces
        ArrayList<String> filteredList = new ArrayList<String>();

        //Check for white spaces and null spaces and remove
        for (int i=0; i<databaseList.size(); i++) {
            if ((databaseList.get(i)!="")&&(databaseList.get(i)!=null)) {
                filteredList.add(databaseList.get(i));
            }
        }

        //Creating a string array to store the filtered list values into
        String[] filteredString = new String[filteredList.size()];

        //Copying filtered list values to the String array
        for (int i=0; i<filteredList.size();i++) {
            filteredString[i] = filteredList.get(i);
        }

        return filteredString;
    }
}
