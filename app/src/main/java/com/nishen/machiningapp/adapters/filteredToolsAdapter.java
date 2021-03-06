package com.nishen.machiningapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nishen.machiningapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nishen on 2017/10/07.
 */

public class filteredToolsAdapter extends BaseAdapter {


    private ArrayList<HashMap<String, String>> myToolsList;
    private Activity activity;
    private ViewHolder viewHolder;



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
            convertView = inflater.inflate(R.layout.content_filtered_tool_single_item, null);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
    //grab temporary tool item from arraylist of filtered tools
        HashMap<String, String> map = myToolsList.get(position);


        DecimalFormat formatter0 = new DecimalFormat("#0");
        DecimalFormat formatter1 = new DecimalFormat("#0.0");
        DecimalFormat formatter2 = new DecimalFormat("#0.00");

        double CuttingSpeed = Double.parseDouble(map.get("CuttingSpeed"));
        double CutWidth = Double.parseDouble(map.get("CutWidth"));
        double CutDepth = Double.parseDouble(map.get("CutDepth"));
        double MMR = Double.parseDouble(map.get("MMR"));
        double CuttingPower = Double.parseDouble(map.get("CuttingPower"));



        viewHolder.Name.setText(map.get("Name"));
        viewHolder.Diameter.setText(map.get("Diameter"));
        viewHolder.CuttingLength.setText(map.get("CuttingLength"));
        viewHolder.FluteNumber.setText(map.get("FluteNumber"));
        viewHolder.CutDepthPerPass.setText(formatter1.format(CutDepth));
        viewHolder.CutWidthPerPass.setText(formatter1.format(CutWidth));
        viewHolder.MaterialRemovalRate.setText(formatter1.format(MMR));
        viewHolder.CuttingSpeed.setText(formatter0.format(CuttingSpeed));
        viewHolder.CuttingPower.setText(formatter2.format(CuttingPower));

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
        TextView CuttingPower;

        public ViewHolder(View view) {
            Name = (TextView)view.findViewById(R.id.Name);
            Diameter = (TextView) view.findViewById(R.id.Diameter);
            CuttingLength = (TextView) view.findViewById(R.id.CuttingLength);
            FluteNumber = (TextView) view.findViewById(R.id.FluteNumber);
            CutDepthPerPass = (TextView) view.findViewById(R.id.CutDepthPerPass);
            CutWidthPerPass = (TextView) view.findViewById(R.id.CutWidthPerPass);
            MaterialRemovalRate = (TextView) view.findViewById(R.id.MMR);
            CuttingSpeed = (TextView) view.findViewById(R.id.CuttingSpeed);
            CuttingPower = (TextView) view.findViewById(R.id.CuttingPower);
        }

    }


}
