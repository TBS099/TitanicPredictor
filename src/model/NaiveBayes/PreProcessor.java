package model.NaiveBayes;

import model.*;

//Initialising PreProcessor class
public class PreProcessor {

    //Initialise variables
    private int numFeatures;
    private int numClasses;
    private int[] featureValueCounts;
    private int[][][] featureCounts;
    private int[] classCounts;
    private int[] classLabels;

    //Create training function
    public void train(Passenger[] passengers) {

        //Check training data
        if (passengers == null || passengers.length == 0) {
            throw new IllegalArgumentException("Training data is empty");
        }

        //Get total number of features
        numFeatures = passengers[0].getCategoricalFeatures().length;

        //Get total number of classes
        boolean hasZero = false, hasOne = false;
        for (Passenger p : passengers) {

            if (p.getSurvived()) {
                hasOne = true;
            } else {
                hasZero = true;
            }

        }

        if (hasOne && hasZero) {
            numClasses = 2; // Binary classification
            classLabels = new int[]{0, 1};
        } else {
            numClasses = 1; // Single class
            classLabels = hasOne ? new int[]{1} : new int[]{0};
        }

        classCounts = new int[numClasses];

        //Find max value for each feature
        int[][] featureMax = new int[numFeatures][1]; //[Feature][Max Value]

        for (Passenger p : passengers) {

            int[] features = p.getCategoricalFeatures();
            for (int i = 0; i < numFeatures; i++) {

                if (features[i] > featureMax[i][0]) {
                    featureMax[i][0] = features[i];
                }

            }

        }

        //Set feature value counts
        featureValueCounts = new int[numFeatures];
        for (int f = 0; f < numFeatures; f++) {
            featureValueCounts[f] = featureMax[f][0] + 1; // +1 to account for zero-indexing
        }

        //Initialise feature counts
        featureCounts = new int[numFeatures][][];

        for (int f = 0; f < numFeatures; f++) {
            featureCounts[f] = new int[featureValueCounts[f]][numClasses];
        }

        //Count Occurrences
        for (Passenger p : passengers) {

            //Initialise variables
            int[] features = p.getCategoricalFeatures();
            int classIndex = p.getSurvived() ? 1 : 0;
            classCounts[classIndex]++;

            for (int f = 0; f < numFeatures; f++) {
                int value = features[f];
                featureCounts[f][value][classIndex]++;
            }

        }
    }

    // Getters
    public int getNumFeatures() { return numFeatures; }

    public int getNumClasses() { return numClasses; }

    public int[] getFeatureValueCounts() { return featureValueCounts; }

    public int[][][] getFeatureCounts() { return featureCounts; }

    public int[] getClassCounts() { return classCounts; }

    public int[] getClassLabels() { return classLabels; }

}
