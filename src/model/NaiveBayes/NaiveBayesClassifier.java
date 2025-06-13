package model.NaiveBayes;

import model.*;

//Initialise NaiveBayesClassifier class
public class NaiveBayesClassifier {

    //Initialise variables
    private int[] classes;
    private double[] classPriors;
    private double[][][] likelihoods;

    private int numClasses;
    private int numFeatures;
    private int[] featureValueCounts;

    //For counting during training
    private int[][] classCounts;
    private int[][][] featureCounts;

    //Initialise constructor
    public NaiveBayesClassifier(PreProcessor preProcessor) {

        this.classes = preProcessor.getClassLabels();
        this.numClasses = preProcessor.getNumClasses();
        this.numFeatures = preProcessor.getNumFeatures();
        this.featureValueCounts = preProcessor.getFeatureValueCounts();

        int[] classCounts = preProcessor.getClassCounts();
        int[][][] featureCounts = preProcessor.getFeatureCounts();
        int totalSamples = 0;

        //Calculate total samples
        for (int c = 0; c < numClasses; c++) {
            totalSamples += classCounts[c];
        }

        //Calculate class priors
        classPriors = new double[numClasses];
        likelihoods = new double[numFeatures][][];

        for (int f = 0; f < numFeatures; f++) {
            likelihoods[f] = new double[featureValueCounts[f]][numClasses];
        }

        //Compute likelihoods with laplace smoothing
        for (int f = 0; f < numFeatures; f++) {

            for (int v = 0; v < featureValueCounts[f]; v++) {

                for (int c = 0; c < numClasses; c++) {

                    int count = featureCounts[f][v][c];
                    likelihoods[f][v][c] = (double) (count + 1) / (classCounts[c] + featureValueCounts[f]);

                }

            }

        }

    }

    //Function to predict
    public boolean predict(Passenger passenger) {

        double[] logProbs = new double[numClasses];
        int[] features = passenger.getCategoricalFeatures();

        //Calculate log probabilities for each class
        for (int c = 0; c < numClasses; c++) {

            logProbs[c] = Math.log(classPriors[c]);

            for (int f = 0; f < numFeatures; f++) {
                int featureValue = features[f];
                logProbs[c] += Math.log(likelihoods[f][featureValue][c]);
            }

        }

        return logProbs[1] > logProbs[0]; // Return true if class 1 (survived) is more probable

    }

    //Function to predict all at once
    public boolean[] predictAll(Passenger[] passengers) {

        boolean[] predictions = new boolean[passengers.length];
        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = predict(passengers[i]);
        }

        return predictions;

    }

}
