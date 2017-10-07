package com.nishen.machiningapp;

import android.app.Activity;
import android.content.Context;
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

public class filteredToolsAdapter extends BaseAdapter {


    public ArrayList<HashMap<String, String>> myToolsList;
    Activity activity;
    TextView Name;
    TextView Diameter;
    TextView CuttingLength;
    TextView FluteNumber;
    TextView CutDepthPerPass;
    TextView CutWidthPerPass;



    public filteredToolsAdapter(Activity activity, ArrayList<HashMap<String, String>> myToolsList) {
        super();
        this.activity = activity;
        this.myToolsList = myToolsList;
    }

    @Override
    public int getCount() {
        return myToolsList.size();
    }

    @Override
    public Object getItem(int position) {
        return myToolsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.filtered_tool_single_item, null);
            //convertView = LayoutInflater.from(context).inflate(R.layout.filtered_tool_single_item, parent, false);
            //viewHolder = new ViewHolder(convertView);
            //convertView.setTag(viewHolder);
            Name = (TextView) convertView.findViewById(R.id.Name);
            Diameter = (TextView) convertView.findViewById(R.id.Diameter);
            CuttingLength = (TextView) convertView.findViewById(R.id.CuttingLength);
            FluteNumber = (TextView) convertView.findViewById(R.id.FluteNumber);
        }

        HashMap<String, String> map = myToolsList.get(position);
        Name.setText(map.get("Name"));
        Diameter.setText(map.get("Diameter") + " mm");
        CuttingLength.setText(map.get("CuttingLength") + " mm");
        FluteNumber.setText(map.get("FluteNumber"));
        //viewHolder.toolName.setText(map.get("Name"));
        //viewHolder.toolDiameter.setText(map.get("Diamter"));

        return convertView;

    }

    private class ViewHolder {
        TextView toolName;
        TextView toolDiameter;

        public ViewHolder(View view) {
            toolName = (TextView)view.findViewById(R.id.Name);
            toolDiameter = (TextView) view.findViewById(R.id.Diameter);
        }
    }


}
