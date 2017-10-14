package com.nishen.machiningapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Optimise_tools extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Html.fromHtml("Milling<big>&#8658</big>Slot<big>&#8658</big>Tools<big>&#8658</big>Optimise"));
        setContentView(R.layout.activity_optimise_tools);

        //int powerWeight = ((MachiningData)getApplicationContext()).getPowerWeight();
        String mat = ((MachiningData)getApplicationContext()).getSelectedMaterial();
        TextView testV = (TextView)findViewById(R.id.testV);
        //testV.setText(Integer.toString(powerWeight));
        testV.setText(mat);



    } //onCreate





    private class MachiningTOPSIS extends AsyncTask<Void, Void, Void> {

        //@Override
        //protected void onPreExecute() {}

        @Override
        protected Void doInBackground(Void...arg0) {


            ArrayList<HashMap<String, String>> filteredToolList = ((MachiningData)getApplicationContext()).getFilteredToolList();
            int Alternatives_rows = filteredToolList.size();
            int Criteria_columns = 5;

            ArrayList<HashMap<String, String>> TOPSIS_List = new ArrayList<>();
            double [] [] TOPSISmatrix = new double[Alternatives_rows][Criteria_columns];

            for (int row = 0; row <= Alternatives_rows; row++) {
                HashMap<String, String> ToolData = filteredToolList.get(row);
                HashMap<String, String> TOPSISelement = new HashMap<>();

                String Name = ToolData.get("Name");
                String Power = ToolData.get("CuttingPower");
                String Roughness = ToolData.get("Roughness");
                String Shear = ToolData.get("Shear");
                String ToolLife = ToolData.get("ToolLife");
                String MMR = ToolData.get("MMR");

                TOPSISmatrix[row][0] = Double.parseDouble(Power);
                TOPSISmatrix[row][1] = Double.parseDouble(Roughness);
                TOPSISmatrix[row][2] = Double.parseDouble(Shear);
                TOPSISmatrix[row][3] = Double.parseDouble(ToolLife);
                TOPSISmatrix[row][4] = Double.parseDouble(MMR);

                TOPSISelement.put(Name, Name);
                TOPSISelement.put(Power, Power);
                TOPSISelement.put(Roughness, Roughness);
                TOPSISelement.put(Shear, Shear);
                TOPSISelement.put(ToolLife, ToolLife);
                TOPSISelement.put(MMR, MMR);

            }


            for(int row = 0;row<Alternatives_rows;row++)
            {
                //TOPSIS_List.add(filteredToolList[row].get);
            }

            TOPSIS machiningTOPSIS = new TOPSIS();
            machiningTOPSIS.setAlternatives_rows(Alternatives_rows);
            machiningTOPSIS.setCriteria_columns(Criteria_columns);
            machiningTOPSIS.setTOPSISmatrix(TOPSISmatrix);




            return null;
        }

        //@Override
        //protected void onProgressUpdate() {}

        @Override
        protected void onPostExecute(Void result) {






        }
    }



}
