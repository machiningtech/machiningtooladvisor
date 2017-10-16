package com.nishen.machiningapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Optimise_tools extends AppCompatActivity {
    ArrayList<HashMap<String, String>> filteredToolList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Html.fromHtml("Milling<big>&#8658</big>Slot<big>&#8658</big>Tools<big>&#8658</big>Optimise"));
        setContentView(R.layout.activity_optimise_tools);

        String mat = ((MachiningData)getApplicationContext()).getSelectedMaterial();

       //new MachiningTOPSIS().execute();

        filteredToolList = ((MachiningData)getApplicationContext()).getFilteredToolList();
        double [] CriteriaWeightingMatrix = ((MachiningData)getApplicationContext()).getCriteriaWeightingMatrix();
        int rows_alternatives = filteredToolList.size();
        int columns_criteria = 5;

        ArrayList<HashMap<String, String>> TOPSIS_List = new ArrayList<>();
        double [] [] TOPSISmatrix = new double[rows_alternatives][columns_criteria];

        for (int row = 0; row < rows_alternatives; row++) {
            HashMap<String, String> ToolData = filteredToolList.get(row);
            HashMap<String, String> TOPSISelement = new HashMap<>();

            String Name = ToolData.get("Name");
            String Power = ToolData.get("CuttingPower");
            String Roughness = ToolData.get("Roughness");
            String Shear = ToolData.get("Shear");
            String ToolLife = ToolData.get("ToolLife");
            String MMR = ToolData.get("MMR");

            TOPSISmatrix[row][0] = Double.parseDouble(Power);       //Must minimise
            TOPSISmatrix[row][1] = Double.parseDouble(Roughness);   //Must minimise
            TOPSISmatrix[row][2] = Double.parseDouble(Shear);       //Must minimise
            TOPSISmatrix[row][3] = Double.parseDouble(ToolLife);    //Must minimise
            TOPSISmatrix[row][4] = Double.parseDouble(MMR);         //Must maximise

            TOPSISelement.put("Name", Name);
            TOPSISelement.put("Power", Power);
            TOPSISelement.put("Roughness", Roughness);
            TOPSISelement.put("Shear", Shear);
            TOPSISelement.put("ToolLife", ToolLife);
            TOPSISelement.put("MMR", MMR);

            TOPSIS_List.add(TOPSISelement);
        }




        TOPSIS machiningTOPSIS = new TOPSIS();
        machiningTOPSIS.setrows_alternatives(rows_alternatives);
        machiningTOPSIS.setcolumns_criteria(columns_criteria);
        machiningTOPSIS.setTOPSISmatrix(TOPSISmatrix);
        machiningTOPSIS.setCriteriaWeightingMatrix(CriteriaWeightingMatrix);
        double [] UnorderedToolScores = machiningTOPSIS.calculate();


        //TODO convert closeness coefficient table into full data table to display.
        for (int row = 0; row < rows_alternatives; row++) {
            HashMap<String, String> ToolData = TOPSIS_List.get(row);
            double toolScore = UnorderedToolScores[row];
            ToolData.put("Score", Double.toString(toolScore));
        }




        ListView testV = (ListView) findViewById(R.id.testList);
        testArrayAdapter adapter = new testArrayAdapter(this,TOPSIS_List);
        testV.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    } //onCreate





    /**private class MachiningTOPSIS extends AsyncTask<Void, Void, Void> {

        //@Override
        //protected void onPreExecute() {}

        @Override
        protected Void doInBackground(Void...arg0) {


            ArrayList<HashMap<String, String>> filteredToolList = ((MachiningData)getApplicationContext()).getFilteredToolList();
            double [] CriteriaWeightingMatrix = ((MachiningData)getApplicationContext()).getCriteriaWeightingMatrix();;
            int rows_alternatives = filteredToolList.size();
            int columns_criteria = 5;

            ArrayList<HashMap<String, String>> TOPSIS_List = new ArrayList<>();
            double [] [] TOPSISmatrix = new double[rows_alternatives][columns_criteria];

            for (int row = 0; row < rows_alternatives; row++) {
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




            TOPSIS machiningTOPSIS = new TOPSIS();
            machiningTOPSIS.setrows_alternatives(rows_alternatives);
            machiningTOPSIS.setcolumns_criteria(columns_criteria);
            machiningTOPSIS.setTOPSISmatrix(TOPSISmatrix);
            machiningTOPSIS.setCriteriaWeightingMatrix(CriteriaWeightingMatrix);
            machiningTOPSIS.calculate();




            return null;
        }

        //@Override
        //protected void onProgressUpdate() {}

        @Override
        protected void onPostExecute(Void result) {

            double [] [] bigmatrix = ((MachiningData)getApplicationContext()).getTOPSISmatrix();


            ListView testV = (ListView) findViewById(R.id.testList);
            testArrayAdapter adapter = new testArrayAdapter(Optimise_tools.this,bigmatrix);
            testV.setAdapter(adapter);
            adapter.notifyDataSetChanged();



        }
    }*/



}
