package com.example.caitlin.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewRecipe extends AppCompatActivity {
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        //Receiving sent recipe name from previous activity
        Bundle bundle = getIntent().getExtras();
        recipeName = bundle.getString("recipe_name");

        //setting sent recipe name as title
        TextView recipeNameToChange = (TextView) findViewById(R.id.recipeName);
        recipeNameToChange.setText(recipeName);

        //Button clicking methods
        toEdit();
        toDelete();
    }

    private void toEdit() {
        Button toEdit = (Button) findViewById(R.id.btnEdit);
        toEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Edit.class);
                intent.putExtra("recipe_name", recipeName);     //Sending selected recipe name
                startActivity(intent);
                finish();
            }
        });
    }

    private void toDelete() {
        Button toDelete = (Button) findViewById(R.id.btnDelete);
        toDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //****To implement: Deleting the recipe
                onBackPressed();
            }
        });
    }


}
