package controller;

import java.util.Arrays;
import java.util.Random;
import model.*;
import model.DecisionTree.*;
import model.KNN.*;
import model.NaiveBayes.*;
import view.*;

//Initialise Main class
public class Main {

    public static void main(String[] args) {

        //Load data
        Passenger[] passengers = DataLoader.loadData("data/train.csv");
        shuffleArray(passengers);

        //Split into 80/20
        int splitIndex = (int) (passengers.length * 0.8);
        Passenger[] trainingData = Arrays.copyOf(passengers, splitIndex);
        Passenger[] testData = Arrays.copyOfRange(passengers, splitIndex, passengers.length);

        //DECISION TREE
        //Initialise decision tree and train with training data
        DecisionTree decisionTree = new DecisionTree(5);
        decisionTree.fit(trainingData);

        //Predict survival and death
        boolean[] predictions = decisionTree.predictAll(testData);

        //Check accuracy and print message
        int correct = 0;
        for (int i = 0; i < testData.length; i++) {
            if (predictions[i] == testData[i].getSurvived()) {
                correct++;
            }
        }

        accuracyCalc("Decision Tree", correct, testData.length);

        //KNN CLASSIFIER
        //Initialise KNNClassifier
        KNNClassifier knn = new KNNClassifier(trainingData);

        //Test and print error margins for when k = 5
        correct = 0;

        //Go through each passenger in the dataset
        for (Passenger s : testData) {

            boolean survivalPrediction = knn.predict(s, 5);
            if (survivalPrediction == s.getSurvived()) {
                correct++;
            }

        }

        //Calculate accuracy and print out
        accuracyCalc("KNN Classifier", correct, testData.length);

        //NaiveBayes Classifier
        NaiveBayes nb = new NaiveBayes();
        nb.fit(trainingData);
        boolean[] naiveBayesPredictions = nb.predictAll(testData);

        //Check accuracy and print message
        int bayesCorrect = 0;
        for (int i = 0; i < testData.length; i++) {
            if (naiveBayesPredictions[i] == testData[i].getSurvived()) {
                bayesCorrect++;
            }
        }

        accuracyCalc("NaiveBayes Classifier", bayesCorrect, testData.length);

    }

    //Helper function to shuffle Passenger data
    public static void shuffleArray(Passenger[] array) {
        Random rand = new Random();

        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);  // random index from 0 to i
            Passenger temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    //Helper function to calculate and print accuracy
    public static void accuracyCalc(String modelName, int correct, int dataLength) {

        double accuracy = ((double) correct / dataLength) * 100;
        Output.showAccuracy(modelName, accuracy);

    }

}
