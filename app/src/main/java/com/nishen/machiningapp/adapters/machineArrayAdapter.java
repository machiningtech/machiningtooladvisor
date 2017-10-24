package com.nishen.machiningapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nishen.machiningapp.R;
import com.nishen.machiningapp.activities.Machine_management;
import com.nishen.machiningapp.helpers.DatabaseAccess;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nishen on 2017/10/07.
 */

public class machineArrayAdapter extends BaseAdapter {


    public ArrayList<HashMap<String, String>> myMachineList;
    private Context context;
    Activity activity;
    ViewHolder viewHolder;

    public machineArrayAdapter(Context context,Activity activity, ArrayList<HashMap<String, String>> myMachineList) {
        super();
        this.context = context;
        this.activity = activity;
        this.myMachineList = myMachineList;
    }

    @Override
    public int getCount() {
        return myMachineList.size();
    }

    @Override
    public Object getItem(int position) {
        return myMachineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.content_mymachines_list_item, null);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
    // grab temporary material from material arraylist
        HashMap<String, String> map = myMachineList.get(position);

        DecimalFormat formatter2 = new DecimalFormat("#0.0");
        Double PowerShort = Double.parseDouble(map.get("Power"))/1000;

        viewHolder.Number.setText(Integer.toString(position+1));
        viewHolder.Name.setText(map.get("Name"));
        viewHolder.Power.setText(formatter2.format(PowerShort));

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(activity.getApplicationContext());
                databaseAccess.open();
                databaseAccess.deleteMachine(myMachineList.get(position).get("Name"));
                databaseAccess.close();
                Intent intent = new Intent(activity.getApplicationContext(), Machine_management.class);
                context.startActivity(intent);
                activity.finish();
                Toast.makeText(activity.getApplicationContext(), "Deleted machine", Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }

    private class ViewHolder {
        TextView Number;
        TextView Name;
        TextView Power;
        TextView Delete;

        ViewHolder(View view) {
            Number = (TextView) view.findViewById(R.id.Number);
            Name = (TextView) view.findViewById(R.id.Name);
            Power = (TextView) view.findViewById(R.id.Machine_power);
            Delete = (TextView)view.findViewById(R.id.DeleteMachine);

        }
    }
}
