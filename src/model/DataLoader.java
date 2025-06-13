package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Initialise Model.DataLoader class
public class DataLoader {

    public static Passenger[] loadData(String filePath) {

        //Count lines in the data file to initialise array
        int lineCount = 0;
        Passenger[] passengers = null;

        //First run: Count lines (Excluding headers and blank lines
        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            //Skip headerline
            String headerline = reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {

                if(!line.trim().isEmpty()) {
                    lineCount++;
                }

            }

        }
        catch (IOException e) {

            System.err.println("Error reading file: " + e.getMessage());
            return new Passenger[0];

        }

        //Create fixed array and initialise counter
        passengers = new Passenger[lineCount];
        int passengerIndex = 0;

        //Second run: Read and parse file
        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String headerline = reader.readLine();
            String[] headers = headerline.split(",");

            //Find and store column index in variables
            int nameIndex = findColumnIndex(headers, "Name");
            int survivedIndex = findColumnIndex(headers, "Survived");
            int pClassIndex = findColumnIndex(headers, "Pclass");
            int sexIndex = findColumnIndex(headers, "Sex");
            int ageIndex = findColumnIndex(headers, "Age");
            int sibspIndex = findColumnIndex(headers, "SibSp");
            int parchIndex = findColumnIndex(headers, "Parch");
            int ticketIndex = findColumnIndex(headers, "Ticket");
            int fareIndex = findColumnIndex(headers, "Fare");
            int cabinIndex = findColumnIndex(headers, "Cabin");
            int embarkedIndex = findColumnIndex(headers, "Embarked");

            String line;
            int skipped = 0;

            //Run through the file
            while ((line = reader.readLine()) != null) {

                //Skip line if empty
                if(line.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(",");

                try {

                    //Store data in variables
                    String name = getSafeString(tokens, nameIndex);
                    boolean survived = getSafeBoolean(tokens, survivedIndex, false);
                    int pClass = getSafeInt(tokens, pClassIndex, 3);
                    String sex = getSafeString(tokens, sexIndex);
                    double age = getSafeDouble(tokens, ageIndex, -1.0);
                    int sibsp = getSafeInt(tokens, sibspIndex, 0);
                    int parch = getSafeInt(tokens, parchIndex, 0);
                    String ticket = getSafeString(tokens, ticketIndex);
                    double fare = getSafeDouble(tokens, fareIndex, -1.0);
                    String cabin = getSafeString(tokens, cabinIndex);
                    String embarked = getSafeString(tokens, embarkedIndex);

                    //Store data in Passenger class instance
                    Passenger passenger = new Passenger(name, sex, pClass, age, sibsp, parch, ticket, fare, cabin, embarked, survived);
                    passengers[passengerIndex++] = passenger;

                }
                catch (Exception e) {

                    System.err.println("Error reading line: " + line);
                    skipped++;

                }

            }

            System.err.println("Skipped " + skipped + " passengers");

        }
        catch (IOException e) {

            System.err.println("Error reading file: " + e.getMessage());

        }

        return passengers;

    }

    //Helper function to find column index by name
    private static int findColumnIndex(String[] headers, String columnName) {

        for(int i = 0; i < headers.length; i++) {
            if(headers[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;

    }

    //Helper function to extract safe string
    private static String getSafeString(String[] tokens, int index) {

        if (index < 0 || index >= tokens.length) {
            return "";
        }

        return tokens[index].trim();

    }

    //Helper function to safely extract parsed integers
    private static int getSafeInt(String[] tokens, int index, int defaultValue) {

        String s = getSafeString(tokens, index);

        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    //Helper function to safely extract parsed double
    private static double getSafeDouble(String[] tokens, int index, double defaultValue) {

        String s = getSafeString(tokens, index);

        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    //Helper function to safely parsed boolean
    private static boolean getSafeBoolean(String[] tokens, int index, boolean defaultValue) {

        String s = getSafeString(tokens, index);

        if (s.equals("1")) return true;
        if (s.equals("0")) return false;
        return defaultValue;

    }

}
