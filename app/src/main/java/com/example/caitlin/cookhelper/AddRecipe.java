package com.example.caitlin.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {
    /*
    private LinearLayout instructionLL;
    private EditText instructionET;
    private ImageButton addInstructionB;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        /*
        instructionLL = (LinearLayout) findViewById(R.id.instructionLinearLayout);
        instructionET = (EditText) findViewById(R.id.firstInstruction);
        addInstructionB = (ImageButton) findViewById(R.id.addInstructionButton);
        */
    }
    /*
    public void onAddNewInstructionClick(View view) {
        int index = instructionLL.indexOfChild(addInstructionB);
        instructionLL.addView(createNewEditText(),index);
    }
*/
    public void addRecipe(View v) {
    }


    private EditText createNewEditText(){
        EditText newET = new EditText(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        newET.setLayoutParams(params);
        newET.setHint("Enter the next instruction");
        return newET;
    }

}
