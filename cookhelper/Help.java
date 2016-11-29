package com.example.caitlin.cookhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Setting the click listener for the help textViews
        howToFind();
        howToView();
        howToAdd();
        howToEdit();
        howToDelete();
    }

    private void howToFind() {
        final TextView txtViewCategory = (TextView) findViewById(R.id.txtviewHowToFindDescription);
        TextView toFind = (TextView) findViewById(R.id.txtHowToFind);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtViewCategory.getVisibility()==View.VISIBLE) {
                    txtViewCategory.setVisibility(View.INVISIBLE);
                    txtViewCategory.setText("");
                }
                else {
                    txtViewCategory.setVisibility(View.VISIBLE);
                    txtViewCategory.setText(getString(R.string.how_to_find_instructions));
                }
            }
        });
    }

    private void howToView() {
        final TextView txtViewCategory = (TextView) findViewById(R.id.txtviewHowToViewDescription);
        TextView toFind = (TextView) findViewById(R.id.txtviewHowToView);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtViewCategory.getVisibility()==View.VISIBLE) {
                    txtViewCategory.setVisibility(View.INVISIBLE);
                    txtViewCategory.setText("");
                }
                else {
                    txtViewCategory.setVisibility(View.VISIBLE);
                    txtViewCategory.setText(getString(R.string.how_to_browse_instructions));
                }
            }
        });
    }

    private void howToAdd() {
        final TextView txtViewCategory = (TextView) findViewById(R.id.txtviewHowToAddDescription);
        TextView toFind = (TextView) findViewById(R.id.txtviewHowToAdd);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtViewCategory.getVisibility()==View.VISIBLE) {
                    txtViewCategory.setVisibility(View.INVISIBLE);
                    txtViewCategory.setText("");
                }
                else {
                    txtViewCategory.setVisibility(View.VISIBLE);
                    txtViewCategory.setText(getString(R.string.how_to_add_instructions));
                }
            }
        });
    }

    private void howToEdit() {
        final TextView txtViewCategory = (TextView) findViewById(R.id.txtviewHowToEditDescription);
        TextView toFind = (TextView) findViewById(R.id.txtviewHowToEdit);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtViewCategory.getVisibility()==View.VISIBLE) {
                    txtViewCategory.setVisibility(View.INVISIBLE);
                    txtViewCategory.setText("");
                }
                else {
                    txtViewCategory.setVisibility(View.VISIBLE);
                    txtViewCategory.setText(getString(R.string.how_to_edit_instructions));
                }
            }
        });
    }

    private void howToDelete() {
        final TextView txtViewCategory = (TextView) findViewById(R.id.txtviewHowToDeleteDescription);
        TextView toFind = (TextView) findViewById(R.id.txtviewHowToDelete);
        toFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtViewCategory.getVisibility()==View.VISIBLE) {
                    txtViewCategory.setVisibility(View.INVISIBLE);
                    txtViewCategory.setText("");
                }
                else {
                    txtViewCategory.setVisibility(View.VISIBLE);
                    txtViewCategory.setText(getString(R.string.how_to_delete_instructions));
                }
            }
        });
    }
}
