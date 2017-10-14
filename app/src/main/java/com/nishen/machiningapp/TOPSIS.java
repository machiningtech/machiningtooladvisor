//TOPSIS Code for machining tool data optimisation

package com.nishen.machiningapp;
import java.lang.Math;

public class TOPSIS
{
    double [] CriteriaWeightingMatrix;
    double [][] TOPSISmatrix;
    int Alternatives_rows, Criteria_columns;

    double [][] normalizedDecisionMatrix;
    double [][] weightedNormalizedDecisionMatrix;
    double [] positiveIdealSolution;
    double [] negativeIdealSolution;
    double []positiveSeparationFromIdeal;
    double []negativeSeparationFromIdeal;
    double [] closenessCoefficient;
    double [] sortclosenessCoefficient;


    public void calculate()
    {
        calculateNormalizedDecisionMatrix();
        calculateWeightedNormalizedDecisionMatrix();
        calculatepositiveIdealSolution();
        calculatenegativeIdealSolution();
        calculatePositiveSeparationFromIdeal();
        calculateNegativeSeparationFromIdeal();
        calculateClosenessCoefficient();

    }

    public double [][] calculateNormalizedDecisionMatrix()
    {
        double [] sumPowSqrt = new double[Criteria_columns];
        normalizedDecisionMatrix = new double[Alternatives_rows][Criteria_columns];

        /*
         * Calculate Normalize Decision Matrix
         */
        for(int col = 0; col<Criteria_columns;col++)
        {

            double sumPow=0.d;
            for(int row = 0;row<Alternatives_rows;row++)
            {
                sumPow = sumPow + Math.pow(TOPSISmatrix[row][col],2);
            }
            sumPowSqrt[col]= Math.sqrt(sumPow);
            for(int row= 0;row<TOPSISmatrix.length;row++)
            {
                normalizedDecisionMatrix[row][col]=TOPSISmatrix[row][col]/sumPowSqrt[col];
            }
        }

        return normalizedDecisionMatrix;
    }

    public double [][] calculateWeightedNormalizedDecisionMatrix()
    {
        weightedNormalizedDecisionMatrix = new double[Alternatives_rows][Criteria_columns];
        for(int col = 0; col<Criteria_columns;col++)
        {
            for(int row = 0;row<Alternatives_rows;row++)
            {
                weightedNormalizedDecisionMatrix[row][col] = normalizedDecisionMatrix[row][col] * CriteriaWeightingMatrix[col];
            }
        }

        return weightedNormalizedDecisionMatrix;

    }

    public double [] calculatepositiveIdealSolution()
    {
        positiveIdealSolution = new double[Criteria_columns];
        double max = 0d;
        for(int col = 0; col<Criteria_columns;col++)
        {
            max = 0d;
            for(int row = 0;row<Alternatives_rows;row++)
            {

                if(weightedNormalizedDecisionMatrix[row][col]>max)
                {
                    max=weightedNormalizedDecisionMatrix[row][col];
                }
                positiveIdealSolution[col]=max;
            }

        }

        return positiveIdealSolution;
    }

    public double [] calculatenegativeIdealSolution()
    {
        negativeIdealSolution = new double[Criteria_columns];
        double min = 0d;
        for(int col = 0; col<Criteria_columns;col++)
        {
            min = 1;
            for(int row = 0;row<Alternatives_rows;row++)
            {

                if(weightedNormalizedDecisionMatrix[row][col]<min)
                {
                    min=weightedNormalizedDecisionMatrix[row][col];
                }
                negativeIdealSolution[col]=min;
            }

        }

        return negativeIdealSolution;
    }

    public double []calculatePositiveSeparationFromIdeal()
    {
        positiveSeparationFromIdeal= new double[Alternatives_rows];
        double [] temp = new double[Alternatives_rows];

        for(int i = 0; i<Alternatives_rows;i++)
        {
            temp[i]=0d;
        }

        for(int row = 0; row<Alternatives_rows;row++)
        {
            for(int col = 0;col<Criteria_columns;col++)
            {
                temp[row] = temp[row] + Math.pow((weightedNormalizedDecisionMatrix[row][col]- positiveIdealSolution[col]), 2);

            }

            positiveSeparationFromIdeal[row]= Math.sqrt(temp[row]);
        }

        return positiveSeparationFromIdeal;
    }

    public double [] calculateNegativeSeparationFromIdeal()
    {
        negativeSeparationFromIdeal= new double[Alternatives_rows];
        double [] temp = new double[Alternatives_rows];

        for(int i = 0; i<Alternatives_rows;i++)
        {
            temp[i]=0d;
        }
        for(int row = 0; row<Alternatives_rows;row++)
        {
            for(int col = 0;col<Criteria_columns;col++)
            {
                temp[row] = temp[row] + Math.pow((weightedNormalizedDecisionMatrix[row][col]- negativeIdealSolution[col]), 2);

            }

            negativeSeparationFromIdeal[row]= Math.sqrt(temp[row]);
        }

        return negativeSeparationFromIdeal;
    }

    public double [] calculateClosenessCoefficient()
    {
        closenessCoefficient = new double[Alternatives_rows];
        for(int i = 0; i<Alternatives_rows;i++)
        {
            closenessCoefficient[i] = negativeSeparationFromIdeal[i]/(negativeSeparationFromIdeal[i] + positiveSeparationFromIdeal[i]);
        }

        return closenessCoefficient;
    }

    public double [] calculateSortClosenessCoefficient()
    {
        sortclosenessCoefficient = new double[Alternatives_rows];

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



    public int getAlternatives_rows() {
        return Alternatives_rows;
    }

    public void setAlternatives_rows(int Alternatives_rows) {
        this.Alternatives_rows = Alternatives_rows;
    }

    public int getCriteria_columns() {
        return Criteria_columns;
    }

    public void setCriteria_columns(int Criteria_columns) {
        this.Criteria_columns = Criteria_columns;
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


}

