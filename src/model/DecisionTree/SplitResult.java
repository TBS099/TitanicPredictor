package model.DecisionTree;

import model.*;

//Initialise SplitResult Class
public class SplitResult {

    //Initialise variables
    public int featureIndex;
    public double threshold;
    public double gini;
    public Passenger[] left;
    public Passenger[] right;

    //Initialise constructor
    public SplitResult (int featureIndex, double threshold, double gini, Passenger[] left, Passenger[] right) {

        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.gini = gini;
        this.left = left;
        this.right = right;

    }

    //Function to find the best split
    public static SplitResult findBestSplit(Passenger[] data) {

        if(data.length == 0) return null;

        //Initialise needed variables
        int featureLength = data[0].getFeatures().length;
        SplitResult bestSplit = null;
        double bestGini = Double.MAX_VALUE;

        //Loop through the features
        for(int featureIndex = 0; featureIndex < featureLength; featureIndex++){

            //Extract feature values
            double[] featureValues = new double[data.length];

            for(int i = 0; i < data.length; i++){

                featureValues[i] = data[i].getFeatures()[featureIndex];

            }

            //Sort values
            double[] sorted = sortUnique(featureValues);

            //Generate Thresholds
            for(int i = 0; i < (sorted.length - 1); i++){

                //Calculate threshold and initialise variables
                double threshold = (sorted[i] + sorted[i + 1]) / 2.0;
                Passenger[] left = new Passenger[data.length];
                Passenger[] right = new Passenger[data.length];
                int leftCount = 0;
                int rightCount = 0;

                //Extract specific feature from each passenger data and compare to threshold
                for(int j = 0; j < data.length; j++){

                    double value = data[j].getFeatures()[featureIndex];

                    if(value <= threshold){

                        left[leftCount++] = data[j];

                    }
                    else {

                        right[rightCount++] = data[j];

                    }

                    //Copy array to properly formed one and calculate gini
                    Passenger[] leftFinal = new Passenger[leftCount];
                    Passenger[] rightFinal = new Passenger[rightCount];
                    System.arraycopy(left, 0, leftFinal, 0, leftCount);
                    System.arraycopy(right, 0, rightFinal, 0, rightCount);

                    double gini = calculateGini(leftFinal, rightFinal);

                    if (gini < bestGini) {

                        bestGini = gini;
                        bestSplit = new SplitResult(featureIndex, threshold, gini, leftFinal, rightFinal);

                    }

                }

            }

        }

        return bestSplit;

    }

    //Helper function to sort values
    private static double[] sortUnique(double[] values){

        int n = values.length;
        for(int i = 0; i < n - 1; i++){

            for(int j = i + 1; j < n - 1; j++){

                if(values[j] > values[j + 1]) {
                    double temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }

            }

        }

        //Remove duplicates
        double[] temp = new double[n];
        int count = 0;

        for(int i = 0; i < n; i++){

            if (i == 0 || values[i] != values[i - 1]) {
                temp[count++] = values[i];
            }

        }

        double[] unique = new double[count];
        System.arraycopy(temp, 0, unique, 0, count);
        return unique;

    }

    //Helper function to calculate gini
    private static double calculateGini(Passenger[] left, Passenger[] right){

        int total = left.length + right.length;

        return ((double) left.length/total) * giniImpurity(left) + ((double) right.length/ total) * giniImpurity(right);

    }


    //Helper function to calculate Gini Impurity
    private static double giniImpurity(Passenger[] group) {

        if(group.length == 0) return 0;
        int survived = 0;

        for(int i = 0; i < group.length; i++){

            if(group[i].getSurvived()) {
                survived++;
            }

        }

        double p1 = (double) survived/group.length; //Survivor proportion
        double p0 = 1.0 - p1; //Death Proportion

        return 1.0 - (p1*p1 + p0*p0);

    }

}
