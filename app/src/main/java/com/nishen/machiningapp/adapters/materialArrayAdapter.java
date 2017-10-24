package com.nishen.machiningapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nishen.machiningapp.R;

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
    ViewHolder viewHolder;

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
            convertView = inflater.inflate(R.layout.content_material_custom_item, null);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
    // grab temporary material from material arraylist
        HashMap<String, String> map = myMaterialList.get(position);

        viewHolder.SMG.setText(map.get("SMG"));
        viewHolder.Description.setText(map.get("Description"));

        return convertView;
    }

    private class ViewHolder {
        TextView SMG;
        TextView Description;

        ViewHolder(View view) {
            SMG = (TextView) view.findViewById(R.id.SMG);
            Description = (TextView) view.findViewById(R.id.Description);
        }
    }
}
