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
        if(sex.equalsIgnoreCase("female")) {

            features[index++] = 1.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        }
        else if (sex.equalsIgnoreCase("male")) {

            features[index++] = 0.0;
            features[index++] = 1.0;
            features[index++] = 0.0;

        }
        else {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 1.0;

        }

        //Pclass
        if(pClass == 1) {

            features[index++] = 1.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        }
        else if (pClass == 2) {

            features[index++] = 0.0;
            features[index++] = 1.0;
            features[index++] = 0.0;

        }
        else {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 1.0;

        }

        //Embarked
        if(embarked.equalsIgnoreCase("S")) {

            features[index++] = 1.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        }
        else if(embarked.equalsIgnoreCase("C")) {

            features[index++] = 0.0;
            features[index++] = 1.0;
            features[index++] = 0.0;

        }
        else if(embarked.equalsIgnoreCase("Q")) {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 1.0;

        }
        else {

            features[index++] = 0.0;
            features[index++] = 0.0;
            features[index++] = 0.0;

        }

        //Age + Missing Flag
        if(age <= 0.0) {

            features[index++] = -1.0;
            features[index++] = 1.0;

        }
        else {

            features[index++] = age;
            features[index++] = 0.0;

        }

        //Fare + Missing Flag
        if(fare <= 0.0) {

            features[index++] = -1.0;
            features[index++] = 1.0;

        }
        else {

            features[index++] = fare;
            features[index++] = 0.0;

        }

        //Family (SibSp + Parch)
        features[index++] = sibsp;
        features[index++] = parch;

        //Return array
        return features;

    }

    public boolean getSurvived() {
        return survived;
    }

}
