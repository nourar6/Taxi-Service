package Taxi_Packages;

import java.util.List;
import java.util.HashMap;

public class Places_Visited {

    HashMap<Integer, List> places_by_year = new HashMap<Integer, List>();

    public void set_places_visited(List<String> places_visited, int year) {

        places_by_year.put(year, places_visited);
    }

    public List<String> get_places_visited(int year) {

        return places_by_year.get(year);

    }

    //TODO: add analysing method here

}

