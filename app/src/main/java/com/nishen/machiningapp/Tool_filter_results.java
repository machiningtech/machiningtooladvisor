package com.nishen.machiningapp;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.getIntent;

/**
 * Created by Nishen on 2017/09/19.
 */

public class Tool_filter_results extends AppCompatActivity {

    ArrayList<HashMap<String, String>> filteredTools;
    String material;
    ListView tool_results_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_filter_results);
        material = new String();
        filteredTools = new ArrayList<>();

// Grab Intent data from input field
        Bundle tool_search_bundle = getIntent().getExtras();
        String cut_profile = tool_search_bundle.getString("profile");
        material = tool_search_bundle.getString("material");
        //String cut_length = tool_search_bundle.getString("cut_length");
        //String cut_width = tool_search_bundle.getString("cut_width");
        //String cut_depth = tool_search_bundle.getString("cut_depth");
        //String max_corner_radius = tool_search_bundle.getString("max_corner_radius");
        //String coolant = tool_search_bundle.getString("coolant");
        //String clamping = tool_search_bundle.getString("clamping");
        //String operation_type = tool_search_bundle.getString("operation_type");
        //String machine = tool_search_bundle.getString("machine");


 /** Populating Tool results listview **/
        tool_results_list= (ListView) findViewById(R.id.toolList);
        View tool_header = getLayoutInflater().inflate(R.layout.tool_header, null);
        tool_results_list.addHeaderView(tool_header);
        DatabaseAccess tool_search_db = DatabaseAccess.getInstance(this);
        tool_search_db.open();
        //Parse input data for database query
        Cursor tool_search_list = tool_search_db.FilterToolsCursor(cut_profile, material);
        //Parse input data for database query
        tool_search_list.moveToFirst();
        while (!tool_search_list.isAfterLast()) {
            String Name = tool_search_list.getString(0);
            String Diameter = tool_search_list.getString(1);
            String CuttingLength = tool_search_list.getString(2);
            String FluteNumber = tool_search_list.getString(3);
            HashMap<String, String> tool = new HashMap<>();
            //add each value to temporary hashmap
            tool.put("Name", Name);
            tool.put("Diameter", Diameter);
            tool.put("CuttingLength", CuttingLength);
            tool.put("FluteNumber", FluteNumber);
            filteredTools.add(tool);
            tool_search_list.moveToNext();
        }
            tool_search_list.close();
            filteredToolsAdapter cnr_radius_adapter = new filteredToolsAdapter(this, filteredTools);
            tool_results_list.setAdapter(cnr_radius_adapter);
            tool_search_db.close();
/** Populating Tool results listview **/


        TextView testView = (TextView) findViewById(R.id.testview);
        testView.setText(material);


    }


}