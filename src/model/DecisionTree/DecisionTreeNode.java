package model.DecisionTree;

//Initialise DecisionTreeNode class
public class DecisionTreeNode {

    //Initialise variables
    int featureIndex; //Feature the node is testing
    double threshold; //Value the feature is compared against
    boolean isLeaf; //true if final prediction node
    boolean prediction; //Only valid if isLeaf == true

    DecisionTreeNode left; //Yes branch (Feature <= Threshold)
    DecisionTreeNode right; //No branch (Feature > Threshold)

    //Leaf Node constructor
    public DecisionTreeNode(boolean prediction) {

        this.isLeaf = true;
        this.prediction = prediction;

    }

    //Decision Node Constructor
    public DecisionTreeNode(int featureIndex, double threshold) {

        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.isLeaf = false;

    }

}
