package com.example.caitlin.cookhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.caitlin.cookhelper.database.SearchResult;

import java.util.ArrayList;

/**
 * Created by ZaidK on 2016-12-01.
 */

public class IngredientAdapter extends ArrayAdapter<SearchResult> {
    public IngredientAdapter(Context context, ArrayList<SearchResult> results) {
        super(context, 0, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SearchResult result = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.rItemName);
        // Populate the data into the template view using the data object
        tvName.setText(result.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}