package com.nishen.machiningapp;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import  java.lang.Math;

import static android.content.Intent.getIntent;

/**
 * Created by Nishen on 2017/09/19.
 */

public class Tool_filter_results extends AppCompatActivity {

    ArrayList<HashMap<String, String>> filteredTools;
    ListView tool_results_list;
    TextView Diameter_header;
    TextView CuttingLength_header;
    TextView CutDepthPerPass_header;
    TextView CutWidthPerPass_header;
    TextView CuttingSpeed_header;
    TextView CuttingPower_header;
    TextView MMR_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Html.fromHtml("Milling<big>&#8658</big>Slot<big>&#8658</big>Tools"));
        setContentView(R.layout.activity_tool_filter_results);
        String materialID = new String();
        filteredTools = new ArrayList<>();

//Format header columns
        Diameter_header = (TextView) findViewById(R.id.Diameter_header);
        Diameter_header.setText(Html.fromHtml("Tool Diameter <small><b><em>mm</em></b></small>"));

        CuttingLength_header = (TextView) findViewById(R.id.CuttingLength_header);
        CuttingLength_header.setText(Html.fromHtml("Cutting Length <small><b><em>mm</em></b></small>"));

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

// Grab input data from MachiningData global class
        //Bundle tool_search_bundle = getIntent().getExtras();
        materialID = ((MachiningData)getApplicationContext()).getSelectedMaterial();
        String cut_profile = ((MachiningData)getApplicationContext()).getProfile();
        //String cut_length = tool_search_bundle.getString("cut_length");
        //String cut_width = tool_search_bundle.getString("cut_width");
        //String cut_depth = tool_search_bundle.getString("cut_depth");
        String max_corner_radius = ((MachiningData)getApplicationContext()).getCornerRadius();
        //String coolant = tool_search_bundle.getString("coolant");
        //String clamping = tool_search_bundle.getString("clamping");
        //String operation_type = tool_search_bundle.getString("operation_type");
        //String machine = tool_search_bundle.getString("machine");


 /** Populating Tool results listview **/
        tool_results_list= (ListView) findViewById(R.id.toolList);
        //View tool_header = getLayoutInflater().inflate(R.layout.tool_header, null);
        //tool_results_list.addHeaderView(tool_header);
        DatabaseAccess tool_search_db = DatabaseAccess.getInstance(this);
        tool_search_db.open();
        //Parse input data for database query
        Cursor tool_search_list = tool_search_db.FilterToolsCursor(cut_profile, materialID);
        //Parse input data for database query
        tool_search_list.moveToFirst();
        while (!tool_search_list.isAfterLast()) {
            String Name = tool_search_list.getString(0);
            String Diameter = tool_search_list.getString(1);
            String CuttingLength = tool_search_list.getString(2);
            String FluteNumber = tool_search_list.getString(3);
            String PartNumber = tool_search_list.getString(4);
            String ToolShape = tool_search_list.getString(5);
            String dmm = tool_search_list.getString(6);
            String l2 = tool_search_list.getString(7);
            String re1 = tool_search_list.getString(8);
            String rakeAngle = tool_search_list.getString(9);
            String Coolant = tool_search_list.getString(10);
            String Ap_Dc = tool_search_list.getString(11);
            String Ae_Dc = tool_search_list.getString(12);
            String Fz6 = tool_search_list.getString(13);
            String Fz8 = tool_search_list.getString(14);
            String Fz10 = tool_search_list.getString(15);
            String Fz12 = tool_search_list.getString(16);
            String Cutting_speed = tool_search_list.getString(17);


            Cursor MaterialData = tool_search_db.getMaterialData(materialID);
            MaterialData.moveToFirst();
            String HB = MaterialData.getString(1);
            String UTS = MaterialData.getString(2);
            String kc = MaterialData.getString(3);

/**     Calculate power, etc.. and filter hashmap for individual tool **/
    //Assign feed/tooth (mm) based on Diameter
            String Feed_per_tooth = "0.1";
            if (Diameter == "6"){
                Feed_per_tooth = Fz6;
            } else if (Diameter == "8"){
                Feed_per_tooth = Fz8;
            }else if (Diameter == "10"){
                Feed_per_tooth = Fz10;
            }else if (Diameter == "12"){
                Feed_per_tooth = Fz12;
            }
    //Assign feed/tooth (mm) based on Diameter
            DecimalFormat formatter1 = new DecimalFormat("#0.0");
            DecimalFormat formatter2 = new DecimalFormat("#0.00");
            double pi = Math.PI;

    //Material removal rate calculations
            double diameter = Double.parseDouble(Diameter);
            double ApDc = Double.parseDouble(Ap_Dc);
            double CutDepth = ApDc * diameter ;
            double AeDc = Double.parseDouble(Ae_Dc);
            double CutWidth = AeDc * diameter ;

            double Vc = Double.parseDouble(Cutting_speed);
            double SpindleSpeed = Vc / (pi * diameter);
            double Fz = Double.parseDouble(Feed_per_tooth);
            double zn = Double.parseDouble(FluteNumber);
            double FeedVelocity = SpindleSpeed * Fz * zn;

            double MMR = CutDepth * CutWidth * FeedVelocity; // Q
    //Material removal rate calculations

            String Cut_depth = formatter1.format(CutDepth);
            String Cut_width = Double.toString(CutWidth);
            //String Material_removal_rate = Double.toString(MMR);


            String Material_removal_rate = formatter1.format(MMR);

    //Cutting power calculations
            double SpecificCuttingEnergy = Double.parseDouble(kc); // Ks
            double CuttingPower = MMR * SpecificCuttingEnergy;
    //Cutting power calculations

            String Cutting_power = formatter2.format(CuttingPower / (60 * 1000)); //kW formatting

    //Cutting force calculations

            //Float Kc = SpecificCuttingEnergy;
            //Float CuttingForce = Ks * CutDepth * Fz;
            //Cutting force calculations


    //Shear plane deformation calculations

            //double Yield = Double.parseDouble(Yield);
            //double ChipCompressionRatio = UTS/Yield;  //Very similar to cutting ratio

            double Gamma0 = Double.parseDouble(rakeAngle); //tool rake angle

            //ChipCompressionRatio = cos(phi - Gamma0) / sin (phi)
            //Solving for phi (shear angle)
            //double phi = Math.atan(Math.cos(Gamma0) / (ChipCompressionRatio - Math.sin(Gamma0)));


            //double phi = (pi / 4)-(Beta - Gamma0) ;
            //Solving for beta (tool-interface friction)
            //double Beta = (pi / 4) + Gamma0 + phi;

            //double ShearStrain = Math.cos(Gamma0) / (Math.sin(phi) - Math.cos(phi - Gamma0));




    //Shear plane deformation calculations



    //Tool wear calculations
            double n = 1; // work hardening factor of material. May be unnecessary in comparison
            //double Py = Yield; // yield strength of material.
            double Em = 1; // Elastic modulus of material.
            double Wn = 1; // Normal load.
            double Kc = 1; //Fracture toughness of material.
            double H = 1; // hardness of material.


//cutting forces...foraxial load



            double Length_of_cut = 10; // from input page
            double ChipNumber = Length_of_cut / Fz;

            double ContactLength = Math.sqrt(CutDepth * diameter);

            double L = ChipNumber * ContactLength / zn ; // sliding distance per tooth

            //double ToolWear = (n * n) * ((Py * Em *(Math.pow(Wn, 3 / 2)))/(Kc * Kc * (Math.pow(H, 3 / 2)))) * L ;

    //Tool wear calculations

    //Surface roughness calculations
            //tool tip radius
            double rn = Double.parseDouble(re1);
            double Roughness = (Fz * Fz) / (31.2 * rn) ; //Ra (mm)
    //Surface roughness calculations




 /**     Calculate power, etc.. and filter hashmap for individual tool  **/

            HashMap<String, String> tool = new HashMap<>();
            //add each value to temporary hashmap
            tool.put("Name", Name);
            tool.put("Diameter", Diameter);
            tool.put("CuttingLength", CuttingLength);
            tool.put("FluteNumber", FluteNumber);
            tool.put("PartNumber", PartNumber);
            tool.put("ToolShape", ToolShape);
            tool.put("dmm", dmm);
            tool.put("l2", l2);
            tool.put("re1", re1);
            tool.put("rakeAngle", rakeAngle);
            tool.put("Coolant", Coolant);
            tool.put("CutDepth", Cut_depth);
            tool.put("CutWidth", Cut_width);
            tool.put("Fz", Feed_per_tooth);
            tool.put("CuttingSpeed", Cutting_speed);
            tool.put("MMR", Material_removal_rate);
            tool.put("CuttingPower", Cutting_power);
            filteredTools.add(tool);




            tool_search_list.moveToNext();
        }



            tool_search_list.close();

            filteredToolsAdapter tool_filter_adapter = new filteredToolsAdapter(this, filteredTools);
            tool_results_list.setAdapter(tool_filter_adapter);
            tool_search_db.close();

/** Populating Tool results listview **/




        TextView testView = (TextView) findViewById(R.id.testview);
        testView.setText(((MachiningData)getApplicationContext()).getCutLength());


    } //onCreate


    public void OptimiseTools(View view) {
        Intent optimise_tools_intent = new Intent(getApplicationContext(), Optimise_tools.class);
        startActivity(optimise_tools_intent);
    }




}