package com.nishen.machiningapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import static com.nishen.machiningapp.R.id.material_spinner;
import static com.nishen.machiningapp.R.id.textView;

public class Face_input extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner material_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_input);

/**  Function to load the spinner data from SQLite database */

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


        TextView cut_length = (TextView) findViewById(R.id.cut_length);





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

