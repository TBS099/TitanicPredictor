package model.DecisionTree;

import model.*;

//Initialise DecisionTree class
public class DecisionTree {

    private final int MAX_DEPTH;
    private DecisionTreeNode root;

    //Constructor to set Max Depth
    public DecisionTree(int maxDepth) {

        this.MAX_DEPTH = maxDepth;

    }

    //Function to build the tree on training data
    public void fit(Passenger[] trainingData) {

        this.root = buildTree(trainingData, 0);

    }

    //Function to predict labels of passengers without manually going through it
    public boolean[] predictAll(Passenger[] passengers) {

        boolean[] predictions = new boolean[passengers.length];

        //Go through it one by one
        for(int i = 0; i < passengers.length; i++){

            predictions[i] = predict(passengers[i]);

        }

        return predictions;

    }

    //Function to predict label of a new passenger
    public boolean predict(Passenger passenger) {

        return predictRecursive(root, passenger);

    }

    //Helper function to build tree
    private DecisionTreeNode buildTree(Passenger[] group, int depth) {

        //Return if empty
        if (group.length == 0) return null;

        //Check if group is pure and create leaf node
        if(isPure(group)) {
            return new DecisionTreeNode(majorityLabel(group));
        }

        //Check if max depth has been reached and create a leaf node
        if(depth >= MAX_DEPTH) {
            return new DecisionTreeNode(majorityLabel(group));
        }

        //Find best splitting feature index and threshold
        SplitResult bestSplit = SplitResult.findBestSplit(group);

        //Check if split provided is usable
        if(bestSplit == null || bestSplit.left.length == 0 || bestSplit.right.length == 0) {

            //No good split possible, so create leaf node
            return new DecisionTreeNode(majorityLabel(group));

        }

        //Create decision node with split info
        DecisionTreeNode node = new DecisionTreeNode(bestSplit.featureIndex, bestSplit.threshold);

        //Recursively build branches
        node.left = buildTree(bestSplit.left, depth + 1);
        node.right = buildTree(bestSplit.right, depth + 1);

        return node;

    }

    //Helper function to check if all hold the same label (i.e, survived, died)
    private boolean isPure(Passenger[] group) {

        if(group.length == 0) return true;

        //Take first label to compare others against
        boolean firstLabel = group[0].getSurvived();

        //Check if labels match
        for (Passenger passenger : group) {

            if (passenger.getSurvived() != firstLabel) {
                return false;
            }

        }

        return true;

    }

    //Function to return majority in a group
    private boolean majorityLabel(Passenger[] group) {

        //Initialising variables
        int survived = 0;
        int died = 0;

        //Go through the group and check how many survived and died
        for (Passenger passenger : group) {

            if(passenger.getSurvived()) {
                survived++;
            }
            else {
                died++;
            }

        }

        //Return boolean accordingly
        return survived >= died;

    }

    //Helper function to traverse the decision tree
    private boolean predictRecursive(DecisionTreeNode node, Passenger passenger) {

        //If leaf node, return prediction
        if(node.isLeaf) return node.prediction;

        //Get feature value to compare
        double featureValue = passenger.getFeatures()[node.featureIndex];

        //Go left or right according to the threshold
        if(featureValue <= node.threshold) {
            return predictRecursive(node.left, passenger);
        }
        else {
            return predictRecursive(node.right, passenger);
        }

    }


}
