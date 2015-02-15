package Taxi_Packages;

import java.util.ArrayList;
import java.util.HashMap;

public class Driver_Visits {
    // Create HashMap which will be used to Map the Drivers Reg to the list of places they have been to.
    HashMap<String, ArrayList<String>> places_visited_map = new HashMap<String, ArrayList<String>>();

    // constructor for setting the places visited
    public void setPlaces_visited(String key, ArrayList<String> places_visited) {
        //add the key and list to the HashMap
        places_visited_map.put(key, places_visited);
    }
    //Public Method to get the contents of the HashMap with a given Key.
    public ArrayList<String> getPlaces_visited(String key) {
        // Returns the List of places
        return places_visited_map.get(key);
    }
}
