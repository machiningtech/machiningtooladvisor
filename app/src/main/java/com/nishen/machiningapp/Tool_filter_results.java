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
        //String material = tool_search_bundle.getString("material");
        //String cut_length = tool_search_bundle.getString("cut_length");
        //String cut_width = tool_search_bundle.getString("cut_width");
        //String cut_depth = tool_search_bundle.getString("cut_depth");
        //String max_corner_radius = tool_search_bundle.getString("max_corner_radius");
        //String coolant = tool_search_bundle.getString("coolant");
        //String clamping = tool_search_bundle.getString("clamping");
        //String operation_type = tool_search_bundle.getString("operation_type");
        //String machine = tool_search_bundle.getString("machine");


// Populate results from database searching
        ListView tool_results_listview = (ListView) findViewById(R.id.toolList);
        DatabaseAccess tool_search_db = DatabaseAccess.getInstance(this);
        tool_search_db.open();
        //Parse input data for database query
        List<String> tool_search_list = tool_search_db.FilterTools(cut_profile);
        //Parse input data for database query
        ArrayAdapter<String> cnr_radius_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tool_search_list);
        tool_results_listview.setAdapter(cnr_radius_adapter);
        tool_search_db.close();


    }


}