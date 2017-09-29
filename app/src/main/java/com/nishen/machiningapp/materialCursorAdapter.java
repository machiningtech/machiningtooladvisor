package com.nishen.machiningapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Nishen on 2017/09/28.
 */

public class materialCursorAdapter extends CursorAdapter {

    public materialCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.material_custom_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView SMGtext = (TextView) view.findViewById(R.id.SMG);
        TextView DescriptionText = (TextView) view.findViewById(R.id.Description);
        // Extract properties from cursor
        String SMG = cursor.getString(1);
        String Description = cursor.getString(2);
        // Populate fields with extracted properties
        SMGtext.setText(SMG);
        DescriptionText.setText(String.valueOf(Description));
    }
}
