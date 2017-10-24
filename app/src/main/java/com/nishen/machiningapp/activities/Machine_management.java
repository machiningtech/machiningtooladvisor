package com.nishen.machiningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nishen.machiningapp.R;
import com.nishen.machiningapp.helpers.DatabaseAccess;
import com.nishen.machiningapp.adapters.machineArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Machine_management extends AppCompatActivity {
    EditText Power;
    EditText Name;
    Button AddMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Machine Management");
        setContentView(R.layout.activity_machine_management);

        TextView machinePowerHeader = (TextView)findViewById(R.id.Machine_power_header);
        machinePowerHeader.setText(Html.fromHtml("Power <small><b><em>kW</em></b></small>"));


        final ListView MachinesList = (ListView)findViewById(R.id.MyMachinesList);
        ArrayList<HashMap<String, String>> MyMachineList = new ArrayList<HashMap<String, String>>();
        DatabaseAccess machinesDB = DatabaseAccess.getInstance(getApplicationContext());
        machinesDB.open();
        Cursor MyMachineCursor = machinesDB.getMyMachines();
        MyMachineCursor.moveToFirst();
        while (!MyMachineCursor.isAfterLast()) {
            HashMap<String, String> machine = new HashMap<>();
            //add each value to temporary hashmap
            machine.put("Name", MyMachineCursor.getString(0));
            machine.put("Power", MyMachineCursor.getString(1));
            //add machine to machine list
            MyMachineList.add(machine);
            MyMachineCursor.moveToNext();
        }
        MyMachineCursor.close();

        final machineArrayAdapter machineArrayAdapter = new machineArrayAdapter(getApplicationContext(),Machine_management.this, MyMachineList);
        MachinesList.setAdapter(machineArrayAdapter);
        machineArrayAdapter.notifyDataSetChanged();





        Name = (EditText)findViewById(R.id.MachineName);


        Power = (EditText)findViewById(R.id.MachinePower);


        AddMachine = (Button)findViewById(R.id.addMachine);
        AddMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String power = Power.getText().toString();
                String powerKW = Double.toString(Double.parseDouble(power)*1000);

                if (name.trim().equals("") || power.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill in the details", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.setMachine(name, powerKW);
                    databaseAccess.close();
                    startMachineManagement();

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    Toast.makeText(getApplicationContext(), "Added machine", Toast.LENGTH_SHORT).show();
                }
            }
        });

    } //OnCreate

    public void startMachineManagement(){
        Intent intent = new Intent(this, Machine_management.class);
        startActivity(intent);
        finish();
    }



}
