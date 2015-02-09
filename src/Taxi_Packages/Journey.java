package Taxi_Packages;


import java.util.ArrayList;

public class Journey {

    String dest_place;
    Double distance;
    String taxi_reg;
    Integer no_of_passengers;
    Double cost;

    private void calculateCost(Integer no_of_passengers, Double distance) {
        cost =  distance * 1+(0.1 * no_of_passengers);
    }

    public void setJourney(String dest_place, Double distance, String taxi_reg, Integer no_of_passengers) {

        this.dest_place = dest_place;
        this.distance = distance;
        this.taxi_reg = taxi_reg;
        this.no_of_passengers = no_of_passengers;
        calculateCost(no_of_passengers, distance);

    }

    public Journey getJourney() {
        return this;
    }
}
