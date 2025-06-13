package model.NaiveBayes;

import model.*;

//Initialise wrapper class
public class NaiveBayes {

    private NaiveBayesClassifier classifier;

    // Fit method to train the classifier
    public void fit(Passenger[] trainingData) {
        PreProcessor preProcessor = new PreProcessor();
        preProcessor.train(trainingData);
        classifier = new NaiveBayesClassifier(preProcessor);
    }

    // Predict for a single passenger
    public boolean predict(Passenger p) {
        return classifier.predict(p);
    }

    // Predict for all passengers
    public boolean[] predictAll(Passenger[] passengers) {
        return classifier.predictAll(passengers);
    }

}
