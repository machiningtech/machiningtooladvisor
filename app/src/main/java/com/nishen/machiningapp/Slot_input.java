package com.nishen.machiningapp;

/**
 * Created by Nishen on 2017/09/19.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import static com.nishen.machiningapp.R.id.material_spinner;
import static com.nishen.machiningapp.R.id.parent;
import static com.nishen.machiningapp.R.id.textView;


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
    ArrayList<HashMap<String, String>> materialList;
    String selectedMaterial;
    String selectedCornerRadius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Html.fromHtml("Milling<big>&#8658</big>Slot"));
        setContentView(R.layout.activity_slot_input);
        materialList = new ArrayList<>();

/**  Function to load the materials spinner data from SQLite database */
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

    material_spinner.setOnItemSelectedListener(new materialSpinnerListener());

//Corner radius dropdown spinner
        cnr_radius_spinner = (Spinner) findViewById(R.id.corner_radius_spinner);
        DatabaseAccess cnr_radius_db = DatabaseAccess.getInstance(this);
        cnr_radius_db.open();
        List<String> cnr_radius_list = cnr_radius_db.unique_corner_radius();
        cnr_radius_list.add(0, "Any");
        ArrayAdapter<String> cnr_radius_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cnr_radius_list);
        cnr_radius_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cnr_radius_spinner.setAdapter(cnr_radius_adapter);
        cnr_radius_db.close();
//Corner radius dropdown spinner


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

        operation_type_spinner = (Spinner)findViewById(R.id.operation_type_spinner);
        String[] operationTypeArray = getResources().getStringArray(R.array.operation_type_list);
        ArrayList<String> operationType_list = new ArrayList<String>(Arrays.asList(operationTypeArray));
        ArrayAdapter<String> operationType_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operationType_list);
        operationType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operation_type_spinner.setAdapter(operationType_adapter);
        operation_type_spinner.setSelection(1);


        machine_spinner = (Spinner) findViewById(R.id.machine_spinner);
        DatabaseAccess machine_db = DatabaseAccess.getInstance(this);
        machine_db.open();
        List<String> machine_list = machine_db.getmachines();
        ArrayAdapter<String> machine_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, machine_list);
        machine_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machine_spinner.setAdapter(machine_adapter);
        machine_db.close();

        clamping_spinner = (Spinner)findViewById(R.id.clamping_spinner);
        String[] clampingArray = getResources().getStringArray(R.array.clamping_list);
        ArrayList<String> clamping_list = new ArrayList<String>(Arrays.asList(clampingArray));
        ArrayAdapter<String> clampingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clamping_list);
        clampingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clamping_spinner.setAdapter(clampingAdapter);
        clamping_spinner.setSelection(1);

    }



      //  EditText cut_length = (EditText) findViewById(R.id.cut_length);



//create dummy variable to hold "slot"
//perform filtertools. send variable with "slot" to be parsed as parameter.

    public void searchtools (View view) {
        //String mat = material_spinner.getItemAtPosition().toString();
        Intent filter_tools = new Intent(getApplicationContext(), Tool_filter_results.class);
        Bundle input_data_bundle = new Bundle();
    //insert data into bundle
        input_data_bundle.putString("profile", "slot");
        input_data_bundle.putString("material",selectedMaterial);
        //input_data_bundle.putString("cut_length", "");
        //input_data_bundle.putString("cut_width", "");
        //input_data_bundle.putString("cut_depth", "");
        input_data_bundle.putString("max_corner_radius", selectedCornerRadius);
        //input_data_bundle.putString("coolant", "");
        //input_data_bundle.putString("clamping", "");
        //input_data_bundle.putString("operation_type", "");
        //input_data_bundle.putString("machine", "");
        filter_tools.putExtras(input_data_bundle);



        startActivity(filter_tools);

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

    public class materialSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            selectedMaterial = new String();
            selectedMaterial = String.valueOf(pos + 1);
  //          selectedMaterial = parent.getItemAtPosition(pos).toString();
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    public class cornerRadiusSpinnerListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            selectedCornerRadius = new String();
            selectedCornerRadius = parent.getItemAtPosition(pos).toString();
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }


}
