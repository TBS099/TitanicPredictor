package view;

import model.*;

//Initialise Output class
public class Output {

    //Display accuracy of model
    public static void showAccuracy(String modelName, double accuracy) {
        System.out.println(modelName + " accuracy: " + accuracy + "%");
    }

    //Display Individual predictions
    public static void showPredictions(String modelName, boolean[] predictions, Passenger[] data) {

        System.out.println("Predictions from: " + modelName);

        //Run through the array
        for (int i = 0; i < predictions.length; i++) {

            System.out.printf("Passenger %d: Actual = %s, Predicted = %s%n",
                    i+1,
                    data[i].getSurvived() ? "Survived" : "Dead",
                    predictions[i] ? "Survived" : "Dead");

        }

    }

    //Display General Messages
    public static void showMessage(String message) {
        System.out.println(message);
    }
}
