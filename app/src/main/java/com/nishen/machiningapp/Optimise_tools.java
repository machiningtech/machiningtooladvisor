package com.nishen.machiningapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Optimise_tools extends AppCompatActivity {
    ArrayList<HashMap<String, String>> filteredToolList;
    TextView Diameter_header;
    TextView CutDepthPerPass_header;
    TextView CutWidthPerPass_header;
    TextView CuttingSpeed_header;
    TextView CuttingPower_header;
    TextView MMR_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Html.fromHtml("Milling<big>&#8658</big>Slot<big>&#8658</big>Tools<big>&#8658</big>Optimise"));
        setContentView(R.layout.activity_optimise_tools);


        //Format header columns
        Diameter_header = (TextView) findViewById(R.id.Diameter_header);
        Diameter_header.setText(Html.fromHtml("Tool Diameter <small><b><em>mm</em></b></small>"));

        CutDepthPerPass_header = (TextView) findViewById(R.id.CutDepthPerPass_header);
        CutDepthPerPass_header.setText(Html.fromHtml("Cut depth per pass <small><b><em>mm</em></b></small>"));

        CutWidthPerPass_header = (TextView) findViewById(R.id.CutWidthPerPass_header);
        CutWidthPerPass_header.setText(Html.fromHtml("Cut width per pass <small><b><em>mm</em></b></small>"));

        CuttingSpeed_header = (TextView) findViewById(R.id.CuttingSpeed_header);
        CuttingSpeed_header.setText(Html.fromHtml("Cutting Speed <small><b><em>m/min</em></b></small>"));

        CuttingPower_header = (TextView) findViewById(R.id.CuttingPower_header);
        CuttingPower_header.setText(Html.fromHtml("Cutting Power <small><b><em>kW</em></b></small"));

        MMR_header = (TextView) findViewById(R.id.MMR_header);
        MMR_header.setText(Html.fromHtml("Material Removal Rate <small><b><em>mm<sup><small>3</small></sup>/min</em></b></small>"));

//Format header columns

       //new MachiningTOPSIS().execute();

        filteredToolList = ((MachiningData)getApplicationContext()).getFilteredToolList();
        double [] CriteriaWeightingMatrix = ((MachiningData)getApplicationContext()).getCriteriaWeightingMatrix();
        int rows_alternatives = filteredToolList.size();
        int columns_criteria = 5;

        //TOPSIS_List = new ArrayList<>();
        double [] [] TOPSISmatrix = new double[rows_alternatives][columns_criteria];

        for (int row = 0; row < rows_alternatives; row++) {
            HashMap<String, String> ToolData = filteredToolList.get(row);
            HashMap<String, String> TOPSISelement = new HashMap<>();

            DecimalFormat formatter2 = new DecimalFormat("#0.00");

            String Name = ToolData.get("Name");
            String Diameter = ToolData.get("Diameter");
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

        }




        TOPSIS machiningTOPSIS = new TOPSIS();
        machiningTOPSIS.setrows_alternatives(rows_alternatives);
        machiningTOPSIS.setcolumns_criteria(columns_criteria);
        machiningTOPSIS.setTOPSISmatrix(TOPSISmatrix);
        machiningTOPSIS.setCriteriaWeightingMatrix(CriteriaWeightingMatrix);
        double [] UnorderedToolScores = machiningTOPSIS.calculate();


        //add closeness coefficient (score) table into tool data table.
        for (int row = 0; row < rows_alternatives; row++) {
            HashMap<String, String> TOPSISelement = filteredToolList.get(row);
            double toolScore = UnorderedToolScores[row];
            DecimalFormat formatter2 = new DecimalFormat("#0.00");
            String toolScoreShort = formatter2.format(toolScore*100);
            TOPSISelement.put("Score", toolScoreShort);
        }


        Collections.sort(filteredToolList, new ToolScoreComparator());

        ArrayList<HashMap<String, String>> Top5Tools = new ArrayList<>();

        for (int position = 0; position < 5; position++){
            Top5Tools.add(filteredToolList.get(position));
        }



        ListView OptimisedToolList = (ListView) findViewById(R.id.OptimisedToolList);
        OptimisedToolsAdapter adapter = new OptimisedToolsAdapter(this,Top5Tools);
        OptimisedToolList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final ImageView ToolDiagram = (ImageView)findViewById(R.id.OptimiseToolDiagramView);

        OptimisedToolList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String FamilyName = filteredToolList.get(position).get("Name");
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Bitmap ToolDiagramBitmap = databaseAccess.getToolDiagram(FamilyName);
                databaseAccess.close();
                ToolDiagram.setImageBitmap(ToolDiagramBitmap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    } //onCreate


    public class ToolScoreComparator
            implements Comparator<HashMap<String, String>>
    {
        @Override
        public int compare(HashMap<String, String> Tool1,
                           HashMap<String, String> Tool2)
        {
            return Double.compare(Double.parseDouble(Tool2.get("Score")), Double.parseDouble(Tool1.get("Score"))); //Tool2 compared to Tool1 so that sorting is in descending order.
        }
    }   //TODO sort out the comparing. String/double???




}
