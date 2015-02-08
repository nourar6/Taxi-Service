import Taxi_Packages.Places_Visited;
import Taxi_Packages.Taxis;

import javax.swing.table.TableCellEditor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.*;

public class Taxi_Service {

    //Load text files and generate instances of the clases to be built
    // Read Drivers file

    public static void main(String[] args) throws FileNotFoundException,IOException {

        TreeMap Taxis_Map = new TreeMap();
        Places_Visited places =  new Places_Visited();

        // CSV READER Setting
        // gs
        String DELIMITER = ",";

        BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\drivers.csv")));
        String line = null;
        //CSV Loop
        while ((line = reader.readLine()) != null) {

            String[] split = line.split(DELIMITER);
            System.out.println(split[0].concat(", " + split[1]));
            Taxis taxi =  new Taxis();
            taxi.setDriver(split[0], split[1]);
            Taxis_Map.put(taxi.getRegistration(), taxi);

        }
        reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\destinations_2014.csv")));
        // specify year for places_visited
        int year = 2014;
        // List of places_visited set as empty
        List<String> places_visited = new ArrayList<String>();
        // While loop to loop over each line of places
        while ((line = reader.readLine()) != null) {
            //add the places to the list
            places_visited.add(line);
        }
        //once list is finished add the list with the year to our places_visited class
        places.set_places_visited(places_visited, year);

        System.out.println(places.get_places_visited(2014));

        reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\destinations_2015.json")));

        JSONObject obj = new JSONObject(reader);

        JSONArray arr = obj.getJSONObject("Z3O 149");

        for (int i = 0; i < arr.length(); i++)
        {
            System.out.println(arr.get(i));

        }

    }


}
