package com.nishen.machiningapp;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import static android.content.Intent.getIntent;

/**
 * Created by Nishen on 2017/09/19.
 */

public class Tool_filter_results extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_filter_results);


// Grab Intent data from input field
        Bundle tool_search_bundle = getIntent().getExtras();
        String cut_profile = tool_search_bundle.getString("profile");


// Populate results from database searching
        ListView tool_results_listview = (ListView) findViewById(R.id.toolList);
        DatabaseAccess tool_search_db = DatabaseAccess.getInstance(this);
        tool_search_db.open();
        List<String> tool_search_list = tool_search_db.FilterTools(cut_profile);
        ArrayAdapter<String> cnr_radius_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tool_search_list);
        tool_results_listview.setAdapter(cnr_radius_adapter);
        tool_search_db.close();


    }


}