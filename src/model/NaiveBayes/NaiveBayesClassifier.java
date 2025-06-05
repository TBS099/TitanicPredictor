package model.NaiveBayes;

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
    public NaiveBayesClassifier(int[] classes, int numFeatures, int[] featureValueCounts) {

        //Initialise variables
        this.classes = classes;
        this.numClasses = classes.length;
        this.numFeatures = numFeatures;
        this.featureValueCounts = featureValueCounts;

        //Initialise arrays for priors and likelihoods
        classPriors = new double[numClasses];
        likelihoods = new double[numFeatures][][];

        for(int f = 0; f < numFeatures; f++) {

            likelihoods[f] = new double[featureValueCounts[f]][numClasses];

        }

        //Initialise count arrays
        classCounts = new int[numClasses][1];
        featureCounts = new int[numFeatures][][];

        for(int f = 0; f < numFeatures; f++) {
            featureCounts[f] = new int[featureValueCounts[f]][numClasses];
        }

    }

}
