package Taxi_Packages;


import java.util.HashMap;
import java.util.Set;

public class Places_Visited {
    // HashMap for Mapping a year to A Set of Places
    HashMap<Integer, Set> places_by_year = new HashMap<Integer, Set>();

    // Constructor
    public void set_places_visited(Set<String> places_visited, int year) {

        places_by_year.put(year, places_visited);
    }

    // Returns the set of places with a given key.
    public Set<String> get_places_visited(int year) {

        return places_by_year.get(year);

    }

    //TODO: add analysing method here

}

