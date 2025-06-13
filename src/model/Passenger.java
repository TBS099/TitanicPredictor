package model;

public class Passenger {

    //Initialise variables
    private final String name;
    private final String sex;
    private final int pClass;
    private final double age;
    private final int sibsp;
    private final int parch;
    private final String ticket;
    private final double fare;
    private final String cabin;
    private final String embarked;
    private final boolean survived;

    //Initialise Passenger Class
    public Passenger(String name, String sex, int pClass, double age, int sibsp, int parch, String ticket, double fare, String cabin, String embarked, boolean survived) {

        this.name = name;
        this.sex = sex;
        this.age = age;
        this.sibsp = sibsp;
        this.parch = parch;
        this.ticket = ticket;
        this.pClass = pClass;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
        this.survived = survived;

    }

    public double[] getFeatures() {

        double[] features = new double[15]; //3 gender + 3 class + 3 embarked + 2 age + 2 fare + 2 family
        int index = 0;

        //One-hot encoding
        //Sex
        if (sex.equalsIgnoreCase("female")) {

            features[index++] = 1.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        } else if (sex.equalsIgnoreCase("male")) {

            features[index++] = 0.0;
            features[index++] = 1.0;
            features[index++] = 0.0;

        } else {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 1.0;

        }

        //Pclass
        switch (pClass) {
            case 1:
                features[index++] = 1.0;
                features[index++] = 0.0;
                features[index++] = 0.0;
                break;
            case 2:
                features[index++] = 0.0;
                features[index++] = 1.0;
                features[index++] = 0.0;
                break;
            default:
                features[index++] = 0.0;
                features[index++] = 0.0;
                features[index++] = 1.0;
                break;
        }

        //Embarked
        if (embarked.equalsIgnoreCase("S")) {

            features[index++] = 1.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        } else if (embarked.equalsIgnoreCase("C")) {

            features[index++] = 0.0;
            features[index++] = 1.0;
            features[index++] = 0.0;

        } else if (embarked.equalsIgnoreCase("Q")) {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 1.0;

        } else {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        }

        //Age + Missing Flag
        if (age <= 0.0) {

            features[index++] = -1.0;
            features[index++] = 1.0;

        } else {

            features[index++] = age;
            features[index++] = 0.0;

        }

        //Fare + Missing Flag
        if (fare <= 0.0) {

            features[index++] = -1.0;
            features[index++] = 1.0;

        } else {

            features[index++] = fare;
            features[index++] = 0.0;

        }

        //Family (SibSp + Parch)
        features[index++] = sibsp;
        features[index++] = parch;

        //Return array
        return features;

    }

    //Function to return features as integer categories
    public int[] getCategoricalFeatures() {

        //Initialise variables
        int[] features = new int[8]; //8 features in numerical form

        //Gender
        if (sex == null) {
            features[0] = 3;
        } else if (sex.equalsIgnoreCase("female")) {
            features[0] = 0;
        } else if (sex.equalsIgnoreCase("male")) {
            features[0] = 1;
        }

        //Passenger class
        switch (pClass) {
            case 0:
                features[1] = 3;
                break;
            case 1:
                features[1] = 0;
                break;
            case 2:
                features[1] = 1;
                break;
            case 3:
                features[1] = 2;
                break;
            default:
                features[1] = 3; //Treat invalid as missing
                break;
        }

        //Embarked
        if (embarked == null) {
            features[2] = 3;
        } else if (embarked.equalsIgnoreCase("S")) {
            features[2] = 0;
        } else if (embarked.equalsIgnoreCase("C")) {
            features[2] = 1;
        } else if (embarked.equalsIgnoreCase("Q")) {
            features[2] = 2;
        } else {
            features[2] = 3; //Treat invalid as missing
        }

        //Age groups
        if (age <= 0.0) {
            features[3] = 4; // missing
        } else if (age <= 12) {
            features[3] = 0;
        } else if (age <= 18) {
            features[3] = 1;
        } else if (age <= 60) {
            features[3] = 2;
        } else {
            features[3] = 3;
        }

        //Fare groups
        if (fare <= 0.0) {
            features[4] = 4; // missing
        } else if (fare < 10) {
            features[4] = 0;
        } else if (fare < 30) {
            features[4] = 1;
        } else if (fare < 100) {
            features[4] = 2;
        } else {
            features[4] = 3;
        }

        //SibSp
        features[5] = Math.min(sibsp, 5);

        //Parch
        features[6] = Math.min(parch, 5);

        //Cabin
        if (cabin == null || cabin.isEmpty()) {
            features[7] = 0;
        } else {
            features[7] = 1; //Has cabin info
        }

        return features;

    }

    //Getter for survival variable
    public boolean getSurvived() {
        return survived;
    }

}
