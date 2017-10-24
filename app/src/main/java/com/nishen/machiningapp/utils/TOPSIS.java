//TOPSIS Code for machining tool data optimisation

package com.nishen.machiningapp.utils;
import java.lang.Math;

public class TOPSIS {
    private double[] CriteriaWeightingMatrix;
    private double[][] TOPSISmatrix;
    private int rows_alternatives, columns_criteria;

    private double[][] normalizedDecisionMatrix;
    private double[] normalizedWeightingMatrix;
    private double[][] weightedNormalizedDecisionMatrix;
    private double[] positiveIdealSolution;
    private double[] negativeIdealSolution;
    private double[] positiveSeparationFromIdeal;
    private double[] negativeSeparationFromIdeal;
    private double[] closenessCoefficient;
    private double[] sortclosenessCoefficient;


    public double [] calculate() {
        calculateNormalizedDecisionMatrix();
        calculateNormalizedWeightingMatrix();
        calculateWeightedNormalizedDecisionMatrix();
        calculatepositiveIdealSolution();
        calculatenegativeIdealSolution();
        calculatePositiveSeparationFromIdeal();
        calculateNegativeSeparationFromIdeal();
        calculateClosenessCoefficient();
        return closenessCoefficient;
    }

    public double[][] calculateNormalizedDecisionMatrix() {
        double[] sumPowSqrt = new double[columns_criteria];
        normalizedDecisionMatrix = new double[rows_alternatives][columns_criteria];

        /*
         * Calculate Normalize Decision Matrix
         */
        for (int col = 0; col < columns_criteria; col++) {

            double sumPow = 0.d;
            for (int row = 0; row < rows_alternatives; row++) {
                sumPow = sumPow + Math.pow(TOPSISmatrix[row][col], 2);
            }
            sumPowSqrt[col] = Math.sqrt(sumPow);
            for (int row = 0; row < TOPSISmatrix.length; row++) {
                normalizedDecisionMatrix[row][col] = TOPSISmatrix[row][col] / sumPowSqrt[col];
            }
        }

        return normalizedDecisionMatrix;
    }

    public double[] calculateNormalizedWeightingMatrix() {
        normalizedWeightingMatrix = new double[columns_criteria];

        double sumPow = 0.d;
        double sumPowSqrt = 0.d;
        for (int row = 0; row < columns_criteria; row++) {
            sumPow = sumPow + Math.pow(CriteriaWeightingMatrix[row], 2);
        }
        sumPowSqrt = Math.sqrt(sumPow);
        for (int row = 0; row < columns_criteria; row++) {
            normalizedWeightingMatrix[row] = (CriteriaWeightingMatrix[row]) / sumPowSqrt;
        }
        return normalizedWeightingMatrix;
    }

    public double [][] calculateWeightedNormalizedDecisionMatrix()
    {
        weightedNormalizedDecisionMatrix = new double[rows_alternatives][columns_criteria];
        for(int col = 0; col<columns_criteria;col++)
        {
            for(int row = 0;row<rows_alternatives;row++)
            {
                weightedNormalizedDecisionMatrix[row][col] = normalizedDecisionMatrix[row][col] * normalizedWeightingMatrix[col];
            }
        }

        return weightedNormalizedDecisionMatrix;

    }

    public double [] calculatepositiveIdealSolution()
    {
        positiveIdealSolution = new double[columns_criteria];
        double max = 0.d;
        for(int col = 0; col<columns_criteria;col++)
        {
            max = 0d;

            if (col == 4) {                                             //maximising the MMR
                for(int row = 0;row<rows_alternatives;row++) {
                    if (weightedNormalizedDecisionMatrix[row][col] > max) {
                        max = weightedNormalizedDecisionMatrix[row][col];
                    }
                    positiveIdealSolution[col] = max;
                }
            } else {

                for(int row = 0;row<rows_alternatives;row++)
                {
                    if(weightedNormalizedDecisionMatrix[row][col]<max)      //Minimising first 4 parameters
                    {
                        max=weightedNormalizedDecisionMatrix[row][col];
                    }
                    positiveIdealSolution[col]=max;
                }
            }

        }

        return positiveIdealSolution;
    }

    public double [] calculatenegativeIdealSolution()
    {
        negativeIdealSolution = new double[columns_criteria];
        double min = 0d;

        for(int col = 0; col<columns_criteria;col++)
        {
            min = 1;
            if (col == 4){                                      //minimise MMR
                for(int row = 0;row<rows_alternatives;row++)
                {
                    min = 500;

                    if(weightedNormalizedDecisionMatrix[row][col]<min)
                    {
                        min=weightedNormalizedDecisionMatrix[row][col];
                    }
                    negativeIdealSolution[col]=min;
                }
            } else {

                for (int row = 0; row < rows_alternatives; row++) {     //maximise eg. power consumption

                    if (weightedNormalizedDecisionMatrix[row][col] > min) {
                        min = weightedNormalizedDecisionMatrix[row][col];
                    }
                    negativeIdealSolution[col] = min;
                }
            }
        }

        return negativeIdealSolution;
    }

    public double []calculatePositiveSeparationFromIdeal()
    {
        positiveSeparationFromIdeal= new double[rows_alternatives];
        double [] temp = new double[rows_alternatives];

        for(int i = 0; i<rows_alternatives;i++)
        {
            temp[i]=0d;
        }

        for(int row = 0; row<rows_alternatives;row++)
        {
            for(int col = 0;col<columns_criteria;col++)
            {
                temp[row] = temp[row] + Math.pow((weightedNormalizedDecisionMatrix[row][col]- positiveIdealSolution[col]), 2);

            }

            positiveSeparationFromIdeal[row]= Math.sqrt(temp[row]);
        }

        return positiveSeparationFromIdeal;
    }

    public double [] calculateNegativeSeparationFromIdeal()
    {
        negativeSeparationFromIdeal= new double[rows_alternatives];
        double [] temp = new double[rows_alternatives];

        for(int i = 0; i<rows_alternatives;i++)
        {
            temp[i]=0d;
        }
        for(int row = 0; row<rows_alternatives;row++)
        {
            for(int col = 0;col<columns_criteria;col++)
            {
                temp[row] = temp[row] + Math.pow((weightedNormalizedDecisionMatrix[row][col]- negativeIdealSolution[col]), 2);

            }

            negativeSeparationFromIdeal[row]= Math.sqrt(temp[row]);
        }

        return negativeSeparationFromIdeal;
    }

    public double [] calculateClosenessCoefficient() //similarity to the greatest distance from worst solution
    {
        closenessCoefficient = new double[rows_alternatives];
        for(int i = 0; i<rows_alternatives;i++)
        {
            closenessCoefficient[i] = negativeSeparationFromIdeal[i]/(negativeSeparationFromIdeal[i] + positiveSeparationFromIdeal[i]);
        }


        return closenessCoefficient;
    }

    public double [] calculateSortClosenessCoefficient()        //Will sort after joining table data
    {
        sortclosenessCoefficient = new double[rows_alternatives];

        return sortclosenessCoefficient;
    }

    public double[] getnegativeIdealSolution() {
        return negativeIdealSolution;
    }

    public double[][] getNormalizedDecisionMatrix() {
        return normalizedDecisionMatrix;
    }

    public double[] getpositiveIdealSolution() {
        return positiveIdealSolution;
    }

    public double[] getclosenessCoefficient() {
        return closenessCoefficient;
    }

    public double[] getSortclosenessCoefficient() {
        return sortclosenessCoefficient;
    }

    public double[][] getWeightedNormalizedDecisionMatrix() {
        return weightedNormalizedDecisionMatrix;
    }



    public int getrows_alternatives() {
        return rows_alternatives;
    }

    public void setrows_alternatives(int rows_alternatives) {
        this.rows_alternatives = rows_alternatives;
    }

    public int getcolumns_criteria() {
        return columns_criteria;
    }

    public void setcolumns_criteria(int columns_criteria) {
        this.columns_criteria = columns_criteria;
    }

    public double[][] getTOPSISmatrix() {
        return TOPSISmatrix;
    }

    public void setTOPSISmatrix(double[][] TOPSISmatrix) {
        this.TOPSISmatrix = TOPSISmatrix;
    }

    public double[] getCriteriaWeightingMatrix() {
        return CriteriaWeightingMatrix;
    }

    public void setCriteriaWeightingMatrix(double[] CriteriaWeightingMatrix) {
        this.CriteriaWeightingMatrix = CriteriaWeightingMatrix;
    }

    public double[] getnegativeSeparationFromIdeal() {
        return negativeSeparationFromIdeal;
    }

    public void setnegativeSeparationFromIdeal(double[] negativeSeparationFromIdeal) {
        this.negativeSeparationFromIdeal = negativeSeparationFromIdeal;
    }

    public double[] getpositiveSeparationFromIdeal() {
        return positiveSeparationFromIdeal;
    }

    public void setpositiveSeparationFromIdeal(double[] positiveSeparationFromIdeal) {
        this.positiveSeparationFromIdeal = positiveSeparationFromIdeal;
    }

    public void setclosenessCoefficient(double[] closenessCoefficient) {
        this.closenessCoefficient = closenessCoefficient;
    }

    public void setnegativeIdealSolution(double[] negativeIdealSolution) {
        this.negativeIdealSolution = negativeIdealSolution;
    }

    public void setNormalizedDecisionMatrix(double[][] normalizedDecisionMatrix) {
        this.normalizedDecisionMatrix = normalizedDecisionMatrix;
    }

    public void setpositiveIdealSolution(double[] positiveIdealSolution) {
        this.positiveIdealSolution = positiveIdealSolution;
    }

    public void setSortclosenessCoefficient(double[] sortclosenessCoefficient) {
        this.sortclosenessCoefficient = sortclosenessCoefficient;
    }

    public void setWeightedNormalizedDecisionMatrix(double[][] weightedNormalizedDecisionMatrix) {
        this.weightedNormalizedDecisionMatrix = weightedNormalizedDecisionMatrix;
    }

    public double[] getNormalizedWeightingMatrix() {
        return normalizedWeightingMatrix;
    }

}

