package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.caitlin.cookhelper.R.id.txtviewSearchCriteria;
import static com.example.caitlin.cookhelper.R.id.txtviewSearchCriteriaInfo;
import static java.lang.String.valueOf;

public class Results extends AppCompatActivity {

    //String to store the selected recipe's name (To send to view activity)
    String selectedRecipe;

    //Temporary filler string to be used for demo purposes **NOT ACTUAL RECIPES**
    String[] recipes = {"Lasagna","Poutine","Orange chicken","Chicken Noodle Soup","Chicken Pot Pie","Tuna Salad",
            "Butternut Squash Soup","Alphabet Soup","Duck Soup","Cinnamon Toast Crunch cereal","Cheesy Sauce Pasta",
            "Cheese Pizza","General Tao Chicken","Rice wraps","Breaded shrimp","Beef Pho","Spicy Beef strips",
            "Breaded Chicken","Buttermilk biscuit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //List View list population
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipes);
        ListView theListView = (ListView) findViewById(R.id.recipesListView);
        theListView.setAdapter(theAdapter);

        //Receiving sent recipe name from previous activity
        Bundle bundle = getIntent().getExtras();
        String searchType = bundle.getString("search_type");

        //setting sent recipe name as title
        TextView recipeNameToChange = (TextView) findViewById(txtviewSearchCriteria);
        if (searchType.equals("Browse")) {
            recipeNameToChange.setText(getString(R.string.resultsSubtitleBrowse));
        }
        else if (searchType.equals("Find")) {
            recipeNameToChange.setText(getString(R.string.resultsSubtitleFind));

            //receiving find criteria
            Bundle find = getIntent().getExtras();
            String searchCriteria = find.getString("search_criteria");
            //setting find criteria text
            TextView recipeSubtitleNameToChange = (TextView) findViewById(txtviewSearchCriteriaInfo);
            recipeSubtitleNameToChange.setText(searchCriteria);
        }

        //Intent to go to recipe view screen
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedRecipe = valueOf(adapterView.getItemAtPosition(position));
                //Intent to go to the next screen
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_name", selectedRecipe);     //Sending selected recipe name
                startActivity(intent);
            }
        });
    }
}
