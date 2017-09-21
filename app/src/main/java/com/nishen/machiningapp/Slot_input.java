package com.nishen.machiningapp;

/**
 * Created by Nishen on 2017/09/19.
 */

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import static com.nishen.machiningapp.R.id.material_spinner;
import static com.nishen.machiningapp.R.id.parent;
import static com.nishen.machiningapp.R.id.textView;


public class Slot_input extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner material_spinner;
    Spinner cnr_radius_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_input);

/**  Function to load the materials spinner data from SQLite database */

// Spinner element
        material_spinner = (Spinner) findViewById(R.id.material_spinner);
        //this.material_spinner = (Spinner) findViewById(R.id.material_spinner);   alternate
// database handler
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
// Spinner Drop down elements
        List<String> materials = databaseAccess.getMaterials();
// Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, materials);
// Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// attaching data adapter to spinner
        material_spinner.setAdapter(dataAdapter);
        //this.material_spinner.setAdapter(dataAdapter);    alternate
        databaseAccess.close();
/**  Function to load the materials spinner data from SQLite database */

//Corner radius dropdown spinner
        cnr_radius_spinner = (Spinner) findViewById(R.id.corner_radius_spinner);
        DatabaseAccess cnr_radius_db = DatabaseAccess.getInstance(this);
        cnr_radius_db.open();
        List<String> cnr_radius_list = cnr_radius_db.unique_corner_radius();
        cnr_radius_list.add(0, "Any");
        ArrayAdapter<String> cnr_radius_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cnr_radius_list);
        cnr_radius_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cnr_radius_spinner.setAdapter(cnr_radius_adapter);
        cnr_radius_db.close();



    }



      //  EditText cut_length = (EditText) findViewById(R.id.cut_length);



//create dummy variable to hold "slot"
//perform filtertools. send variable with "slot" to be parsed as parameter.

    public void searchtools (View view) {

        Intent filter_tools = new Intent(getApplicationContext(), Tool_filter_results.class);
        Bundle input_data_bundle = new Bundle();

        input_data_bundle.putString("profile", "slot");
        filter_tools.putExtras(input_data_bundle);



        startActivity(filter_tools);


    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
