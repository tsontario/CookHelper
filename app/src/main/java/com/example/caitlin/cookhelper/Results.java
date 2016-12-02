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
    ArrayList<SearchResult> results;

    // ---------------
    // ON CREATE
    // ---------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rBook = RecipeBook.getInstance();

        // determine the type of search: search with criteria or browse all recipes
        Intent intent = getIntent();
        String searchType = intent.getExtras().getString("search_type");

        // references to TextViews
        TextView kindOfSearch = (TextView) findViewById(R.id.txtviewSearchCriteria);
        TextView inputtedIngInfo = (TextView) findViewById(R.id.txtviewSearchCriteriaInfo);

        results = new ArrayList<SearchResult>();

        if (searchType.equals("Browse")) {

            kindOfSearch.setText("Browse All Recipes");
            results = rBook.browseAllRecipes(getApplicationContext());
        } else { // searchType.equals("Criteria")

            kindOfSearch.setText("Showing results for:");
            ArrayList<String> allCriteria = intent.getExtras().getStringArrayList("criteria");
            inputtedIngInfo.setText("Category: " + allCriteria.get(0) +
                    ", Type: " + allCriteria.get(1) +
                    "\n" + "Ingredients: " + allCriteria.get(2));
            results = rBook.searchWithCriteria(getApplicationContext(),
                    allCriteria.get(0), allCriteria.get(1), allCriteria.get(2));
        }

        // ListView population
        IngredientAdapter adapter = new IngredientAdapter(this, results);
        ListView listView = (ListView) findViewById(R.id.recipesListView);
        listView.setAdapter(adapter);

        // intent to go to recipe view screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                SearchResult selectedRecipeSearch = results.get(position);
                Intent intent = new Intent(getApplicationContext(), ViewRecipe.class);
                intent.putExtra("recipe_id", selectedRecipeSearch.getId());
                startActivity(intent);
                finish();
            }
        });
    }

}
