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

public class OptimisedToolsAdapter extends BaseAdapter {


    public ArrayList<HashMap<String, String>> BIGmatrix;
    Activity activity;


    HashMap<String, String> element;

    ViewHolder viewHolder;

    public OptimisedToolsAdapter(Activity activity, ArrayList<HashMap<String, String>> BIGmatrix) {
        super();
        this.activity = activity;
        this.BIGmatrix = BIGmatrix;
    }

    @Override
    public int getCount() {
        return BIGmatrix.size();
    }


    @Override
    public Object getItem(int position) {
        return BIGmatrix.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.content_optimised_tool_single_item, null);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
    // grab temporary material from material arraylist
        element = BIGmatrix.get(position);

        DecimalFormat formatter0 = new DecimalFormat("#0");
        DecimalFormat formatter1 = new DecimalFormat("#0.0");
        DecimalFormat formatter2 = new DecimalFormat("#0.00");

        double CuttingSpeed = Double.parseDouble(element.get("CuttingSpeed"));
        double CutWidth = Double.parseDouble(element.get("CutWidth"));
        double CutDepth = Double.parseDouble(element.get("CutDepth"));
        double MMR = Double.parseDouble(element.get("MMR"));
        double CuttingPower = Double.parseDouble(element.get("CuttingPower"));

        viewHolder.position.setText(Integer.toString(position+1));
        viewHolder.name.setText(element.get("Name"));
        viewHolder.diameter.setText(element.get("Diameter"));
        viewHolder.score.setText(element.get("Score"));
        viewHolder.CutDepthPerPass.setText(formatter1.format(CutDepth));
        viewHolder.CutWidthPerPass.setText(formatter1.format(CutWidth));
        viewHolder.CuttingSpeed.setText(formatter0.format(CuttingSpeed));
        viewHolder.power.setText(formatter2.format(CuttingPower));
        viewHolder.mmr.setText(formatter1.format(MMR));


        return convertView;
    }

    private class ViewHolder {
        TextView position;
        TextView name;
        TextView diameter;
        TextView score;
        TextView CutDepthPerPass;
        TextView CutWidthPerPass;
        TextView CuttingSpeed;
        TextView power;
        TextView mmr;


        ViewHolder(View view) {
            position = (TextView)view.findViewById(R.id.Position);
            name = (TextView)view.findViewById(R.id.Name);
            diameter = (TextView)view.findViewById(R.id.Diameter);
            score = (TextView)view.findViewById(R.id.Score);
            CutWidthPerPass = (TextView) view.findViewById(R.id.CutWidthPerPass);
            CutDepthPerPass = (TextView) view.findViewById(R.id.CutDepthPerPass);
            CuttingSpeed = (TextView) view.findViewById(R.id.CuttingSpeed);
            power = (TextView)view.findViewById(R.id.CuttingPower);
            mmr = (TextView)view.findViewById(R.id.MMR);

        }
    }
}
