package com.nishen.machiningapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nishen on 2017/10/07.
 */

public class materialArrayAdapter extends BaseAdapter {


    public ArrayList<HashMap<String, String>> myMaterialList;
    Activity activity;
    TextView SMG;
    TextView Description;
    Map<Integer, Integer> myMap;
    Spinner materialSpinner;

    public materialArrayAdapter(Activity activity, ArrayList<HashMap<String, String>> myMaterialList) {
        super();
        this.activity = activity;
        this.myMaterialList = myMaterialList;
    }

    @Override
    public int getCount() {
        return myMaterialList.size();
    }

    @Override
    public Object getItem(int position) {
        return myMaterialList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.material_custom_item, null);

            SMG = (TextView) convertView.findViewById(R.id.SMG);
            Description = (TextView) convertView.findViewById(R.id.Description);
            materialSpinner = (Spinner) convertView.findViewById(R.id.material_spinner);
        }

        HashMap<String, String> map = myMaterialList.get(position);
        SMG.setText(map.get("SMG"));
        Description.setText(map.get("Description"));


        return convertView;

    }




}
