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
    TextView MaterialRemovalRate;
    TextView CuttingSpeed;
    ViewHolder viewHolder;



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

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);


            /* Non-viewholder implementation.
            Name = (TextView) convertView.findViewById(R.id.Name);
            Diameter = (TextView) convertView.findViewById(R.id.Diameter);
            CuttingLength = (TextView) convertView.findViewById(R.id.CuttingLength);
            FluteNumber = (TextView) convertView.findViewById(R.id.FluteNumber);
            CutDepthPerPass = (TextView) convertView.findViewById(R.id.CutDepthPerPass);
            CutWidthPerPass = (TextView) convertView.findViewById(R.id.CutWidthPerPass);
            MaterialRemovalRate = (TextView) convertView.findViewById(R.id.MMR);
            CuttingSpeed = (TextView) convertView.findViewById(R.id.CuttingSpeed);*/
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
    //grab temporary tool item from arraylist of filtered tools
        HashMap<String, String> map = myToolsList.get(position);

        /* Non-viewholder implementation
        Name.setText(map.get("Name"));
        Diameter.setText(map.get("Diameter"));
        CuttingLength.setText(map.get("CuttingLength"));
        FluteNumber.setText(map.get("FluteNumber"));
        CutDepthPerPass.setText(map.get("CutDepth"));
        CutWidthPerPass.setText(map.get("CutWidth"));
        MaterialRemovalRate.setText(map.get("MMR"));
        CuttingSpeed.setText(map.get("CuttingSpeed")); */

        viewHolder.Name.setText(map.get("Name"));
        viewHolder.Diameter.setText(map.get("Diameter"));
        viewHolder.CuttingLength.setText(map.get("CuttingLength"));
        viewHolder.FluteNumber.setText(map.get("FluteNumber"));
        viewHolder.CutDepthPerPass.setText(map.get("CutDepth"));
        viewHolder.CutWidthPerPass.setText(map.get("CutWidth"));
        viewHolder.MaterialRemovalRate.setText(map.get("MMR"));
        viewHolder.CuttingSpeed.setText(map.get("CuttingSpeed"));

        return convertView;
    }

    private class ViewHolder {
        TextView Name;
        TextView Diameter;
        TextView CuttingLength;
        TextView FluteNumber;
        TextView CutDepthPerPass;
        TextView CutWidthPerPass;
        TextView MaterialRemovalRate;
        TextView CuttingSpeed;

        public ViewHolder(View view) {
            Name = (TextView)view.findViewById(R.id.Name);
            Diameter = (TextView) view.findViewById(R.id.Diameter);
            CuttingLength = (TextView) view.findViewById(R.id.CuttingLength);
            FluteNumber = (TextView) view.findViewById(R.id.FluteNumber);
            CutDepthPerPass = (TextView) view.findViewById(R.id.CutDepthPerPass);
            CutWidthPerPass = (TextView) view.findViewById(R.id.CutWidthPerPass);
            MaterialRemovalRate = (TextView) view.findViewById(R.id.MMR);
            CuttingSpeed = (TextView) view.findViewById(R.id.CuttingSpeed);
        }

    }


}
