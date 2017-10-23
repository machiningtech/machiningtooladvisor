package com.nishen.machiningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nishen on 2017/09/20.
 */

public class Milling_task extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Milling");
        setContentView(R.layout.activity_milling_task);

        // Capture our button from layout
        Button ramp_button = (Button)findViewById(R.id.button_ramp);
        ramp_button.setOnClickListener(rampListener);



    }


    /** Called when the user taps the Slot Milling button */
    public void slotbutton(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Slot_input.class);
        ((MachiningData)getApplicationContext()).setProfile("Slot");
        startActivity(intent);

        //finish();
    }

    public void sidebutton(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Side_input.class);
        ((MachiningData)getApplicationContext()).setProfile("Side"); //set global Profile variable
        startActivity(intent);
    }

    public void contourbutton(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Contour_input.class);
        ((MachiningData)getApplicationContext()).setProfile("Contour");
        startActivity(intent);
    }



    // Toast for ramp button
    private View.OnClickListener rampListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            showCustomToast("Feature arriving in the future");
        }
    };



    //show custom Toast in android
    private void showCustomToast(String showToast) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(showToast);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(this, Machine_management.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
