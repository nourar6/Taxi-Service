import Taxi_Packages.Driver_Visits;
import Taxi_Packages.Journey;
import Taxi_Packages.Places_Visited;
import Taxi_Packages.Taxis;

import javax.swing.table.TableCellEditor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Array;
import java.util.*;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Taxi_Service {

    // Load text files and generate instances of the clases to be built
    // Read Drivers file

    public static void main(String[] args) throws FileNotFoundException,IOException {

        TreeMap Taxis_Map = new TreeMap();
        Places_Visited places =  new Places_Visited(); // TODO: change places to a SET type
        Driver_Visits driver_visits = new Driver_Visits();
        ArrayList<Object> journey_list = new ArrayList<Object>();
        // CSV READER Setting
        // gs
        String DELIMITER = ",";

        BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\drivers.csv")));
        String line = null;
        //CSV Loop
        while ((line = reader.readLine()) != null) {

            String[] split = line.split(DELIMITER);
            // System.out.println(split[0].concat(", " + split[1]));
            Taxis taxi =  new Taxis();
            taxi.setDriver(split[0], split[1]);
            Taxis_Map.put(taxi.getRegistration(), taxi);

        }
        reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\destinations_2014.csv")));
        // specify year for places_visited
        int year = 2014;
        // List of places_visited set as empty
        Set<String> places_visited = new HashSet<String>();
        // While loop to loop over each line of places
        while ((line = reader.readLine()) != null) {
            //add the places to the list
            places_visited.add(line);
        }
        //once list is finished add the list with the year to our places_visited class
        places.set_places_visited(places_visited, year);


        reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\places_and_distances.csv")));
        line = null;
        //CSV Loop
        HashMap<String, Double> distances = new HashMap<String, Double>();

        while ((line = reader.readLine()) != null) {

            String[] split = line.split(DELIMITER);
            distances.put(split[0], Double.parseDouble(split[1]));

        }


        //Create FileReader object which points to json file
        FileReader fileReader = new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\destinations_2015.json"));
        //Create JSONParser to use on the input of file
        JSONParser parser = new org.json.simple.parser.JSONParser();

        // try for parsing the file in case the file is not the correct format.
        try {
            //do the parsing
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            //get the keys from the root of the json file.
            Set<String> json_keys = jsonObject.keySet();
            //transform Set of keys to an iterator to loop through them.
            Iterator<String> json_iter = json_keys.iterator();

            //Reset the places_visited Array
            places_visited = new HashSet<String>();

            //Loop over the Keys
            while (json_iter.hasNext())
            {
                //fetch the key from iterator
                String key = json_iter.next();
                //fetch the jsonObject using the key
                Object arr = jsonObject.get(key);

                //Cast Array to the expected type
                ArrayList<ArrayList<String>> visited = (ArrayList<ArrayList<String>>) arr;

                //Create new ArrayList for Drivers visited
                ArrayList<String> driver_visited = new ArrayList<String>();


                //loop over the list of visited places
                for(int i= 0; i < visited.size(); i++)
                {
                    Journey journey = new Journey();

                    //get the first element(string) for each array(i).
                    String place = visited.get(i).get(0);
                   // System.out.println(place);
                    int no_pass = Integer.parseInt(visited.get(i).get(1));
                    //System.out.println(no_pass);
                    //add place to places_visited
                    places_visited.add(place);
                    //add place to driver_visited
                    driver_visited.add(place);

                    try {
                        journey.setJourney(place, distances.get(place), key, no_pass);

                        journey_list.add(journey);
                    }
                    catch (NullPointerException ex)
                    {
                        System.out.println(ex);
                    }
                }
                //Driver_Visited Class Takes a HashMap for storing the Driver to visited_places
                HashMap<String, ArrayList<String>> map_driver_visited = new HashMap<String, ArrayList<String>>();
                //put the reg and places into map
                map_driver_visited.put(key, driver_visited);
                //set the map to the driver_visits class
                driver_visits.setPlaces_visited(map_driver_visited);

            }
            //set places visited for 2015
            places.set_places_visited(places_visited, 2015);
        }
        // catch for the possible parsing excpetion
        catch (ParseException ex) {
            //print the stacktrace for the exception
            System.out.println("OOOPS");
            ex.printStackTrace();
        }

//        for(int i=0; i < journey_list.size(); i++) {
//
//            Journey journey = (Journey) journey_list.get(i);
//        }
        //System.out.println(places.get_places_visited(2014));
        //System.out.println(places.get_places_visited(2015));
        //System.out.println(driver_visits.getPlaces_visited("M5Y 626"));

        report_fares(journey_list, false);

        report_fares(journey_list, true);
    }

    public static void  report_fares(ArrayList list_journeys, Boolean expensive) {

        List<Journey> ordered_list = new ArrayList<Journey>();

        for (int i = 0; i < list_journeys.size(); i++) {
            ordered_list.add((Journey) list_journeys.get(i));

        }
        Collections.sort(ordered_list);


        if (expensive) {
            System.out.println("CHARGES FOR THE TOP 5 JOURNEYS");

            for (int i = ordered_list.size()-1; i > (ordered_list.size()-6); i--) {

                //System.out.println(ordered_list.get(i).getJourney());
                ArrayList journey_vals = ordered_list.get(i).getJourney();
                System.out.printf("%s   %s    %.1f miles  %d people   Cost £%.2f\n",
                        (String) journey_vals.get(2),
                        (String) journey_vals.get(0),
                        (Double) journey_vals.get(1),
                        (Integer) journey_vals.get(3),
                        (Double) journey_vals.get(4));
            }
            System.out.println();

        }
        else {

            System.out.println("CHARGES FOR THE CHEAPEST 5 JOURNEYS");

            for (int i = 0; i < 5; i++) {

                //System.out.println(ordered_list.get(i).getJourney());
                ArrayList journey_vals = ordered_list.get(i).getJourney();
                System.out.printf("%s   %s    %.1f miles  %d people   Cost £%.2f\n",
                        (String) journey_vals.get(2),
                        (String) journey_vals.get(0),
                        (Double) journey_vals.get(1),
                        (Integer) journey_vals.get(3),
                        (Double) journey_vals.get(4));
            }
            System.out.println();

        }

    }
}
