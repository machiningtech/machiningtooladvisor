package com.nishen.machiningapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Nishen on 2017/09/20.
 */

public class Milling_task extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milling_task);

        // Capture our button from layout
        Button ramp_button = (Button)findViewById(R.id.button_ramp);
        ramp_button.setOnClickListener(rampListener);



    }


    /** Called when the user taps the Slot Milling button */
    public void slotbutton(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Slot_input.class);

        startActivity(intent);
    }



    // Toast for ramp button
    private View.OnClickListener rampListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getBaseContext(), "Feature arriving in the future" , Toast.LENGTH_SHORT ).show();
        }
    };



}
