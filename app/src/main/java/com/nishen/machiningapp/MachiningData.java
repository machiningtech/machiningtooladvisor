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
    private String Coolant;
    private String Clamping;
    private String OperationType;
    private String Machine;

    private ArrayList<HashMap<String, String>> FilteredToolList;
    private ArrayList<HashMap<String, String>> ToolList;

    double [] CriteriaWeightingMatrix;
    double [] [] TOPSISmatrix;
    double [] UnorderedToolScores;





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

    public String getCoolant() {
        return Coolant;
    }
    public void setCoolant(String coolant) {
        Coolant = coolant;
    }

    public ArrayList<HashMap<String, String>> getFilteredToolList() {
        return FilteredToolList;
    }
    public void setFilteredToolList(ArrayList<HashMap<String, String>> filteredToolList) {
        FilteredToolList = filteredToolList;
    }

    public ArrayList<HashMap<String, String>> getToolList() {
        return ToolList;
    }
    public void setToolList(ArrayList<HashMap<String, String>> toolList) {
        ToolList = toolList;
    }

    public double[] getCriteriaWeightingMatrix() {
        return CriteriaWeightingMatrix;
    }
    public void setCriteriaWeightingMatrix(double[] criteriaWeightingMatrix) {
        CriteriaWeightingMatrix = criteriaWeightingMatrix;
    }

    public double[][] getTOPSISmatrix() {
        return TOPSISmatrix;
    }
    public void setTOPSISmatrix(double[][] TOPSISmatrix) {
        this.TOPSISmatrix = TOPSISmatrix;
    }

    public double[] getUnorderedToolScores() {
        return UnorderedToolScores;
    }

    public void setUnorderedToolScores(double[] unorderedToolScores) {
        UnorderedToolScores = unorderedToolScores;
    }

    @Override
    public void onCreate(){
        super.onCreate();//reinitialise variables
    }

}
