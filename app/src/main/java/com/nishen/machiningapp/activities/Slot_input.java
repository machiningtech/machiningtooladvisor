package com.nishen.machiningapp.activities;

/**
 * Created by Nishen on 2017/09/19.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nishen.machiningapp.R;
import com.nishen.machiningapp.adapters.materialArrayAdapter;
import com.nishen.machiningapp.helpers.DatabaseAccess;
import com.nishen.machiningapp.models.MachiningData;


public class Slot_input extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    /**
     * Hold a reference to the current animator, so that it can be canceled mid-way.
     */
    private Animator mCurrentAnimator;

    /**
     * The system "short" animation time duration, in milliseconds. This duration is ideal for
     * subtle animations or animations that occur very frequently.
     */
    private int mShortAnimationDuration;


    Spinner material_spinner;
    Spinner cnr_radius_spinner;
    Spinner coolant_spinner;
    Spinner operation_type_spinner;
    Spinner machine_spinner;
    Spinner clamping_spinner;
    Switch user_cutdata_switch;
    TextView user_cutdata_input_edit;
    //String UserCutWidth;
    //String UserCutDepth;
    //String UserCuttingSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cut_profile = ((MachiningData)getApplicationContext()).getProfile();
        setTitle(Html.fromHtml("Milling<big>&#8658</big>" +cut_profile));
        setContentView(R.layout.activity_slot_input);
        //materialList = new ArrayList<>();
        //cnr_radius_list = new ArrayList<>();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

/**  Function to load the materials spinner data from SQLite database
        material_spinner = (Spinner)findViewById(R.id.material_spinner);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Cursor materials =databaseAccess.getMaterialsCursor();
        materials.moveToFirst();
        while (!materials.isAfterLast()) {
            String SMG = materials.getString(0);
            String Description = materials.getString(1);
            HashMap<String, String> material = new HashMap<>();
            //add each value to temporary hashmap
            material.put("SMG", SMG);
            material.put("Description", Description);
            //add material to materialList
            materialList.add(material);
            materials.moveToNext();
        }
        materials.close();

        materialArrayAdapter adapter = new materialArrayAdapter(Slot_input.this, materialList);
        this.material_spinner.setAdapter(adapter);
/**  Function to load the materials spinner data from SQLite database */

    //material_spinner.setOnItemSelectedListener(new materialSpinnerListener());

        new SlotInputAsyncTask().execute();

        //Corner radius dropdown spinner
        cnr_radius_spinner = (Spinner) findViewById(R.id.corner_radius_spinner);
        DatabaseAccess cnr_radius_db = DatabaseAccess.getInstance(getApplicationContext());
        cnr_radius_db.open();
        List<String> cnr_radius_list = cnr_radius_db.unique_corner_radius();
        cnr_radius_list.add(0, "Any");

        cnr_radius_db.close();
        ArrayAdapter<String> cnr_radius_adapter = new ArrayAdapter<String>(Slot_input.this, android.R.layout.simple_spinner_item, cnr_radius_list);
        cnr_radius_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cnr_radius_spinner.setAdapter(cnr_radius_adapter);

        //Corner radius dropdown spinner
        cnr_radius_spinner.setOnItemSelectedListener(new cornerRadiusSpinnerListener());

// Zoomable image buttons
        final View slot_length_view = findViewById(R.id.slot_length_imagebutton);
        slot_length_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(slot_length_view, R.drawable.profile_slot_length);
            }
        });

        final View slot_width_view = findViewById(R.id.slot_width_imagebutton);
        slot_width_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(slot_width_view, R.drawable.profile_slot_width);
            }
        });

        final View slot_depth_view = findViewById(R.id.slot_depth_imagebutton);
        slot_depth_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(slot_depth_view, R.drawable.profile_slot_depth);
            }
        });

        final View slot_cnr_radius_view = findViewById(R.id.slot_corner_radius_imagebutton);
        slot_cnr_radius_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(slot_cnr_radius_view, R.drawable.profile_slot_corner_radius);
            }
        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
// Zoomable image buttons

        coolant_spinner = (Spinner)findViewById(R.id.coolant_spinner);
        String[] coolantArray = getResources().getStringArray(R.array.coolant_list);
        ArrayList<String> coolant_List = new ArrayList<String>(Arrays.asList(coolantArray));
        ArrayAdapter<String> coolant_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coolant_List);
        coolant_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coolant_spinner.setAdapter(coolant_adapter);

        coolant_spinner.setOnItemSelectedListener(new coolantSpinnerListener());

        operation_type_spinner = (Spinner)findViewById(R.id.operation_type_spinner);
        String[] operationTypeArray = getResources().getStringArray(R.array.operation_type_list);
        ArrayList<String> operationType_list = new ArrayList<String>(Arrays.asList(operationTypeArray));
        ArrayAdapter<String> operationType_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operationType_list);
        operationType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operation_type_spinner.setAdapter(operationType_adapter);
        operation_type_spinner.setSelection(1);
        //TODO utilise operation type to filter tools (no explicit argument)

        operation_type_spinner.setOnItemSelectedListener(new operationTypeSpinnerListener());

        machine_spinner = (Spinner) findViewById(R.id.machine_spinner);
        DatabaseAccess machine_db = DatabaseAccess.getInstance(this);
        machine_db.open();
        List<String> machine_list = machine_db.getmachines();
        ArrayAdapter<String> machine_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, machine_list);
        machine_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machine_spinner.setAdapter(machine_adapter);
        machine_db.close();
        //TODO utilise machine power to limit tool selection

        material_spinner.setOnItemSelectedListener(new machineSpinnerListener());

        clamping_spinner = (Spinner)findViewById(R.id.clamping_spinner);
        String[] clampingArray = getResources().getStringArray(R.array.clamping_list);
        ArrayList<String> clamping_list = new ArrayList<String>(Arrays.asList(clampingArray));
        ArrayAdapter<String> clampingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clamping_list);
        clampingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clamping_spinner.setAdapter(clampingAdapter);
        clamping_spinner.setSelection(1);
        //TODO utilise clamping value

        clamping_spinner.setOnItemSelectedListener(new clampingSpinnerListener());

        user_cutdata_input_edit = (TextView) findViewById(R.id.EditUserCutData);
        user_cutdata_input_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserCutdataWindow(true);




            }
        });
        user_cutdata_switch = (Switch) findViewById(R.id.UserCutDataSwitch);
        user_cutdata_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    user_cutdata_input_edit.setTextColor(getResources().getColor(R.color.colorPrimary));
                    UserCutdataWindow(false);
                } else {
                    user_cutdata_input_edit.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
            }
        });






    }   //onCreate






    public void UserCutdataWindow(boolean Edit){
        try {
            // get a reference to the already created main layout
            final ScrollView mainLayout = (ScrollView) findViewById(R.id.container);

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.fragment_user_cutdata_popup, null);

            // create the popup window
            //boolean focusable = false; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, 1, 1, true);
            popupWindow.setWidth(850);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            // show the popup window
            popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 250);

            final EditText CutWidth = (EditText)popupView.findViewById(R.id.userCutWidthPerPass);
            final EditText CutDepth = (EditText)popupView.findViewById(R.id.userCutDepthPerPass);
            final EditText CuttingSpeed = (EditText)popupView.findViewById(R.id.userCuttingSpeed);
            final EditText FeedPerTooth = (EditText)popupView.findViewById(R.id.userFeedPerTooth);

            /**if (Edit && !UserCutWidth.trim().equals("") && !UserCutDepth.trim().equals("") && UserCuttingSpeed.trim().equals("")){
                CutWidth.setText(UserCutWidth);
                CutDepth.setText(UserCutDepth);
                CuttingSpeed.setText(UserCuttingSpeed);
            }*/

            //UserCutWidth = CutWidth.getText().toString();
            //UserCutDepth = CutDepth.getText().toString();
            //UserCuttingSpeed = CuttingSpeed.getText().toString();
            Button Done = (Button)popupView.findViewById(R.id.userCutDataDone);
            Done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CutWidth.getText().toString().trim().equals("") | CutDepth.getText().toString().trim().equals("") | CuttingSpeed.getText().toString().trim().equals("") | FeedPerTooth.getText().toString().trim().equals("") ) {

                        Toast.makeText(getApplicationContext(), "Please fill in the details", Toast.LENGTH_SHORT).show();

                    } else {
                        ((MachiningData) getApplicationContext()).setUserCutWidth(CutWidth.getText().toString());
                        ((MachiningData) getApplicationContext()).setUserCutDepth(CutDepth.getText().toString());
                        ((MachiningData) getApplicationContext()).setUserCuttingSpeed(CuttingSpeed.getText().toString());
                        ((MachiningData) getApplicationContext()).setUserFeedPerTooth(FeedPerTooth.getText().toString());
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(popupView.getWindowToken(), 0);
                        popupWindow.dismiss();
                    }
                }
            });

            Button Back = (Button)popupView.findViewById(R.id.userCutDataBack);
            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(popupView.getWindowToken(), 0);

                    popupWindow.dismiss();
                    user_cutdata_switch.toggle();


                }
            });

            // dismiss the popup window when touched
            //popupView.setOnTouchListener(new View.OnTouchListener() {
             //   @Override
             //   public boolean onTouch(View v, MotionEvent event) {
             //       popupWindow.dismiss();
             //       return true;
              //  }
           // });
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchtools (View view) {
        Intent filter_tools = new Intent(getApplicationContext(), Tool_filter_results.class);
        //Bundle input_data_bundle = new Bundle();
        //insert data into bundle

        //input_data_bundle.putString("coolant", "");
        //input_data_bundle.putString("clamping", "");
        //input_data_bundle.putString("operation_type", "");
        //input_data_bundle.putString("machine", "");

        //filter_tools.putExtras(input_data_bundle);

        //Grab cut dimensions and add to global variables
        EditText CutLength = (EditText)findViewById(R.id.cut_length);
        String cut_length = CutLength.getText().toString();
        ((MachiningData)getApplicationContext()).setCutLength(cut_length);

        EditText CutWidth = (EditText)findViewById(R.id.cut_width);
        String cut_width = CutWidth.getText().toString();
        ((MachiningData)getApplicationContext()).setCutWidth(cut_width);

        //TODO filter tool diameter from sql db when width is less than diameter

        EditText CutDepth = (EditText)findViewById(R.id.cut_depth);
        String cut_depth = CutDepth.getText().toString();
        ((MachiningData)getApplicationContext()).setCutDepth(cut_depth);

        ((MachiningData)getApplicationContext()).setUserCutDataChecked(user_cutdata_switch.isChecked());


        startActivity(filter_tools);
        finish();
    }

    /**
     * "Zooms" in a thumbnail view by assigning the high resolution image to a hidden "zoomed-in"
     * image view and animating its bounds to fit the entire activity content area. More
     * specifically:
     * <p>
     * <ol>
     * <li>Assign the high-res image to the hidden "zoomed-in" (expanded) image view.</li>
     * <li>Calculate the starting and ending bounds for the expanded view.</li>
     * <li>Animate each of four positioning/sizing properties (X, Y, SCALE_X, SCALE_Y)
     * simultaneously, from the starting bounds to the ending bounds.</li>
     * <li>Zoom back out by running the reverse animation on click.</li>
     * </ol>
     *
     * @param thumbView  The thumbnail view to zoom in.
     * @param imageResId The high-resolution version of the image represented by the thumbnail.
     */
    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(R.id.profile_slot_length_big);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class materialSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedMaterial = new String();
            selectedMaterial = String.valueOf(pos + 1);
            ((MachiningData)getApplicationContext()).setSelectedMaterial(selectedMaterial);
  //          selectedMaterial = parent.getItemAtPosition(pos).toString();
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private class cornerRadiusSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedCornerRadius = new String();
            selectedCornerRadius = parent.getItemAtPosition(pos).toString();
            ((MachiningData)getApplicationContext()).setCornerRadius(selectedCornerRadius);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private class coolantSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedCoolant = new String();
            selectedCoolant = parent.getItemAtPosition(pos).toString();
            ((MachiningData)getApplicationContext()).setCoolant(selectedCoolant);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private class clampingSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedClamping = new String();
            selectedClamping = parent.getItemAtPosition(pos).toString();
            ((MachiningData)getApplicationContext()).setClamping(selectedClamping);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private class operationTypeSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedOperationType = new String();
            selectedOperationType = parent.getItemAtPosition(pos).toString();
            ((MachiningData)getApplicationContext()).setOperationType(selectedOperationType);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private class machineSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedMachine = new String();
            selectedMachine = parent.getItemAtPosition(pos).toString();
            ((MachiningData)getApplicationContext()).setMachine(selectedMachine);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private class SlotInputAsyncTask extends AsyncTask<Void, Void, Void> {

        ArrayList<HashMap<String, String>> materialList;
        List<String> cnr_radius_list;

        //@Override
        //protected void onPreExecute() {}

        @Override
        protected Void doInBackground(Void...arg0) {

            materialList = new ArrayList<HashMap<String, String>>();
            //cnr_radius_list= ;

        /**  Function to load the materials spinner data from SQLite database */
            material_spinner = (Spinner)findViewById(R.id.material_spinner);
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            Cursor materials =databaseAccess.getMaterialsCursor();
            materials.moveToFirst();
            while (!materials.isAfterLast()) {
                String SMG = materials.getString(0);
                String Description = materials.getString(1);
                HashMap<String, String> material = new HashMap<>();
                //add each value to temporary hashmap
                material.put("SMG", SMG);
                material.put("Description", Description);
                //add material to materialList
                materialList.add(material);
                materials.moveToNext();
            }
            materials.close();

        /**  Function to load the materials spinner data from SQLite database */




            return null;
        }

        //@Override
        //protected void onProgressUpdate() {}

        @Override
        protected void onPostExecute(Void result) {

    /**  Setting the materials spinner data from SQLite database */
        materialArrayAdapter adapter = new materialArrayAdapter(Slot_input.this, materialList);
        material_spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        material_spinner.setOnItemSelectedListener(new materialSpinnerListener());




        }

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
