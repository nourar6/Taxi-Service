package Taxi_Packages;

import java.util.ArrayList;
import java.util.HashMap;

public class Driver_Visits {
    String reg;
    HashMap<String, ArrayList<String>> places_visited;

    public void setPlaces_visited(HashMap<String, ArrayList<String>> places_visited) {
        this.places_visited = places_visited;
    }

    public ArrayList<String> getPlaces_visited(String key) {

        return places_visited.get(key);
    }
}
