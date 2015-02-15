package Taxi_Packages;

import java.util.ArrayList;
import java.util.HashMap;

public class Driver_Visits {
    HashMap<String, ArrayList<String>> places_visited_map = new HashMap<String, ArrayList<String>>();

    public void setPlaces_visited(String key, ArrayList<String> places_visited) {
        places_visited_map.put(key, places_visited);
    }

    public ArrayList<String> getPlaces_visited(String key) {

        return places_visited_map.get(key);
    }
}
