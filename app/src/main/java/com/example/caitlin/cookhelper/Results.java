package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caitlin.cookhelper.database.SearchResult;

import java.util.ArrayList;

import static com.example.caitlin.cookhelper.R.id.txtviewSearchCriteria;
import static com.example.caitlin.cookhelper.R.id.txtviewSearchCriteriaInfo;

public class Results extends AppCompatActivity {

    //SearchResult object to store the selected searchResult object
    SearchResult selectedRecipeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        final ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        results.add(new SearchResult("Pasta",1));
        results.add(new SearchResult("Soup",2));
        results.add(new SearchResult("Chicken Dish",3));

        //List View list population
        IngredientAdapter adapter = new IngredientAdapter(this, results);
        ListView listView = (ListView) findViewById(R.id.recipesListView);
        listView.setAdapter(adapter);

        //Receiving sent search type from previous activity
        Bundle bundle = getIntent().getExtras();
        String searchType = bundle.getString("search_type");

        //setting sent search type name as sub title
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedRecipeSearch = results.get(position);
                //Intent to go to the next screen
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_id", selectedRecipeSearch.getId());     //Sending selected recipe name
                startActivity(intent);
            }
        });
    }
}
