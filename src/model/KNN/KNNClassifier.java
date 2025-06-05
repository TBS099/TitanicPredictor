package model.KNN;

import model.Passenger;

import java.util.Arrays;
import java.util.Comparator;

//Initialise KNNClassifier Class
public class KNNClassifier {

    //Initialise variables
    private Passenger[] trainingData;

    //Initialise constructor
    public KNNClassifier(Passenger[] trainingData) {

        this.trainingData = trainingData;

    }

    //Function to calculate Euclidean distance
    private double distance(double[] a, double[] b) {

        //Initialise variables
        double sum = 0.0;

        //Go through the data and sum up differences
        for (int i = 0; i < a.length; i++) {

            double diff = a[i] - b[i];
            sum += diff * diff;

        }

        return Math.sqrt(sum);

    }

    //Function to predict according to differences between data
    public boolean predict(Passenger input, int k) {

        return Arrays.stream(trainingData)
                .sorted(Comparator.comparingDouble(p -> distance(p.getFeatures(), input.getFeatures())))
                .limit(k)
                .map(p -> p.getSurvived() ? 1 : 0) // convert boolean to int (1 = survived)
                .reduce(0, Integer::sum)           // sum how many survived
                >= (k / 2.0);                      // return true if majority survived

    }

}
