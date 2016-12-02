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

public class Results extends AppCompatActivity {

    // ---------------
    // VARIABLES
    // ---------------

    // associations
    RecipeBook rBook;

    // ---------------
    // ON CREATE
    // ---------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rBook = RecipeBook.getInstance();
        ArrayList<String> criteria = getIntent().getExtras().getStringArrayList("criteria");
        final ArrayList<SearchResult> results = rBook.searchWithCriteria(getApplicationContext(),
                criteria.get(0), criteria.get(1), criteria.get(2));


        //List View list population
        IngredientAdapter adapter = new IngredientAdapter(this, results);
        ListView listView = (ListView) findViewById(R.id.recipesListView);
        listView.setAdapter(adapter);

        //Receiving sent search type from previous activity
        Bundle bundle = getIntent().getExtras();
        String searchType = bundle.getString("search_type");

        //setting sent search type name as sub title
        TextView recipeNameToChange = (TextView) findViewById(R.id.txtviewSearchCriteria);
        if (searchType.equals("Browse")) {
            recipeNameToChange.setText(getString(R.string.resultsSubtitleBrowse));
        }
        else if (searchType.equals("Find")) {
            recipeNameToChange.setText(getString(R.string.resultsSubtitleFind));

            //receiving find criteria
            Bundle find = getIntent().getExtras();
            String searchCriteria = find.getString("search_criteria");
            //setting find criteria text
            TextView recipeSubtitleNameToChange = (TextView) findViewById(R.id.txtviewSearchCriteriaInfo);
            recipeSubtitleNameToChange.setText(searchCriteria);
        }

        //Intent to go to recipe view screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SearchResult selectedRecipeSearch = results.get(position);
                //Intent to go to the next screen
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_id", selectedRecipeSearch.getId());     //Sending selected recipe name
                startActivity(intent);
            }
        });
    }
}
