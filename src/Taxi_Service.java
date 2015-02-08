import Taxi_Packages.Taxis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Taxi_Service {

    //Load text files and generate instances of the clases to be built
    // Read Drivers file

    public static void main(String[] args) throws FileNotFoundException,IOException {

        String DELIMITER = ",";
        int FIELD_TO_SUM = 2;
        int sum = 0;
        int lineCount = 0;
        System.out.println(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\drivers.csv"));
        BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\drivers.csv")));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(DELIMITER);

            lineCount++;
            System.out.println(split[0].concat(", " + split[1]));
            Taxis taxi =  new Taxis();
            taxi.setDriver(split[0], split[1]);

            System.out.println(taxi.getDriver());
            System.out.println(taxi.getRegistration());
        }
    }


}
