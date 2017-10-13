package com.nishen.machiningapp;

import android.app.Application;
import android.support.annotation.CallSuper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nishen on 2017/10/11.
 */

public class MachiningData extends Application {
    private String Profile;
    private String selectedMaterial;
    private String CutLength;
    private String CutWidth;
    private String CutDepth;
    private String CornerRadius;

    private ArrayList<HashMap<String, String>> ToolList;





    public String getProfile() {
        return Profile;
    }
    public void setProfile(String profile) {
        Profile = profile;
    }

    public String getSelectedMaterial() {
        return selectedMaterial;
    }
    public void setSelectedMaterial(String selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }


    public String getCutLength() {
        return CutLength;
    }
    public void setCutLength(String cutLength) {
        CutLength = cutLength;
    }

    public String getCutWidth() {
        return CutWidth;
    }
    public void setCutWidth(String cutWidth) {
        CutWidth = cutWidth;
    }

    public String getCutDepth() {
        return CutDepth;
    }
    public void setCutDepth(String cutDepth) {
        CutDepth = cutDepth;
    }

    public String getCornerRadius() {
        return CornerRadius;
    }
    public void setCornerRadius(String cornerRadius) {
        CornerRadius = cornerRadius;
    }

    public ArrayList<HashMap<String, String>> getToolList() {
        return ToolList;
    }
    public void setToolList(ArrayList<HashMap<String, String>> toolList) {
        ToolList = toolList;
    }





    @Override
    public void onCreate(){
        super.onCreate();//reinitialise variables
    }

}
