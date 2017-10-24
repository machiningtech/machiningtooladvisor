package com.nishen.machiningapp.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nishen.machiningapp.adapters.OptimisedToolsAdapter;
import com.nishen.machiningapp.R;
import com.nishen.machiningapp.utils.TOPSIS;
import com.nishen.machiningapp.helpers.DatabaseAccess;
import com.nishen.machiningapp.models.MachiningData;

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
    TextView testV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cut_profile = ((MachiningData)getApplicationContext()).getProfile();
        setTitle(Html.fromHtml("Milling<big>&#8658</big>" + cut_profile + "<big>&#8658</big>Tools<big>&#8658</big>Optimise"));
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

        for (int position = 0; position < 4; position++){
            Top5Tools.add(filteredToolList.get(position));
        }



        ListView OptimisedToolList = (ListView) findViewById(R.id.OptimisedToolList);
        OptimisedToolsAdapter adapter = new OptimisedToolsAdapter(this,Top5Tools);
        OptimisedToolList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        testV = (TextView)findViewById(R.id.ToolName);


        /**OptimisedToolList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String FamilyName = filteredToolList.get(position).get("Name").toLowerCase();
                final int imgId = getResources().getIdentifier(FamilyName , "drawable", getPackageName());
                //ToolDetailsWindow(FamilyName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        OptimisedToolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToolDetailsWindow(position);
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
    }





    public void ToolDetailsWindow(int position){
        try {
            // get a reference to the already created main layout
            final ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.container);

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.content_optimised_tool_details, null);

            // create the popup window
            //boolean focusable = false; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, 1, 1, false);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            // show the popup window
            popupWindow.showAtLocation(mainLayout, Gravity.BOTTOM, 0, 0);

            String FamilyName = filteredToolList.get(position).get("Name");
            //String FamilyName = filteredToolList.get(position).get("Name").toLowerCase();
            String ToolName = filteredToolList.get(position).get("PartNumber");
            String ToolShape = filteredToolList.get(position).get("ToolShape");
            String Dc = filteredToolList.get(position).get("Diameter");
            String dmm = filteredToolList.get(position).get("dmm");
            String ap = filteredToolList.get(position).get("CuttingLength");
            String l2 = filteredToolList.get(position).get("l2");
            String re1 = filteredToolList.get(position).get("re1");
            String flutes = filteredToolList.get(position).get("FluteNumber");
            String rake = filteredToolList.get(position).get("rakeAngle");

            TextView Tool_name = (TextView) popupView.findViewById(R.id.ToolName);
            TextView Tool_shape = (TextView) popupView.findViewById(R.id.ToolShape);
            TextView dc = (TextView) popupView.findViewById(R.id.Dc);
            TextView Dmm = (TextView) popupView.findViewById(R.id.dmm);
            TextView Ap = (TextView) popupView.findViewById(R.id.ap);
            TextView L2 = (TextView) popupView.findViewById(R.id.l2);
            TextView Re1 = (TextView) popupView.findViewById(R.id.re1);
            TextView Flutes = (TextView) popupView.findViewById(R.id.Flutes);
            TextView Rake = (TextView) popupView.findViewById(R.id.Rake);

            Tool_name.setText("Tool name: " + ToolName);
            Tool_shape.setText("Tool shape: " + ToolShape);
            dc.setText(Html.fromHtml("D<sub><small>c</small></sub>: " + Dc + "mm"));
            Dmm.setText(Html.fromHtml("Dm<sub><small>m</small></sub>: " + dmm + "mm"));
            Ap.setText(Html.fromHtml("A<sub><small>p</small></sub>: " + ap + "mm"));
            L2.setText(Html.fromHtml("l<sub><small>2</small></sub>: " + l2 + "mm"));
            Re1.setText(Html.fromHtml("r<sub><small>e1</small></sub>: " + re1 + "mm"));
            Flutes.setText("Flutes: " + flutes);
            Rake.setText(Html.fromHtml("Rake: " + rake + "<sup><small>o</small></sup>"));

            ImageView ToolDiagram = (ImageView)popupView.findViewById(R.id.OptimisedToolDiagram);
            //int imageID = getResources().getIdentifier(FamilyName, "drawable", getPackageName());
            //ToolDiagram.setBackground(getDrawable(imageID));
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            Bitmap toolDiagram = databaseAccess.getToolDiagram(FamilyName);
            ToolDiagram.setImageBitmap(toolDiagram);
            //TODO check for zoomable and pannable with a click. Flushing pictures from ram.
        }


        catch (Exception e) {
            e.printStackTrace();
        }
    };


}
