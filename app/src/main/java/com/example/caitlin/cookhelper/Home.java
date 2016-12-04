package com.example.caitlin.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Methods for button clicks to appropriate activities
        goToFind();
        goToResults();
        goToAdd();
        goToHelp();
    }

    //Onclick method for find button
    private void goToFind() {
        Button toHelp = (Button) findViewById(R.id.btnFindRecipe);
        toHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindRecipe.class);
                startActivity(intent);
            }
        });
    }

    //Onclick method for Browse button
    private void goToResults() {
        Button toAdd = (Button) findViewById(R.id.btnBrowseRecipe);
        toAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Results.class);
                intent.putExtra("search_type", "Browse");     //Sending selected recipe name
                startActivity(intent);
            }
        });
    }

    //Onclick method for Add button
    private void goToAdd() {
        Button toFind = (Button) findViewById(R.id.btnAddRecipe);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddRecipe.class);
                startActivity(intent);
            }
        });
    }

    //Onclick method for Help button
    private void goToHelp() {
        Button toFind = (Button) findViewById(R.id.btnHelp);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        onPause();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(getResources().getString(R.string.pauseHomeQuestion)).setCancelable(false).setPositiveButton(getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        onResume();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        System.exit(0);
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.setTitle(getResources().getString(R.string.exit));
        alert.show();
    }
}
